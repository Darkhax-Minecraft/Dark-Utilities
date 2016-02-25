package net.darkhax.darkutils.client.model;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.darkhax.bookshelf.lib.BlockStates;
import net.darkhax.darkutils.blocks.BlockSneakyLever;
import net.darkhax.darkutils.blocks.BlockSneakyTorch;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ISmartBlockModel;
import net.minecraftforge.common.property.IExtendedBlockState;

public class ModelSneakyBlock implements ISmartBlockModel {
    
    @Override
    public IBakedModel handleBlockState (IBlockState state) {
        
        Minecraft mc = Minecraft.getMinecraft();
        EnumWorldBlockLayer layer = MinecraftForgeClient.getRenderLayer();
        IBlockState heldState = ((IExtendedBlockState) state).getValue(BlockStates.HELD_STATE);
        
        if (heldState == null && layer == EnumWorldBlockLayer.SOLID) {
            
            ModelResourceLocation path;
            
            Block block = state.getBlock();
            if (block instanceof BlockSneakyLever)
                path = new ModelResourceLocation("darkutils:sneaky_lever");
                
            else if (block instanceof BlockSneakyTorch)
                path = new ModelResourceLocation("darkutils:sneaky_torch");
                
            else
                path = new ModelResourceLocation("darkutils:sneaky_default");
            return mc.getBlockRendererDispatcher().getBlockModelShapes().getModelManager().getModel(path);
        }
        
        else if (heldState != null && heldState.getBlock().canRenderInLayer(layer)) {
            
            IBakedModel model = mc.getBlockRendererDispatcher().getBlockModelShapes().getModelForState(heldState);
            
            if (model instanceof ISmartBlockModel)
                model = ((ISmartBlockModel) model).handleBlockState(heldState);
                
            return model;
        }
        
        return this;
    }
    
    @Override
    public List<BakedQuad> getFaceQuads (EnumFacing facing) {
        
        return ImmutableList.of();
    }
    
    @Override
    public List<BakedQuad> getGeneralQuads () {
        
        return ImmutableList.of();
    }
    
    @Override
    public boolean isAmbientOcclusion () {
        
        return true;
    }
    
    @Override
    public boolean isGui3d () {
        
        return true;
    }
    
    @Override
    public boolean isBuiltInRenderer () {
        
        return false;
    }
    
    @Override
    public TextureAtlasSprite getParticleTexture () {
        
        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("darkutils:blocks/trap_base");
    }
    
    @Override
    public ItemCameraTransforms getItemCameraTransforms () {
        
        return ItemCameraTransforms.DEFAULT;
    }
}