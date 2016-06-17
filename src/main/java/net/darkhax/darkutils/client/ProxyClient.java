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
        registerBlockInvModel(ContentHandler.blockTrap, "trap", BlockTrapEffect.EnumType.getTypes());
        registerBlockInvModel(ContentHandler.blockEnderTether);
        registerBlockInvModel(ContentHandler.blockTrapMovement);
        registerBlockInvModel(ContentHandler.blockTrapMovementFast);
        registerBlockInvModel(ContentHandler.blockTrapMovementHyper);
        registerBlockInvModel(ContentHandler.blockGrate);
        registerBlockInvModel(ContentHandler.blockFilter, "filter", BlockFilter.EnumType.getTypes());
        registerBlockInvModel(ContentHandler.blockTimer);
        registerBlockInvModel(ContentHandler.blockAntiSlime);
        registerBlockInvModel(ContentHandler.blockDetector);
        registerBlockInvModel(ContentHandler.blockCakeEPlus);
        registerBlockInvModel(ContentHandler.blockFeeder);
        registerBlockInvModel(ContentHandler.blockFakeTNT);
        
        // Item Models
        registerItemInvModel(ContentHandler.itemMaterial, "material", ItemMaterial.varients);
        registerItemInvModel(ContentHandler.itemPotion, 0, "bottle_drinkable");
        registerItemInvModel(ContentHandler.itemPotion, 1, "bottle_drinkable");
        registerItemInvModel(ContentHandler.itemCharmPortal);
        
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
     * Registers inventory models for a block that uses meta data.
     * 
     * @param block The block to register models for.
     * @param prefix A prefix for the model names.
     * @param variants The names of the models to use in order of meta data.
     */
    private void registerBlockInvModel (Block block, String prefix, String[] variants) {
        
        registerItemInvModel(Item.getItemFromBlock(block), prefix, variants);
    }
    
    /**
     * Registers inventory models for a block.
     * 
     * @param block The block to register models for.
     */
    private void registerBlockInvModel (Block block) {
        
        registerItemInvModel(Item.getItemFromBlock(block), 0);
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
     * @param prefix A prefix to use on the variant names.
     * @param variants The names of the models to use, in order of meta data.
     */
    private void registerItemInvModel (Item item, String prefix, String[] variants) {
        
        for (int meta = 0; meta < variants.length; meta++)
            ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName().getResourceDomain() + ":" + prefix + "_" + variants[meta], "inventory"));
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
     */
    private void registerItemInvModel (Item item) {
        
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
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