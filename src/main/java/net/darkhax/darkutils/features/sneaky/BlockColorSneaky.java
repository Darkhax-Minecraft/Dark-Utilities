package net.darkhax.darkutils.features.sneaky;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockColorSneaky implements IBlockColor {

    @Override
    public int colorMultiplier (IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {

        if (world != null && pos != null) {

            final TileEntity tile = world.getTileEntity(pos);

            if (tile instanceof TileEntitySneaky && !tile.isInvalid()) {

                final TileEntitySneaky sneaky = (TileEntitySneaky) tile;

                if (sneaky.heldState != null) {
                    return Minecraft.getMinecraft().getBlockColors().colorMultiplier(sneaky.heldState, world, pos, tintIndex);
                }
            }

            return 16777215;
        }
        else {
            return 16777215;
        }
    }
}
