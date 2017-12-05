package net.darkhax.darkutils.features.sneaky;

import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.material.FeatureMaterial;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@DUFeature(name = "Sneaky Blocks", description = "Blocks that can hide as other blocks")
public class FeatureSneaky extends Feature {

    public static Block blockSneakyBlock;

    public static Block blockSneakyLever;

    public static Block blockSneakyGhost;

    public static Block blockSneakyTorch;

    public static Block blockSneakyObsidian;

    public static Block blockSneakyPlate;

    public static Block blockSneakyBedrock;

    public static Block blockSneakyButton;

    public static boolean opacity;

    @Override
    public void onPreInit () {

        blockSneakyBlock = new BlockSneaky();
        DarkUtils.REGISTRY.registerBlock(blockSneakyBlock, "sneaky");
        GameRegistry.registerTileEntity(TileEntitySneaky.class, "sneaky");

        blockSneakyLever = new BlockSneakyLever();
        DarkUtils.REGISTRY.registerBlock(blockSneakyLever, "sneaky_lever");

        blockSneakyGhost = new BlockSneakyGhost();
        DarkUtils.REGISTRY.registerBlock(blockSneakyGhost, "sneaky_ghost");

        blockSneakyTorch = new BlockSneakyTorch();
        DarkUtils.REGISTRY.registerBlock(blockSneakyTorch, "sneaky_torch");

        blockSneakyObsidian = new BlockSneaky().setHardness(20f).setResistance(2000f);
        DarkUtils.REGISTRY.registerBlock(blockSneakyObsidian, "sneaky_obsidian");

        blockSneakyPlate = new BlockSneakyPressurePlate();
        DarkUtils.REGISTRY.registerBlock(blockSneakyPlate, "sneaky_plate");

        blockSneakyBedrock = new BlockSneakyBedrock();
        DarkUtils.REGISTRY.registerBlock(blockSneakyBedrock, "sneaky_bedrock");

        blockSneakyButton = new BlockSneakyButton();
        DarkUtils.REGISTRY.registerBlock(blockSneakyButton, "sneaky_button");
    }

    @Override
    public void setupConfiguration (Configuration config) {

        opacity = config.getBoolean("Opacity", this.configName, true, "When true, all sneaky blocks will let no light through. When disabled, all light will be let through.");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientInit () {

        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new BlockColorSneaky(), blockSneakyBlock, blockSneakyLever, blockSneakyGhost, blockSneakyTorch, blockSneakyObsidian, blockSneakyPlate, blockSneakyBedrock);

        this.registerSneakyModel(blockSneakyBlock, "sneaky_default");
        this.registerSneakyModel(blockSneakyLever, "sneaky_lever");
        this.registerSneakyModel(blockSneakyGhost, "sneaky_default");
        this.registerSneakyModel(blockSneakyTorch, "sneaky_torch");
        this.registerSneakyModel(blockSneakyObsidian, "sneaky_default");
        this.registerSneakyModel(blockSneakyPlate, "sneaky_plate");
        this.registerSneakyModel(blockSneakyBedrock, "sneaky_default");
        this.registerSneakyModel(blockSneakyButton, "sneaky_button");
    }

    @Override
    public void onPreRecipe () {

        DarkUtils.REGISTRY.addShapedRecipe("sneaky_block", new ItemStack(blockSneakyBlock, 8), "rrr", "rsr", "rrr", 'r', OreDictUtils.STONE, 's', new ItemStack(FeatureMaterial.itemMaterial, 1, 2));
        DarkUtils.REGISTRY.addShapelessRecipe("sneaky_lever", new ItemStack(blockSneakyLever), blockSneakyBlock, Blocks.LEVER);
        DarkUtils.REGISTRY.addShapelessRecipe("sneaky_ghost", new ItemStack(blockSneakyGhost), blockSneakyBlock, Blocks.WOOL);
        DarkUtils.REGISTRY.addShapelessRecipe("sneaky_torch", new ItemStack(blockSneakyTorch), blockSneakyBlock, Blocks.TORCH);
        DarkUtils.REGISTRY.addShapelessRecipe("sneaky_obsidian", new ItemStack(blockSneakyObsidian), blockSneakyBlock, Blocks.OBSIDIAN);
        DarkUtils.REGISTRY.addShapelessRecipe("sneaky_plate", new ItemStack(blockSneakyPlate), blockSneakyBlock, Blocks.STONE_PRESSURE_PLATE);
        DarkUtils.REGISTRY.addShapelessRecipe("sneaky_bedrock", new ItemStack(blockSneakyBedrock), blockSneakyBlock, Blocks.BEDROCK);
        DarkUtils.REGISTRY.addShapelessRecipe("sneaky_button", new ItemStack(blockSneakyButton), blockSneakyBlock, Blocks.STONE_BUTTON);
    }

    @Override
    public boolean usesEvents () {

        return true;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onModelBake (ModelBakeEvent event) {

        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky", "normal"), new ModelSneakyBlock());
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky_lever", "powered=true"), new ModelSneakyBlock());
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky_lever", "powered=false"), new ModelSneakyBlock());
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky_button", "powered=true"), new ModelSneakyBlock());
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky_button", "powered=false"), new ModelSneakyBlock());
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky_ghost", "normal"), new ModelSneakyBlock());
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky_torch", "normal"), new ModelSneakyBlock());
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky_obsidian", "normal"), new ModelSneakyBlock());
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky_plate", "powered=true"), new ModelSneakyBlock());
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky_plate", "powered=false"), new ModelSneakyBlock());
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky_bedrock", "normal"), new ModelSneakyBlock());
    }

    /**
     * Registers a sneaky block model.
     *
     * @param block The block to register the model for.
     * @param name The name of the sneaky block model.
     * @param useDefault Whether or not the default model should be used.
     */
    @SideOnly(Side.CLIENT)
    public void registerSneakyModel (Block block, String name) {

        final Item item = Item.getItemFromBlock(block);
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("darkutils:" + name, "inventory"));
    }
}
