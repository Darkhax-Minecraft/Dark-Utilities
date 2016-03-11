package net.darkhax.darkutils.tileentity;

import java.util.List;

import net.darkhax.bookshelf.lib.util.EntityUtils;
import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.bookshelf.tileentity.TileEntityBasic;
import net.darkhax.darkutils.blocks.BlockFeeder;
import net.darkhax.darkutils.handler.ContentHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;

public class TileEntityFeeder extends TileEntityBasic implements IInventory {
    
    public String foodType;
    
    public int addFood (ItemStack foodStack) {
        
        if (isValidFood(foodStack)) {
            
            int food = getFood();
            
            if (food == getSizeInventory())
                return foodStack.stackSize;
                
            setFood(food += foodStack.stackSize);
            
            if (food > getSizeInventory()) {
                
                int remaining = food - getSizeInventory();
                setFood(getSizeInventory());
                return remaining;
            }
        }
        
        return foodStack.stackSize;
    }
    
    public int getFood () {
        
        return this.getWorld().getBlockState(this.pos).getValue(BlockFeeder.FOOD).intValue();
    }
    
    public void setFood (int food) {
        
        this.worldObj.setBlockState(this.pos, getStateFromFood(food), 3);
    }
    
    public boolean isValidFood(ItemStack stack) {
        
        if (foodType == null || foodType.equals("null"))
            foodType = ItemStackUtils.writeStackToString(stack);
        
        return ItemStackUtils.isValidStack(stack) && ItemStackUtils.writeStackToString(stack).equals(this.foodType);
    }
    
    @Override
    public void onEntityUpdate () {
        
        if (!this.worldObj.isRemote) {
            
            int food = getFood();
            
            if (food != 0) {
                
                List<EntityAnimal> animals = EntityUtils.getEntitiesInArea(EntityAnimal.class, this.getWorld(), this.getPos(), 8);
                
                for (EntityAnimal animal : animals) {
                    
                    int currentFood = getFood();
                    ItemStack foodStack = ItemStackUtils.createStackFromString(foodType);
                    
                    if (animal.getGrowingAge() == 0 && animal.inLove <= 0 && currentFood != 0 && animal.isBreedingItem(foodStack)) {
                        
                        animal.inLove = 1200;
                        this.getWorld().setEntityState(animal, (byte) 18);
                        setFood(currentFood - 1);
                    }
                }
            }
            
            else if (food == 0)
                foodType = "null";
        }
    }
    
    public IBlockState getStateFromFood (int food) {
        
        return ContentHandler.blockFeeder.getStateFromMeta(food);
    }
    
    @Override
    public void writeNBT (NBTTagCompound dataTag) {
        
        dataTag.setString("FoodType", (this.foodType == null || this.foodType.isEmpty()) ? "null" : this.foodType);
    }

    @Override
    public void readNBT (NBTTagCompound dataTag) {
    
        this.foodType = dataTag.getString("FoodType");
    }
    
    @Override
    public String getName () {
        
        return "tile.darkutils.feeder.name";
    }
    
    @Override
    public boolean hasCustomName () {
        
        return false;
    }
    
    @Override
    public IChatComponent getDisplayName () {
        
        return null;
    }
    
    @Override
    public int getSizeInventory () {
        
        return 10;
    }
    
    @Override
    public ItemStack getStackInSlot (int index) {
        
        return null;
    }
    
    @Override
    public ItemStack decrStackSize (int index, int count) {
        
        return null;
    }
    
    @Override
    public ItemStack removeStackFromSlot (int index) {
        
        return null;
    }
    
    @Override
    public void setInventorySlotContents (int index, ItemStack stack) {
        
        stack.stackSize = addFood(stack);
    }
    
    @Override
    public int getInventoryStackLimit () {
        
        return 1;
    }
    
    @Override
    public boolean isUseableByPlayer (EntityPlayer player) {
        
        return false;
    }
    
    @Override
    public void openInventory (EntityPlayer player) {
    
    }
    
    @Override
    public void closeInventory (EntityPlayer player) {
    
    }
    
    @Override
    public boolean isItemValidForSlot (int index, ItemStack stack) {
        
        if (getFood() != 10 && isValidFood(stack)) {
            
            Item item = stack.getItem();
            return (item == Items.carrot || item == Items.wheat_seeds || item == Items.wheat);
        }
        
        return false;
    }
    
    @Override
    public int getField (int id) {
        
        return 0;
    }
    
    @Override
    public void setField (int id, int value) {
    
    }
    
    @Override
    public int getFieldCount () {
        
        return 0;
    }
    
    @Override
    public void clear () {
        
        setFood(0);
    }
}