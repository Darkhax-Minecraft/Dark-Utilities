package net.darkhax.darkutils;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.darkutils.blocks.BlockTrapTile;
import net.darkhax.darkutils.common.ProxyCommon;
import net.darkhax.darkutils.items.ItemBlockBasic;
import net.darkhax.darkutils.libs.Constants;
import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, acceptedMinecraftVersions = Constants.MCVERSION)
public class DarkUtils {
    
    @SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
    public static ProxyCommon proxy;
    
    @Mod.Instance(Constants.MOD_ID)
    public static DarkUtils instance;
    
    Block blockTrap;
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        
        blockTrap = new BlockTrapTile();
        GameRegistry.registerBlock(blockTrap, ItemBlockBasic.class, "trap_tile", new Object[] { BlockTrapTile.EnumType.getTypes() });
        
        List<ModelResourceLocation> models = new ArrayList<ModelResourceLocation>();
        
        Item item = Item.getItemFromBlock(blockTrap);
        for (BlockTrapTile.EnumType varient : BlockTrapTile.EnumType.values())
            ModelLoader.setCustomModelResourceLocation(item, varient.meta, new ModelResourceLocation("darkutils:trap_" + varient.name()));
            
    }
}