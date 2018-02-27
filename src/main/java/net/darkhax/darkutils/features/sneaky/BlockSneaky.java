package net.darkhax.darkutils.features.sneaky;

import net.darkhax.bookshelf.data.Blockstates;
import net.darkhax.bookshelf.lib.Constants;
import net.darkhax.bookshelf.util.ParticleUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockSneaky extends BlockContainer {

    public BlockSneaky () {

        super(Material.ROCK);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setDefaultState(((IExtendedBlockState) this.blockState.getBaseState()).withProperty(Blockstates.HELD_STATE, null).withProperty(Blockstates.BLOCK_ACCESS, null).withProperty(Blockstates.BLOCKPOS, null));

        if (FeatureSneaky.opacity) {
            this.setLightOpacity(255);
        }
    }

    @Override
    public boolean onBlockActivated (World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        final TileEntity tile = world.getTileEntity(pos);
        final ItemStack stack = playerIn.getHeldItemMainhand();

        if (tile instanceof TileEntitySneaky && !tile.isInvalid() && stack != null && stack.getItem() != null) {

            final TileEntitySneaky sneaky = (TileEntitySneaky) tile;
            final Block block = Block.getBlockFromItem(stack.getItem());

            if (block instanceof Block) {

                final IBlockState heldState = Block.getBlockFromItem(stack.getItem()).getStateFromMeta(stack.getItemDamage());

                if (heldState != null && isValidBlock(heldState)) {

                    sneaky.heldState = heldState;
                    world.notifyBlockUpdate(pos, state, state, 8);

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void onBlockPlacedBy (World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

        final TileEntity tile = worldIn.getTileEntity(pos);

        if (!tile.isInvalid() && tile instanceof TileEntitySneaky && placer instanceof EntityPlayer) {

            final TileEntitySneaky sneaky = (TileEntitySneaky) tile;
            sneaky.playerID = ((EntityPlayer) placer).getUniqueID().toString();
        }
    }

    @Override
    @Deprecated
    public EnumBlockRenderType getRenderType (IBlockState state) {

        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity (World world, int meta) {

        return new TileEntitySneaky();
    }

    @Override
    public BlockStateContainer createBlockState () {

        return new ExtendedBlockState(this, new IProperty[] {}, new IUnlistedProperty[] { Blockstates.HELD_STATE, Blockstates.BLOCK_ACCESS, Blockstates.BLOCKPOS });
    }

    @Override
    public int getMetaFromState (IBlockState state) {

        return 0;
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta (int meta) {

        return this.getDefaultState();
    }

    @Override
    public IBlockState getExtendedState (IBlockState state, IBlockAccess world, BlockPos pos) {

        state = ((IExtendedBlockState) state).withProperty(Blockstates.BLOCK_ACCESS, world).withProperty(Blockstates.BLOCKPOS, pos);

        if (world.getTileEntity(pos) instanceof TileEntitySneaky) {

            final TileEntitySneaky tile = (TileEntitySneaky) world.getTileEntity(pos);
            return ((IExtendedBlockState) state).withProperty(Blockstates.HELD_STATE, tile.heldState);
        }
        else {
            return state;
        }
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube (IBlockState state) {

        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube (IBlockState state) {

        return false;
    }

    @Override
    public boolean canRenderInLayer (IBlockState state, BlockRenderLayer layer) {

        return true;
    }

    @Override
    public boolean addLandingEffects (IBlockState state, WorldServer world, BlockPos pos, IBlockState iblockstate, EntityLivingBase entity, int count) {

        final TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileEntitySneaky && !tile.isInvalid()) {

            final TileEntitySneaky sneaky = (TileEntitySneaky) tile;

            if (sneaky.heldState != null) {

                world.spawnParticle(EnumParticleTypes.BLOCK_DUST, entity.posX, entity.posY, entity.posZ, count, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] { Block.getStateId(sneaky.heldState) });
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean addHitEffects (IBlockState state, World world, RayTraceResult hitPos, ParticleManager renderer) {

        final TileEntity tile = world.getTileEntity(hitPos.getBlockPos());

        if (tile instanceof TileEntitySneaky && !tile.isInvalid()) {

            final TileEntitySneaky sneaky = (TileEntitySneaky) tile;

            if (sneaky.heldState != null) {
                return ParticleUtils.spawnDigParticles(renderer, sneaky.heldState, world, hitPos.getBlockPos(), hitPos.sideHit);
            }
        }

        return false;
    }

    @Override
    public boolean addDestroyEffects (World world, BlockPos pos, ParticleManager renderer) {

        final TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileEntitySneaky && !tile.isInvalid()) {

            final TileEntitySneaky sneaky = (TileEntitySneaky) tile;

            if (sneaky.heldState != null) {
                return ParticleUtils.spawnBreakParticles(renderer, sneaky.heldState, world, pos);
            }
        }

        return false;
    }

    @Override
    @Deprecated
    public boolean shouldSideBeRendered (IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

        final TileEntity tile = blockAccess.getTileEntity(pos);

        if (tile instanceof TileEntitySneaky && !tile.isInvalid()) {

            final TileEntitySneaky sneaky = (TileEntitySneaky) tile;

            if (sneaky.heldState != null) {

                if (sneaky.heldState.getBlock() == Blocks.GLASS || sneaky.heldState.getBlock() == Blocks.GLASS_PANE) {

                    final IBlockState connected = blockAccess.getBlockState(pos.offset(side));

                    if (connected == sneaky.heldState) {
                        return false;
                    }
                    else if (connected.getBlock() instanceof BlockSneaky) {
                        return ((TileEntitySneaky) blockAccess.getTileEntity(pos.offset(side))).heldState != sneaky.heldState;
                    }
                }

                try {

                    return sneaky.heldState.shouldSideBeRendered(blockAccess, pos, side);
                }

                catch (final Exception e) {

                    Constants.LOG.warn("Issue with shouldSideBeRendered!", e);
                }
            }
        }

        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    /**
     * A check to see if a block is valid for the sneaky block. For a block to be valid, it
     * must be an opaque cube, or have a render type of 3. Tile entities are considered
     * invalid.
     *
     * @param state The current BlockState being tested.
     * @return boolean Whether or not the block is valid for the sneaky block.
     */
    private static boolean isValidBlock (IBlockState state) {

        final Block block = state.getBlock();
        return (block.isOpaqueCube(state) || block.getRenderType(state) == EnumBlockRenderType.MODEL) && !block.hasTileEntity(state) && block.getMaterial(state) != Material.AIR;
    }
}