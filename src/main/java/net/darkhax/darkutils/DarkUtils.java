package net.darkhax.darkutils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.darkhax.bookshelf.item.ItemGroupBase;
import net.darkhax.bookshelf.network.NetworkHelper;
import net.darkhax.bookshelf.registry.RegistryHelper;
import net.darkhax.bookshelf.registry.RegistryHelperClient;
import net.darkhax.darkutils.features.slimecrucible.MessageSyncCrucibleType;
import net.darkhax.darkutils.network.NetworkHandlerClient;
import net.darkhax.darkutils.network.NetworkHandlerServer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DarkUtils.MOD_ID)
public class DarkUtils {
    
    public static final String MOD_ID = "darkutils";
    
    public static final String MOD_NAME = "Dark Utilities";
    
    public static final Logger LOG = LogManager.getLogger(MOD_NAME);
    
    public static final ItemGroup ITEM_GROUP = new ItemGroupBase(MOD_ID, () -> new ItemStack(Items.APPLE));
    
    public static final NetworkHelper NETWORK = new NetworkHelper(new ResourceLocation(MOD_ID, "main"), "2.0.X");
    
    public static RegistryHelper registry;
    
    public static Content content;
    
    public DarkUtils() {
        
        registry = DistExecutor.runForDist( () -> () -> new RegistryHelperClient(MOD_ID, LOG, ITEM_GROUP), () -> () -> new RegistryHelper(MOD_ID, LOG, ITEM_GROUP));
        registry.initialize(FMLJavaModLoadingContext.get().getModEventBus());
        content = DistExecutor.runForDist( () -> () -> new ContentClient(registry), () -> () -> new Content(registry));
        
        NETWORK.registerEnqueuedMessage(MessageSyncCrucibleType.class, NetworkHandlerServer::encodeStageMessage, t -> NetworkHandlerClient.decodeStageMessage(t), (t, u) -> NetworkHandlerClient.processSyncStagesMessage(t, u));
    }
    
    /**
     * Gets a map of recipes for a given type from the recipe manager of the world.
     * 
     * @param recipeType The type of recipe to look for.
     * @param manager The recipe manager instance.
     * @return A map of all recipes for the provided recipe type. This will be null if no
     *         recipes were registered.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T extends IRecipe<?>> Map<ResourceLocation, T> getRecipes (IRecipeType<T> recipeType, RecipeManager manager) {
        
        final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipesMap = ObfuscationReflectionHelper.getPrivateValue(RecipeManager.class, manager, "field_199522_d");
        return (Map)recipesMap.getOrDefault(recipeType, Collections.emptyMap());
    }
    
    public static <T extends IRecipe<?>> List<T> getRecipeList(IRecipeType<T> recipeType, RecipeManager manager ) {
        
        Comparator<T> comparator = Comparator.comparing(recipe -> recipe.getRecipeOutput().getTranslationKey());
        return getRecipes(recipeType, manager).values().stream().sorted(comparator).collect(Collectors.toList());
    }
}