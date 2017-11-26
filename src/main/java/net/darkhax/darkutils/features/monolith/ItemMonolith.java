package net.darkhax.darkutils.features.monolith;

import java.util.List;

import javax.annotation.Nullable;

import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.darkhax.bookshelf.util.PlayerUtils;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMonolith extends ItemBlockBasic {

    public ItemMonolith (Block block, String[] names) {

        super(block, names);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        if (PlayerUtils.getClientPlayer() != null && worldIn != null) {

            final BlockPos playerPos = PlayerUtils.getClientPlayer().getPosition();

            if (!TileEntityMonolith.validatePosition(worldIn, null, playerPos, false)) {

                tooltip.add(TextFormatting.RED + I18n.format("tooltip.darkutils.monolith.invalid"));
            }
        }
    }
}