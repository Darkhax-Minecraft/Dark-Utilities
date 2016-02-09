package net.darkhax.darkutils.client.statemap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class StateMapSneaky extends StateMapperBase {
    
    @Override
    protected ModelResourceLocation getModelResourceLocation (IBlockState state) {
        
        return new ModelResourceLocation("darkutils:sneaky", "normal");
    }
}