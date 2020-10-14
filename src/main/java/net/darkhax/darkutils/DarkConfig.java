package net.darkhax.darkutils;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;

public class DarkConfig {
    
    public static class Common {
        
        public final ConfigValue<List<? extends String>> gluttonyCharmAdditionalFoodItems;
        public final ConfigValue<List<? extends String>> gluttonyCharmBlockedFoodItems;
        
        public Common(Builder builder) {
    
            builder.push("gluttonyCharm");
            gluttonyCharmAdditionalFoodItems = builder
                    .comment("The registry names of additional items that the Gluttony Charm should consider valid food.")
                    .defineList(
                            "additionalFoodItems",
                            Collections.singletonList("mekanism:canteen"),
                            s -> s instanceof String && ResourceLocation.tryCreate((String) s) != null
                    );
            gluttonyCharmBlockedFoodItems = builder
                    .comment("The registry names of food items that the Gluttony Charm should NOT consider valid food.")
                    .defineList(
                            "blockedFoodItems",
                            Collections.emptyList(),
                            s -> s instanceof String && ResourceLocation.tryCreate((String) s) != null
                    );
        }
    }
    
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;
    
    static {
        Pair<Common, ForgeConfigSpec> specPair = new Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
