package net.darkhax.darkutils.features.trap;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.darkutils.features.material.FeatureMaterial;
import net.darkhax.darkutils.handler.FakePlayerHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IStringSerializable;

public enum TrapType implements IStringSerializable {

    POISON(0, "poison", Items.SPIDER_EYE, new AffecterPotion(new PotionEffect(MobEffects.POISON, 100))),
    WEAKNESS(1, "weakness", Items.FERMENTED_SPIDER_EYE, new AffecterPotion(new PotionEffect(MobEffects.WEAKNESS, 60))),
    HARMING(2, "harming", Items.IRON_SWORD, (entity) -> entity.attackEntityFrom(DamageSource.MAGIC, 2.5f)),
    SLOWNESS(3, "slowness", Blocks.SOUL_SAND, new AffecterPotion(new PotionEffect(MobEffects.SLOWNESS, 60, 2))),
    FIRE(4, "fire", Items.FLINT_AND_STEEL, (entity) -> entity.setFire(2)),
    WITHER(5, "wither", new ItemStack(FeatureMaterial.itemMaterial, 1, 0), new AffecterPotion(new PotionEffect(MobEffects.WITHER, 60))),
    MAIM(6, "maim", Items.NETHER_STAR, new AffecterMaim()),
    PLAYER(7, "player", Items.SKULL, (entity) -> FakePlayerHandler.causePlayerDamage(entity, 2.5f));

    private static String[] nameList;

    public final int meta;

    public final String type;

    public final IAffecter affect;

    public final Object crafting;

    private TrapType (int meta, String name, Object crafting, IAffecter affect) {

        this.meta = meta;
        this.type = name;
        this.crafting = crafting;
        this.affect = affect;
    }

    @Override
    public String getName () {

        return this.type;
    }

    @Override
    public String toString () {

        return this.type;
    }

    public static TrapType fromMeta (int meta) {

        for (final TrapType type : TrapType.values()) {
            if (type.meta == meta) {
                return type;
            }
        }

        return POISON;
    }

    public static String[] getTypes () {

        if (nameList != null) {
            return nameList;
        }

        final List<String> names = new ArrayList<>();

        for (final TrapType type : TrapType.values()) {
            names.add(type.type);
        }

        nameList = names.toArray(new String[names.size()]);
        return nameList;
    }

    static interface IAffecter {

        void apply (EntityLivingBase entity);
    }

    private static class AffecterPotion implements IAffecter {

        private final PotionEffect effect;

        public AffecterPotion (PotionEffect effect) {

            this.effect = effect;
            this.effect.setCurativeItems(new ArrayList<ItemStack>());
        }

        @Override
        public void apply (EntityLivingBase entity) {

            if (entity.getActivePotionEffect(this.effect.getPotion()) == null) {

                entity.addPotionEffect(new PotionEffect(this.effect));
            }
        }
    }

    private static class AffecterMaim implements IAffecter {

        @Override
        public void apply (EntityLivingBase entity) {

            // Doesn't affect mobs with 1 or less health, bosses, players, TODO or configurable
            // blacklist
            if (entity.getMaxHealth() > 1.0f && entity.isNonBoss() && !(entity instanceof EntityPlayer)) {

                final IAttributeInstance inst = entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
                inst.setBaseValue(Math.max(inst.getBaseValue() - 1.0f, 1.0f));
            }
        }
    }

}