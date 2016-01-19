package net.darkhax.darkutils.libs;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraftforge.oredict.OreDictionary;

public class Utilities {
    
    public static void centerDirectional (BlockPos block, Entity entity, EnumFacing facing) {
        
        Vec3 entPos = entity.getPositionVector();
        
        if (facing == EnumFacing.EAST || facing == EnumFacing.WEST)
            entity.setPositionAndUpdate(entPos.xCoord, entPos.yCoord, block.getZ() + 0.5);
            
        else
            entity.setPositionAndUpdate(block.getX() + 0.5, entPos.yCoord, entPos.zCoord);
    }
    
    public static boolean requireCentering (BlockPos block, Vec3 entPos, EnumFacing facing) {
        
        if (facing == EnumFacing.EAST || facing == EnumFacing.WEST)
            return !isInRange(block.getZ() + 0.35, block.getZ() + 0.65, entPos.zCoord);
            
        return !isInRange(block.getX() + 0.35, block.getX() + 0.65, entPos.xCoord);
    }
    
    public static boolean isInRange (double min, double max, double value) {
        
        return (value <= max && value >= min);
    }
    
    /**
     * A simple check to make sure that an EntityPlayer actually exists.
     * 
     * @param player: The instance of EntityPlayer to check.
     * @return boolean: If the player exists true will be returned. If they don't false will be
     *         returned.
     */
    public static boolean isPlayerReal (EntityPlayer player) {
        
        if (player == null || player.worldObj == null || player.getClass() != EntityPlayerMP.class)
            return false;
            
        return MinecraftServer.getServer().getConfigurationManager().playerEntityList.contains(player);
    }
    
    /**
     * Calculates the distance between two entities. This is done by getting the square root of
     * the entities positions.
     * 
     * @param firstEntity: The first entity to use.
     * @param secondEntity: The second entity to use.
     * @return double: The distance between the two entities passed.
     */
    public static double getDistanceBetweenEntities (Entity firstEntity, Entity secondEntity) {
        
        double distanceX = firstEntity.posX - secondEntity.posX;
        double distanceY = firstEntity.posY - secondEntity.posY;
        double distanceZ = firstEntity.posZ - secondEntity.posZ;
        
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
    }
    
    public static double getDistaceFromPos (Entity entity, BlockPos pos) {
        
        double distanceX = entity.posX - pos.getX();
        double distanceY = entity.posY - pos.getY();
        double distanceZ = entity.posZ - pos.getZ();
        
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
    }
    
    /**
     * Pushes an entity towards another one.
     * 
     * @param entityToMove: The entity that should be pushed towards the other entity.
     * @param destination: The destination entity, that the entity to move should be pushed
     *            towards.
     * @param force: The amount of force to push the entityToMove with.
     */
    public static void pushTowards (Entity entityToMove, Entity destination, double force) {
        
        double distanceX = destination.posX - entityToMove.posX;
        double distanceY = destination.posY - entityToMove.posY;
        double distanceZ = destination.posZ - entityToMove.posZ;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
        
        if (distance > 0) {
            
            entityToMove.motionX = distanceX / distance * force;
            entityToMove.motionY = distanceY / distance * force;
            entityToMove.motionZ = distanceZ / distance * force;
        }
    }
    
    public static void pushTowards (Entity entityToMove, EnumFacing direction, double force) {
        
        pushTowards(entityToMove, entityToMove.getPosition().offset(direction.getOpposite(), 1), force);
    }
    
    public static void pushTowards (Entity entityToMove, BlockPos pos, double force) {
        
        BlockPos entityPos = entityToMove.getPosition();
        double distanceX = pos.getX() - entityPos.getX();
        double distanceY = pos.getY() - entityPos.getY();
        double distanceZ = pos.getZ() - entityPos.getZ();
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
        
        if (distance > 0) {
            
            entityToMove.motionX = distanceX / distance * force;
            entityToMove.motionY = distanceY / distance * force;
            entityToMove.motionZ = distanceZ / distance * force;
        }
    }
    
    /**
     * Checks if two entities are close enough together.
     * 
     * @param firstEntity: The first entity to check.
     * @param secondEntity: The second entity to check.
     * @param maxDistance: The maximum distance that the entities can be apart.
     * @return boolean: True if the distance between the entities are within range of the
     *         maxDistance.
     */
    public static boolean areEntitiesCloseEnough (Entity firstEntity, Entity secondEntity, double maxDistance) {
        
        return getDistanceBetweenEntities(firstEntity, secondEntity) < (maxDistance * maxDistance);
    }
    
    /**
     * Sets a stack compound to an ItemStack if it does not already have one.
     * 
     * @param stack: ItemStack having a tag set on it.
     */
    public static NBTTagCompound prepareDataTag (ItemStack stack) {
        
        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
            
        return stack.getTagCompound();
    }
    
    /**
     * Sets the lore for an ItemStack. This will override any existing lore on that item.
     * 
     * @param stack: An instance of an ItemStack to write the lore to.
     * @param lore: An array containing the lore to write. Each line is a new entry.
     * @return ItemStack: The same instance of ItemStack that was passed to this method.
     */
    public static ItemStack setLore (ItemStack stack, String[] lore) {
        
        prepareDataTag(stack);
        NBTTagCompound tag = stack.getTagCompound();
        NBTTagList loreList = new NBTTagList();
        
        if (!tag.hasKey("display", 10))
            tag.setTag("display", new NBTTagCompound());
            
        for (String line : lore)
            loreList.appendTag(new NBTTagString(line));
            
        tag.getCompoundTag("display").setTag("Lore", loreList);
        stack.setTagCompound(tag);
        
        return stack;
    }
    
    /**
     * Checks if an ItemStack is valid. A valid ItemStack is one that is not null, and has an
     * Item.
     * 
     * @param stack: The ItemStack to check.
     * @return boolean: True if the stack is valid, false if it is not.
     */
    public static boolean isValidStack (ItemStack stack) {
        
        return (stack != null && stack.getItem() != null);
    }
    
    /**
     * Compares all ore dictionary names associated with an ItemStack, with the provided ore
     * dictionary name.
     * 
     * @param stack: The ItemStack to compare against.
     * @param oreName: The ore dictionary name to compare to.
     * @return boolean: True if any of the ore dictionary entries for the provided stack match
     *         the provided ore name.
     */
    public static boolean compareStackToOreName (ItemStack stack, String oreName) {
        
        for (int stackName : OreDictionary.getOreIDs(stack))
            if (OreDictionary.getOreName(stackName).equalsIgnoreCase(oreName))
                return true;
                
        return false;
    }
    
    /**
     * Compares all applicable ore dictionary names for two item stacks, to see if either have
     * a name in common.
     * 
     * @param firstStack: The first ItemStack to compare.
     * @param secondStack: The second ItemStack to compare.
     * @return boolean: True, if any of the ore dictionary names for either stack are the same.
     */
    public static boolean doStacksShareOreName (ItemStack firstStack, ItemStack secondStack) {
        
        for (int firstName : OreDictionary.getOreIDs(firstStack))
            for (int secondName : OreDictionary.getOreIDs(secondStack))
                if (firstName == secondName)
                    return true;
                    
        return false;
    }
    
    /**
     * Checks to see if two ItemStacks are similar. A similar stack has the same item, and the
     * same damage.
     * 
     * @param firstStack: The first stack to check.
     * @param secondStack: The second stack to check.
     * @return boolean: True if stacks are similar, or if both are null.
     */
    public static boolean areStacksSimilar (ItemStack firstStack, ItemStack secondStack) {
        
        return (firstStack == null && secondStack == null) ? true : (isValidStack(firstStack) && isValidStack(secondStack) && firstStack.getItemDamage() == secondStack.getItemDamage() && firstStack.getItem() == secondStack.getItem());
    }
    
    /**
     * Checks to see if two ItemStacks are similar. A similar stack has the same item, and the
     * same damage and same size.
     * 
     * @param firstStack: The first stack to check.
     * @param secondStack: The second stack to check.
     * @return boolean: True if stacks are similar, or if both are null.
     */
    public static boolean areStacksSimilarWithSize (ItemStack firstStack, ItemStack secondStack) {
        
        return (firstStack == null && secondStack == null) ? true : (isValidStack(firstStack) && isValidStack(secondStack) && firstStack.getItemDamage() == secondStack.getItemDamage() && firstStack.getItem() == secondStack.getItem() && firstStack.stackSize == secondStack.stackSize);
    }
    
    /**
     * Retrieves the custom color of an ItemStack. This will only retrieve color data that has
     * been set through this mod. If no valid color can be found, white will be used.
     * 
     * @param stack: The ItemStack to check the color of.
     * @return int: A numeric representation of the color, that can be broken down into RGB
     *         components.
     */
    public static int getItemColor (ItemStack stack) {
        
        return stack.getTagCompound().hasKey("bookshelfColor") ? stack.getTagCompound().getInteger("bookshelfColor") : 16777215;
    }
    
    /**
     * Sets a color to an ItemStack. This color will override any color value provided by the
     * getColorFromItemStack method.
     * 
     * @param stack: The ItemStack to change the color of.
     * @param color: A numeric representation of the color, that can be broken down into RGB
     *            components.
     */
    public static void setItemColor (ItemStack stack, int color) {
        
        prepareDataTag(stack);
        stack.getTagCompound().setInteger("bookshelfColor", color);
    }
    
    /**
     * Removes all color data associated with an ItemStack. This only works for custom NBT
     * colors set by this mod.
     * 
     * @param stack: The ItemStack to remove the color from.
     */
    public static void removeItemColor (ItemStack stack) {
        
        prepareDataTag(stack);
        stack.getTagCompound().removeTag("bookshelfColor");
    }
    
    /**
     * Writes an ItemStack as a sub NBTTagCompound on a larger NBTTagCompound.
     * 
     * @param stack: The ItemStack to write to the tag.
     * @param tag: The NBTTagCompound to write the stack to.
     * @param tagName: The name for this new NBTTagCompound entry.
     */
    public static void writeStackToTag (ItemStack stack, NBTTagCompound tag, String tagName) {
        
        NBTTagCompound stackTag = new NBTTagCompound();
        stack.writeToNBT(stackTag);
        tag.setTag(tagName, stackTag);
    }
    
    /**
     * Safely decreases the amount of items held by an ItemStack.
     * 
     * @param stack: The ItemStack to decrease the size of.
     * @param amount: The amount to decrease the stack size by.
     * @return ItemStack: Null, if the stack size is smaller than 1.
     */
    public static ItemStack decreaseStackSize (ItemStack stack, int amount) {
        
        stack.stackSize -= amount;
        return (stack.stackSize <= 0) ? null : stack;
    }
}