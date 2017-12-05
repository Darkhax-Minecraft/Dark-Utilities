package net.darkhax.darkutils.features.shulkerpearl;

import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.shulkerpearl.ShulkerDataHandler.ICustomData;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

@DUFeature(name = "Shulker Pearls", description = "Adds shulker pearls and related content")
public class FeatureShulkerPearlItem extends Feature {

    public static Item itemShulkerPearl;

    public static Block blockShulkerPearl;

    private boolean harvestablePearls = true;

    private int maxCooldown = 6000;

    @Override
    public void onPreInit () {

        itemShulkerPearl = DarkUtils.REGISTRY.registerItem(new ItemShulkerPearl(), "shulker_pearl");
        blockShulkerPearl = new BlockShulkerPearl();
        DarkUtils.REGISTRY.registerBlock(blockShulkerPearl, new ItemBlockBasic(blockShulkerPearl, BlockShulkerPearl.types, false), "pearl_block");

        if (this.harvestablePearls) {
            ShulkerDataHandler.init();
        }
    }

    @Override
    public void onPreRecipe () {

        DarkUtils.REGISTRY.addShapedRecipe("compact_pearl", new ItemStack(blockShulkerPearl, 32, 0), "xxx", "xsx", "xxx", 'x', itemShulkerPearl, 's', OreDictUtils.ENDSTONE);
        DarkUtils.REGISTRY.addShapedRecipe("convert_pearl_1", new ItemStack(blockShulkerPearl, 4, 1), "xx ", "xx ", 'x', new ItemStack(blockShulkerPearl, 1, 0));
        DarkUtils.REGISTRY.addShapedRecipe("convert_pearl_2", new ItemStack(blockShulkerPearl, 4, 2), "xx ", "xx ", 'x', new ItemStack(blockShulkerPearl, 1, 1));
        DarkUtils.REGISTRY.addShapedRecipe("convert_pearl_3", new ItemStack(blockShulkerPearl, 4, 3), "xx ", "xx ", 'x', new ItemStack(blockShulkerPearl, 1, 2));
        DarkUtils.REGISTRY.addShapelessRecipe("deconstruct_pearl", new ItemStack(itemShulkerPearl), new ItemStack(blockShulkerPearl, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(blockShulkerPearl, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(blockShulkerPearl, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(blockShulkerPearl, 1, OreDictionary.WILDCARD_VALUE));
    }

    @Override
    public void onInit () {

        OreDictionary.registerOre("blockPearl", new ItemStack(blockShulkerPearl, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("gemPearl", itemShulkerPearl);
    }

    @Override
    public void setupConfiguration (Configuration config) {

        this.harvestablePearls = config.getBoolean("Harvest Pearls", this.configName, true, "Should pearls be harvestable from shulkers?");
        this.maxCooldown = config.getInt("Shulker Cooldown", this.configName, 6000, 0, Integer.MAX_VALUE, "The pearl harvest cooldown tile, in ticks");
    }

    @Override
    public boolean usesEvents () {

        return true;
    }

    @SubscribeEvent
    public void onLivingDrops (LivingDropsEvent event) {

        if (event.getEntity() instanceof EntityShulker) {

            final ICustomData data = ShulkerDataHandler.getData(event.getEntity());

            if (data != null && data.getCooldown() <= 0) {

                final Vec3d pos = event.getEntity().getPositionVector();
                event.getDrops().add(new EntityItem(event.getEntity().getEntityWorld(), pos.x, pos.y, pos.z, new ItemStack(itemShulkerPearl)));
                data.setCooldown(this.maxCooldown);
            }
        }
    }

    @SubscribeEvent
    public void onEntityInteract (EntityInteract event) {

        if (event.getSide().equals(Side.SERVER) && this.harvestablePearls && event.getTarget() instanceof EntityShulker) {

            final ICustomData data = ShulkerDataHandler.getData(event.getTarget());

            if (data != null && data.getCooldown() <= 0) {

                event.getTarget().entityDropItem(new ItemStack(itemShulkerPearl), 0.5f);
                data.setCooldown(this.maxCooldown);
            }
        }
    }

    @SubscribeEvent
    public void onEntityUpdate (LivingUpdateEvent event) {

        if (this.harvestablePearls && event.getEntity() instanceof EntityShulker) {

            final ICustomData data = ShulkerDataHandler.getData(event.getEntity());
            final int current = data.getCooldown();

            if (data != null && current > 0) {
                data.setCooldown(current - 1);
            }
        }
    }
}
