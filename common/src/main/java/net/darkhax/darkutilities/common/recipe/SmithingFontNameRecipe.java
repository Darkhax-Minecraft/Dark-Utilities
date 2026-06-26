package net.darkhax.darkutilities.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.darkhax.darkutilities.common.DarkUtils;
import net.darkhax.darkutilities.common.component.NameStyle;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public class SmithingFontNameRecipe extends SimpleSmithingRecipe {

    public static final Identifier RECIPE_ID = DarkUtils.id("smithing_font_name");

    public static final MapCodec<SmithingFontNameRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CommonInfo.MAP_CODEC.forGetter(r -> r.commonInfo),
            Ingredient.CODEC.fieldOf("base").forGetter(r -> r.base),
            Ingredient.CODEC.fieldOf("template").forGetter(r -> r.template),
            Ingredient.CODEC.fieldOf("addition").forGetter(r -> r.addition),
            Identifier.CODEC.fieldOf("font").forGetter(r -> r.fontID)
    ).apply(instance, SmithingFontNameRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SmithingFontNameRecipe> STREAM_CODEC = StreamCodec.composite(
            CommonInfo.STREAM_CODEC, r -> r.commonInfo,
            Ingredient.CONTENTS_STREAM_CODEC, r -> r.base,
            Ingredient.CONTENTS_STREAM_CODEC, r -> r.template,
            Ingredient.CONTENTS_STREAM_CODEC, r -> r.addition,
            Identifier.STREAM_CODEC, r -> r.fontID,
            SmithingFontNameRecipe::new);
    public static final RecipeSerializer<SmithingFontNameRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);


    final Ingredient base;
    final Ingredient template;
    final Ingredient addition;
    final Identifier fontID;

    public SmithingFontNameRecipe(CommonInfo info, Ingredient base, Ingredient template, Ingredient addition, Identifier fontID) {
        super(info);
        this.base = base;
        this.template = template;
        this.addition = addition;
        this.fontID = fontID;
    }

    @Override
    public boolean matches(SmithingRecipeInput input, Level level) {
        return this.template.test(input.template()) && !input.base().isEmpty() && (input.addition().isEmpty() || this.addition.test(input.addition()));
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input) {
        final ItemStack output = input.base().copy();
        output.set(NameStyle.COMPONENT.get(), new NameStyle(this.fontID, Optional.ofNullable(input.addition().get(DataComponents.DYE))));
        return output;
    }

    @Override
    public Optional<Ingredient> templateIngredient() {
        return Optional.of(this.template);
    }

    @Override
    public Ingredient baseIngredient() {
        return this.base;
    }

    @Override
    public Optional<Ingredient> additionIngredient() {
        return Optional.of(this.addition);
    }

    @Override
    public RecipeSerializer<SmithingFontNameRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    protected PlacementInfo createPlacementInfo() {
        return PlacementInfo.createFromOptionals(List.of(this.templateIngredient(), Optional.of(this.base), this.additionIngredient()));
    }
}