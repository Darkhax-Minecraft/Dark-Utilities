package net.darkhax.darkutilities.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.darkhax.darkutilities.common.Constants;
import net.darkhax.darkutilities.common.component.NameStyle;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SmithingFontNameRecipe implements SmithingRecipe {

    public static final ResourceLocation RECIPE_ID = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "smithing_font_name");
    public static final CachedSupplier<RecipeSerializer<SmithingFontNameRecipe>> SERIALIZER = CachedSupplier.of(BuiltInRegistries.RECIPE_SERIALIZER, RECIPE_ID).cast();

    private static final HashMap<TagKey<Item>, DyeColor> COLOR_MAP = Util.make(() -> {
        final HashMap<TagKey<Item>, DyeColor> map = new HashMap<>();
        for (DyeColor color : DyeColor.values()) {
            map.put(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "dyes/" + color.getName())), color);
        }
        return map;
    });

    public static Optional<DyeColor> getDyeColor(ItemStack stack) {
        if (stack.getItem() instanceof DyeItem dye) {
            return Optional.of(dye.getDyeColor());
        }
        for (Map.Entry<TagKey<Item>, DyeColor> entry : COLOR_MAP.entrySet()) {
            if (stack.is(entry.getKey())) {
                return Optional.ofNullable(entry.getValue());
            }
        }
        return Optional.empty();
    }

    final Ingredient template;
    final ResourceLocation fontID;

    public SmithingFontNameRecipe(Ingredient template, ResourceLocation fontID) {
        this.template = template;
        this.fontID = fontID;
    }

    @Override
    public boolean matches(SmithingRecipeInput input, Level level) {
        return !input.base().isEmpty() && this.template.test(input.template()) && (input.addition().isEmpty() || getDyeColor(input.addition()).isPresent());
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider registries) {
        final Optional<DyeColor> color = getDyeColor(input.addition());
        final ItemStack output = input.base().copy();
        output.set(NameStyle.COMPONENT.get(), new NameStyle(this.fontID, color));
        return output;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return Items.NAME_TAG.getDefaultInstance();
    }

    @Override
    public boolean isTemplateIngredient(ItemStack stack) {
        return this.template.test(stack);
    }

    @Override
    public boolean isBaseIngredient(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isAdditionIngredient(ItemStack stack) {
        return stack.isEmpty() || getDyeColor(stack) != null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public boolean isIncomplete() {
        return this.template.isEmpty();
    }

    public static class Serializer implements RecipeSerializer<SmithingFontNameRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        private static final MapCodec<SmithingFontNameRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("template").forGetter(r -> r.template),
                ResourceLocation.CODEC.fieldOf("font").forGetter(r -> r.fontID)
        ).apply(instance, SmithingFontNameRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, SmithingFontNameRecipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        public MapCodec<SmithingFontNameRecipe> codec() {
            return CODEC;
        }

        public StreamCodec<RegistryFriendlyByteBuf, SmithingFontNameRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static SmithingFontNameRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            final Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            final ResourceLocation fontID = buffer.readResourceLocation();
            return new SmithingFontNameRecipe(template, fontID);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, SmithingFontNameRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.template);
            buffer.writeResourceLocation(recipe.fontID);
        }
    }
}