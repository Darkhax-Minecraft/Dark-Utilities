package net.darkhax.darkutils;

import net.darkhax.bookshelf.registry.RegistryHelper;
import net.darkhax.darkutils.features.flatblocks.BlockFlatTile;
import net.darkhax.darkutils.features.flatblocks.BlockFlatTileRotating;
import net.darkhax.darkutils.features.flatblocks.BlockFlatTileRotatingTicking;
import net.darkhax.darkutils.features.flatblocks.TileEntityTickingEffect;
import net.darkhax.darkutils.features.flatblocks.collision.CollisionEffect;
import net.darkhax.darkutils.features.flatblocks.collision.CollisionEffectImport;
import net.darkhax.darkutils.features.flatblocks.collision.CollisionEffectPush;
import net.darkhax.darkutils.features.flatblocks.tick.TickEffect;
import net.darkhax.darkutils.features.flatblocks.tick.TickEffectExport;
import net.darkhax.darkutils.features.slimecrucible.BlockSlimeCrucible;
import net.darkhax.darkutils.features.slimecrucible.TileEntitySlimeCrucible;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;

public class Content {

    public final Block blankPlate;
    public final Block vectorPlate;
    public final Block vectorPlateFast;
    public final Block vectorPlateHyper;
    public final Block exportPlate;
    public final Block exportPlateFast;
    public final Block exportPlateHyper;
    public final Block importPlate;
    public final Block importPlateFast;
    public final Block importPlateHyper;
    public final Block slimeCrucible;

    public final TileEntityType<TileEntityTickingEffect> tileTickingEffect;
    public final TileEntityType<TileEntitySlimeCrucible> tileSlimeCrucible; 
    
    public Content (RegistryHelper registry) {

        final CollisionEffect effectVectorPlate = new CollisionEffectPush(0.06d);
        final CollisionEffect effectVectorPlateFast = new CollisionEffectPush(0.3d);
        final CollisionEffect effectVectorPlateExtreme = new CollisionEffectPush(1.5d);

        this.blankPlate = registry.registerBlock(new BlockFlatTile(), "blank_plate");
        this.vectorPlate = registry.registerBlock(new BlockFlatTileRotating(effectVectorPlate), "vector_plate");
        this.vectorPlateFast = registry.registerBlock(new BlockFlatTileRotating(effectVectorPlateFast), "vector_plate_fast");
        this.vectorPlateHyper = registry.registerBlock(new BlockFlatTileRotating(effectVectorPlateExtreme), "vector_plate_extreme");

        final TickEffect exportEffect = new TickEffectExport();
        this.exportPlate = registry.registerBlock(new BlockFlatTileRotatingTicking(effectVectorPlate, exportEffect, 10), "export_plate");
        this.exportPlateFast = registry.registerBlock(new BlockFlatTileRotatingTicking(effectVectorPlate, exportEffect, 5), "export_plate_fast");
        this.exportPlateHyper = registry.registerBlock(new BlockFlatTileRotatingTicking(effectVectorPlate, exportEffect, 1), "export_plate_extreme");

        this.importPlate = registry.registerBlock(new BlockFlatTileRotating(new CollisionEffectImport(0.06d, 1)), "import_plate");
        this.importPlateFast = registry.registerBlock(new BlockFlatTileRotating(new CollisionEffectImport(0.3d, 16)), "import_plate_fast");
        this.importPlateHyper = registry.registerBlock(new BlockFlatTileRotating(new CollisionEffectImport(1.5d, 32)), "import_plate_extreme");

        this.slimeCrucible = registry.registerBlock(new BlockSlimeCrucible(BlockFlatTile.BLOCK_PROPERTIES), "slime_crucible");

        this.tileTickingEffect = registry.registerTileEntity(TileEntityTickingEffect::new, "ticking_tile", exportPlate, exportPlateFast, exportPlateHyper);
        this.tileSlimeCrucible = registry.registerTileEntity(TileEntitySlimeCrucible::new, "slime_crucible", slimeCrucible);
    }
}