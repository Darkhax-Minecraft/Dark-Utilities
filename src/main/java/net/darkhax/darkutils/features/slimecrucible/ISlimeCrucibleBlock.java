package net.darkhax.darkutils.features.slimecrucible;

/**
 * This interface is used to identify a block as a slime crucible. It exists to allow slime
 * crucible properties and tile entity logic to be applied to blocks that are not the built in
 * BlockSlimeCrucible.
 */
public interface ISlimeCrucibleBlock {
    
    /**
     * Gets the crucible type represented by the block.
     * 
     * @return The crucible type.
     */
    SlimeCrucibleType getCrucibleType ();
}