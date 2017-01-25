package net.darkhax.darkutils.features.chests;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class StateMapChest extends StateMapperBase {

    @Override
    protected ModelResourceLocation getModelResourceLocation (IBlockState state) {

        return new ModelResourceLocation("minecraft:stone", "normal");
    }
}