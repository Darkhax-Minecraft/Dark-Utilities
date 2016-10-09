package net.darkhax.darkutils.features.filter;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.IStringSerializable;

public enum FilterType implements IStringSerializable {
    
    PLAYER(0, "player"),
    UNDEAD(1, "undead"),
    ARTHROPOD(2, "arthropod"),
    MONSTER(3, "monster"),
    ANIMAL(4, "animal"),
    WATER(5, "water"),
    BABY(6, "baby"),
    PET(7, "pet"),
    SLIME(8, "slime");
    
    private static String[] nameList;
    
    public final int meta;
    public final String type;
    
    private FilterType(int meta, String name) {
        
        this.meta = meta;
        this.type = name;
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
}