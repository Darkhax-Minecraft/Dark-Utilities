package net.darkhax.darkutils.features.filter;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.bookshelf.lib.util.OreDictUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.IStringSerializable;

public enum FilterType implements IStringSerializable {
    
    PLAYER(0, "player", Items.GOLDEN_PICKAXE),
    UNDEAD(1, "undead", OreDictUtils.BONE),
    ARTHROPOD(2, "arthropod", Items.SPIDER_EYE),
    MONSTER(3, "monster", Items.ROTTEN_FLESH),
    ANIMAL(4, "animal", OreDictUtils.CROP_WHEAT),
    WATER(5, "water", Items.WATER_BUCKET),
    BABY(6, "baby", OreDictUtils.EGG),
    PET(7, "pet", Items.MILK_BUCKET),
    SLIME(8, "slime", Blocks.SLIME_BLOCK),
    FIRERES(9, "fireres", Items.BLAZE_POWDER),
    BOSS(10, "boss", OreDictUtils.OBSIDIAN);
    
    private static String[] nameList;
    
    public final int meta;
    public final String type;
    public final Object crafting;
    
    private FilterType(int meta, String name, Object crafting) {
        
        this.meta = meta;
        this.type = name;
        this.crafting = crafting;
    }
    
    @Override
    public String getName () {
        
        return this.type;
    }
    
    @Override
    public String toString () {
        
        return this.type;
    }
    
    public static FilterType fromMeta (int meta) {
        
        for (final FilterType type : FilterType.values())
            if (type.meta == meta)
                return type;
            
        return PLAYER;
    }
    
    public static String[] getTypes () {
        
        if (nameList != null)
            return nameList;
        
        final List<String> names = new ArrayList<>();
        
        for (final FilterType type : FilterType.values())
            names.add(type.type);
        
        nameList = names.toArray(new String[names.size()]);
        return nameList;
    }
    
    public static boolean isValidTarget (EntityLivingBase living, int meta) {
        
        switch (meta) {
            
            case 0:
                return living instanceof EntityPlayer;
            
            case 1:
                return living.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
            
            case 2:
                return living.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD;
            
            case 3:
                return living instanceof IMob;
            
            case 4:
                return living instanceof EntityAnimal;
            
            case 5:
                return living instanceof EntityWaterMob || living instanceof EntityGuardian;
            
            case 6:
                return living.isChild();
            
            case 7:
                return living instanceof EntityTameable;
            
            case 8:
                return living instanceof EntitySlime;
            
            case 9:
                return living.isImmuneToFire();
            
            case 10:
                return !living.isNonBoss();
            
            default:
                return false;
        }
    }
}