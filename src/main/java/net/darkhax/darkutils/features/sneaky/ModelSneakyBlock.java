package net.darkhax.darkutils.features.sneaky;

import java.util.ArrayList;
import java.util.List;

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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSneakyBlock implements IBakedModel {

    @Override
    public List<BakedQuad> getQuads (IBlockState state, EnumFacing side, long rand) {

        final BlockRenderLayer layer = MinecraftForgeClient.getRenderLayer();
        final List<BakedQuad> quads = new ArrayList<>();

        final IBakedModel model = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(Blocks.END_ROD.getDefaultState());
        quads.addAll(model.getQuads(Blocks.END_ROD.getDefaultState(), side, rand));

        if (layer == Blocks.GLASS.getBlockLayer()) {

            final IBakedModel model2 = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(Blocks.GLASS.getDefaultState());
            quads.addAll(model2.getQuads(Blocks.GLASS.getDefaultState(), side, rand));
        }
        return quads;
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