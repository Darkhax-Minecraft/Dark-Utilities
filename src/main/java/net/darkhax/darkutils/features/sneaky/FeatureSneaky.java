package net.darkhax.darkutils.features.sneaky;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
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
    }

    @Override
    public void setupConfiguration (Configuration config) {

        opacity = config.getBoolean("Opacity", this.configName, true, "When true, all sneaky blocks will let no light through. When disabled, all light will be let through.");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {
        
        IStateMapper mapper = new StateMapSneaky();
        ModelLoader.setCustomStateMapper(blockSneakyLever, mapper);
        ModelLoader.setCustomStateMapper(blockSneakyPlate, mapper);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientInit () {

        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new BlockColorSneaky(), blockSneakyBlock, blockSneakyLever, blockSneakyGhost, blockSneakyTorch, blockSneakyObsidian, blockSneakyPlate, blockSneakyBedrock);
    }

    @Override
    public boolean usesEvents () {

        return true;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onModelBake (ModelBakeEvent event) {
        
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky", "normal"), new ModelSneakyBlock());
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky_lever", "normal"), new ModelSneakyBlock());
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky_ghost", "normal"), new ModelSneakyBlock());
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky_torch", "normal"), new ModelSneakyBlock());
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky_obsidian", "normal"), new ModelSneakyBlock());
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky_plate", "normal"), new ModelSneakyBlock());
        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky_bedrock", "normal"), new ModelSneakyBlock());
        
        this.registerSneakyModel(blockSneakyBlock, "sneaky_default");
        this.registerSneakyModel(blockSneakyLever, "sneaky_lever");
        this.registerSneakyModel(blockSneakyGhost, "sneaky_default");
        this.registerSneakyModel(blockSneakyTorch, "sneaky_torch");
        this.registerSneakyModel(blockSneakyObsidian, "sneaky_default");
        this.registerSneakyModel(blockSneakyPlate, "sneaky_plate");
        this.registerSneakyModel(blockSneakyBedrock, "sneaky_default");
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
