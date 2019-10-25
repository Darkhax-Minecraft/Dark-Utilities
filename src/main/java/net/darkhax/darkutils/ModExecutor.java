package net.darkhax.darkutils;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import net.minecraftforge.fml.ModList;

public final class ModExecutor {
    
    public static <T> T callIfPresent (String modid, Supplier<Callable<T>> toRun) {
        
        if (ModList.get().isLoaded(modid)) {
            
            try {
                
                return toRun.get().call();
            }
            
            catch (final Exception e) {
                
                throw new RuntimeException(e);
            }
        }
        
        return null;
    }
    
    public static void runIfPresent (String modid, Supplier<Runnable> toRun) {
        
        if (ModList.get().isLoaded(modid)) {
            toRun.get().run();
        }
    }
    
    public static <T> T runIfPresent (String modid, Supplier<Supplier<T>> ifPresent, Supplier<Supplier<T>> notPresent) {
        
        if (ModList.get().isLoaded(modid)) {
            
            return ifPresent.get().get();
        }
        
        else {
            
            return notPresent.get().get();
        }
    }
}