package net.darkhax.darkutils.features.sneaky;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.darkhax.bookshelf.data.Blockstates;
import net.darkhax.bookshelf.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSneakyBlock implements IBakedModel {

    @Override
    public List<BakedQuad> getQuads (IBlockState state, EnumFacing side, long rand) {

        final Minecraft mc = Minecraft.getMinecraft();
        final BlockRenderLayer layer = MinecraftForgeClient.getRenderLayer();

        if (!(state.getBlock() instanceof BlockSneaky)) {
            return mc.getBlockRendererDispatcher().getBlockModelShapes().getModelManager().getMissingModel().getQuads(state, side, rand);
        }

        final IBlockState heldState = ((IExtendedBlockState) state).getValue(Blockstates.HELD_STATE);
        final IBlockAccess heldWorld = ((IExtendedBlockState) state).getValue(Blockstates.BLOCK_ACCESS);
        final BlockPos heldPos = ((IExtendedBlockState) state).getValue(Blockstates.BLOCKPOS);

        if (heldWorld == null || heldPos == null) {
            return ImmutableList.of();
        }

        if (heldState == null && layer == BlockRenderLayer.SOLID) {

            final Block block = state.getBlock();
            IBlockState defaultState = Blocks.FIRE.getDefaultState();

            if (block == FeatureSneaky.blockSneakyBlock) {

                defaultState = Blocks.COBBLESTONE.getDefaultState();
            }

            else if (block == FeatureSneaky.blockSneakyLever) {

                defaultState = Blocks.LEVER.getDefaultState();
            }

            else if (block == FeatureSneaky.blockSneakyGhost) {

                defaultState = Blocks.PORTAL.getDefaultState();
            }

            else if (block == FeatureSneaky.blockSneakyTorch) {

                defaultState = Blocks.TORCH.getDefaultState();
            }

            else if (block == FeatureSneaky.blockSneakyObsidian) {

                defaultState = Blocks.OBSIDIAN.getDefaultState();
            }

            else if (block == FeatureSneaky.blockSneakyPlate) {

                defaultState = Blocks.STONE_PRESSURE_PLATE.getDefaultState();
            }

            else if (block == FeatureSneaky.blockSneakyBedrock) {

                defaultState = Blocks.BEDROCK.getDefaultState();
            }

            return RenderUtils.getModelForState(defaultState).getQuads(defaultState, side, rand);
        }

        else if (layer != null && heldState != null && heldState.getBlock().canRenderInLayer(heldState, layer)) {

            final IBlockState actualState = heldState.getBlock().getActualState(heldState, new SneakyBlockAccess(heldWorld), heldPos);
            final IBakedModel model = mc.getBlockRendererDispatcher().getBlockModelShapes().getModelForState(actualState);
            final IBlockState extended = heldState.getBlock().getExtendedState(actualState, new SneakyBlockAccess(heldWorld), heldPos);
            return model.getQuads(extended, side, rand);
        }

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

    @Override
    public ItemOverrideList getOverrides () {

        return ItemOverrideList.NONE;
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
        private SneakyBlockAccess (IBlockAccess access) {

            this.access = access;
        }

        @Override
        public TileEntity getTileEntity (BlockPos pos) {

            return this.access.getTileEntity(pos);
        }

        @Override
        public int getCombinedLight (BlockPos pos, int light) {

            return 15 << 20 | 15 << 4;
        }

        @Override
        public IBlockState getBlockState (BlockPos pos) {

            IBlockState state = this.access.getBlockState(pos);

            if (state.getBlock() instanceof BlockSneaky) {
                state = ((TileEntitySneaky) this.access.getTileEntity(pos)).heldState;
            }

            return state == null ? Blocks.AIR.getDefaultState() : state;
        }

        @Override
        public boolean isAirBlock (BlockPos pos) {

            return this.access.isAirBlock(pos);
        }

        @Override
        public int getStrongPower (BlockPos pos, EnumFacing direction) {

            return this.access.getStrongPower(pos, direction);
        }

        @Override
        public WorldType getWorldType () {

            return this.access.getWorldType();
        }

        @Override
        public boolean isSideSolid (BlockPos pos, EnumFacing side, boolean _default) {

            return this.access.isSideSolid(pos, side, _default);
        }

        @Override
        public Biome getBiome (BlockPos pos) {

            return this.access.getBiome(pos);
        }
    }
}