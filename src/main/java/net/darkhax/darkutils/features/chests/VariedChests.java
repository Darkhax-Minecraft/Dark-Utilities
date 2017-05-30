package net.darkhax.darkutils.features.chests;

import net.darkhax.bookshelf.builder.ChestBuilder;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.Constants;

@DUFeature(name = "Chests", description = "Chests!!!!")
public class VariedChests extends Feature {

    @Override
    public void onPreInit () {

        final ChestBuilder builer = new ChestBuilder(Constants.MOD_ID, true);

    }
}