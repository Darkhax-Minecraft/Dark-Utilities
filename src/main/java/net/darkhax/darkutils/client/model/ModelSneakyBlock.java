package net.darkhax.darkutils.client.model;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.darkhax.bookshelf.lib.BlockStates;
import net.darkhax.darkutils.blocks.BlockSneaky;
import net.darkhax.darkutils.blocks.BlockSneakyLever;
import net.darkhax.darkutils.blocks.BlockSneakyTorch;
import net.darkhax.darkutils.tileentity.TileEntitySneaky;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ISmartBlockModel;
import net.minecraftforge.common.property.IExtendedBlockState;

public class ModelSneakyBlock implements ISmartBlockModel {
    
    @Override
    public IBakedModel handleBlockState (IBlockState state) {
        
        if (!(state.getBlock() instanceof BlockSneaky))
            return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelManager().getMissingModel();
            
        Minecraft mc = Minecraft.getMinecraft();
        EnumWorldBlockLayer layer = MinecraftForgeClient.getRenderLayer();
        
        IBlockState heldState = ((IExtendedBlockState) state).getValue(BlockStates.HELD_STATE);
        IBlockAccess heldWorld = ((IExtendedBlockState) state).getValue(BlockStates.BLOCK_ACCESS);
        BlockPos heldPos = ((IExtendedBlockState) state).getValue(BlockStates.BLOCKPOS);
        
        if (heldWorld == null || heldPos == null)
            return this;
            
        if (heldState == null && layer == EnumWorldBlockLayer.SOLID) {
            
            Block block = state.getBlock();
            
            if (block instanceof BlockSneakyLever)
                return mc.getBlockRendererDispatcher().getBlockModelShapes().getModelManager().getModel(new ModelResourceLocation("darkutils:sneaky_lever"));
                
            else if (block instanceof BlockSneakyTorch)
                return mc.getBlockRendererDispatcher().getBlockModelShapes().getModelManager().getModel(new ModelResourceLocation("darkutils:sneaky_torch"));
                
            else
                return mc.getBlockRendererDispatcher().getBlockModelShapes().getModelManager().getModel(new ModelResourceLocation("darkutils:sneaky_default"));
        }
        
        else if (heldState != null && heldState.getBlock().canRenderInLayer(layer)) {
            
            IBlockState actualState = heldState.getBlock().getActualState(heldState, new SneakyBlockAccess(heldWorld), heldPos);
            IBakedModel model = mc.getBlockRendererDispatcher().getBlockModelShapes().getModelForState(actualState);
            
            if (model instanceof ISmartBlockModel)
                model = ((ISmartBlockModel) model).handleBlockState(heldState.getBlock().getExtendedState(actualState, new SneakyBlockAccess(heldWorld), heldPos));
                
            return model;
        }
        
        return this;
    }
    
    @Override
    public List<BakedQuad> getFaceQuads (EnumFacing p_177551_1_) {
        
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
    
    private static class SneakyBlockAccess implements IBlockAccess {
        
        /**
         * The IBlockAccess instance used by the SneakyBlockAccess.
         */
        private final IBlockAccess access;
        
        /**
         * Constructs the SneakyBlockAccess with an existing IBlockAccess.
         * 
         * @param access The base block state to use for the SneakyBlockState.
         */
        private SneakyBlockAccess(IBlockAccess access) {
            
            this.access = access;
        }
        
        @Override
        public TileEntity getTileEntity (BlockPos pos) {
            
            return access.getTileEntity(pos);
        }
        
        @Override
        public int getCombinedLight (BlockPos pos, int light) {
            
            return 15 << 20 | 15 << 4;
        }
        
        @Override
        public IBlockState getBlockState (BlockPos pos) {
            
            IBlockState state = access.getBlockState(pos);
            
            if (state.getBlock() instanceof BlockSneaky)
                state = ((TileEntitySneaky) access.getTileEntity(pos)).heldState;
                
            return state == null ? Blocks.air.getDefaultState() : state;
        }
        
        @Override
        public boolean isAirBlock (BlockPos pos) {
            
            return access.isAirBlock(pos);
        }
        
        @Override
        public BiomeGenBase getBiomeGenForCoords (BlockPos pos) {
            
            return access.getBiomeGenForCoords(pos);
        }
        
        @Override
        public boolean extendedLevelsInChunkCache () {
            
            return access.extendedLevelsInChunkCache();
        }
        
        @Override
        public int getStrongPower (BlockPos pos, EnumFacing direction) {
            
            return access.getStrongPower(pos, direction);
        }
        
        @Override
        public WorldType getWorldType () {
            
            return access.getWorldType();
        }
        
        @Override
        public boolean isSideSolid (BlockPos pos, EnumFacing side, boolean _default) {
            
            return access.isSideSolid(pos, side, _default);
        }
    }
}