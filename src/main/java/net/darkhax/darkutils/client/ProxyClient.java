package net.darkhax.darkutils.client;

import net.darkhax.darkutils.blocks.BlockFilter;
import net.darkhax.darkutils.blocks.BlockTrapEffect;
import net.darkhax.darkutils.client.renderer.RenderFactoryFactory;
import net.darkhax.darkutils.client.renderer.color.PotionColorHandler;
import net.darkhax.darkutils.client.renderer.tile.RenderShulkerPearl;
import net.darkhax.darkutils.client.statemap.StateMapSneaky;
import net.darkhax.darkutils.common.ProxyCommon;
import net.darkhax.darkutils.entity.EntityFakeTNT;
import net.darkhax.darkutils.handler.ContentHandler;
import net.darkhax.darkutils.items.ItemMaterial;
import net.darkhax.darkutils.tileentity.TileEntityShulkerPearl;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ProxyClient extends ProxyCommon {
    
    /**
     * A state map instance for the sneaky block model.
     */
    private static final StateMapperBase SNEAKY_MAP = new StateMapSneaky();
    
    @Override
    public void onPreInit () {
        
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        
        // Block Models
        registerBlockInvModel(ContentHandler.blockTrap, BlockTrapEffect.EnumType.getTypes());
        registerBlockInvModel(ContentHandler.blockEnderTether, 0);
        registerBlockInvModel(ContentHandler.blockTrapMovement, 0);
        registerBlockInvModel(ContentHandler.blockTrapMovementFast, 0);
        registerBlockInvModel(ContentHandler.blockTrapMovementHyper, 0);
        registerBlockInvModel(ContentHandler.blockGrate, 0);
        registerBlockInvModel(ContentHandler.blockFilter, BlockFilter.EnumType.getTypes());
        registerBlockInvModel(ContentHandler.blockTimer, 0);
        registerBlockInvModel(ContentHandler.blockAntiSlime, 0);
        registerBlockInvModel(ContentHandler.blockDetector, 0);
        registerBlockInvModel(ContentHandler.blockCakeEPlus, 0);
        registerBlockInvModel(ContentHandler.blockFakeTNT, 0);
        
        // Item Models
        registerItemInvModel(ContentHandler.itemMaterial, ItemMaterial.varients);
        registerItemInvModel(ContentHandler.itemPotion, 0, "bottle_drinkable");
        registerItemInvModel(ContentHandler.itemPotion, 1, "bottle_drinkable");
        registerItemInvModel(ContentHandler.itemCharmPortal, 0);
        
        // Sneaky Block Models
        this.registerSneakyModel(ContentHandler.blockSneakyBlock, "sneaky_default", false);
        this.registerSneakyModel(ContentHandler.blockSneakyLever, "sneaky_lever", false);
        this.registerSneakyModel(ContentHandler.blockSneakyGhost, "sneaky_default", true);
        this.registerSneakyModel(ContentHandler.blockSneakyTorch, "sneaky_torch", false);
        this.registerSneakyModel(ContentHandler.blockSneakyObsidian, "sneaky_default", true);
        this.registerSneakyModel(ContentHandler.blockSneakyPlate, "sneaky_plate", false);
        
        // Entity renders
        RenderingRegistry.registerEntityRenderingHandler(EntityFakeTNT.class, new RenderFactoryFactory.RenderFactoryTNT());
        
        // Tile Entity Renders
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShulkerPearl.class, new RenderShulkerPearl());
    }
    
    @Override
    public void onInit () {
        
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new PotionColorHandler(), ContentHandler.itemPotion);
    }
    
    @Override
    public void onPostInit () {
    
    }
    
    /**
     * Registers inventory models for a block that uses meta data.
     * 
     * @param block The block to register models for.
     * @param variants The names of the models to use in order of meta data.
     */
    private void registerBlockInvModel (Block block, String[] variants) {
        
        registerItemInvModel(Item.getItemFromBlock(block), variants);
    }
    
    /**
     * Registers inventory models for a block.
     * 
     * @param block The block to register models for.
     * @param meta The meta data to register the model for.
     */
    private void registerBlockInvModel (Block block, int meta) {
        
        registerItemInvModel(Item.getItemFromBlock(block), meta);
    }
    
    /**
     * Registers inventory models for an item.
     * 
     * @param item The item to register a model for.
     * @param meta The meta data to register the model for.
     * @param model The name of the model to register.
     */
    private void registerItemInvModel (Item item, int meta, String model) {
        
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(model, "inventory"));
    }
    
    /**
     * Registers inventory models for an item that uses meta data.
     * 
     * @param item The item to register a model for.
     * @param variants The names of the models to use, in order of meta data.
     */
    private void registerItemInvModel (Item item, String[] variants) {
        
        for (int meta = 0; meta < variants.length; meta++)
            ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName().getResourceDomain() + ":" + variants[meta], "inventory"));
    }
    
    /**
     * Registers inventory models for an item.
     * 
     * @param item The item to registers a model for.
     * @param meta The meta data to register the model for.
     */
    private void registerItemInvModel (Item item, int meta) {
        
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
    }
    
    /**
     * Registers a sneaky block model.
     * 
     * @param block The block to register the model for.
     * @param name The name of the sneaky block model.
     * @param useDefault Whether or not the default model should be used.
     */
    private void registerSneakyModel (Block block, String name, boolean useDefault) {
        
        final Item item = Item.getItemFromBlock(block);
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("darkutils:" + name, "inventory"));
        
        if (!useDefault)
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("darkutils:" + name, "normal"));
            
        ModelLoader.setCustomStateMapper(block, SNEAKY_MAP);
    }
}