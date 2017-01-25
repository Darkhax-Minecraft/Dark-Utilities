package net.darkhax.darkutils.features.trap;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.IStringSerializable;

public enum TrapType implements IStringSerializable {

    POISON(0, "poison"),
    WEAKNESS(1, "weakness"),
    HARMING(2, "harming"),
    SLOWNESS(3, "slowness"),
    FIRE(4, "fire"),
    WITHER(5, "wither");

    private static String[] nameList;

    public final int meta;

    public final String type;

    private TrapType (int meta, String name) {

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

    public static TrapType fromMeta (int meta) {

        for (final TrapType type : TrapType.values())
            if (type.meta == meta)
                return type;

        return POISON;
    }

    public static String[] getTypes () {

        if (nameList != null)
            return nameList;

        final List<String> names = new ArrayList<>();

        for (final TrapType type : TrapType.values())
            names.add(type.type);

        nameList = names.toArray(new String[names.size()]);
        return nameList;
    }
}