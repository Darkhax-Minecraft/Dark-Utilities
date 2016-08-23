package net.darkhax.darkutils.features.blocks.feeder;

import java.util.List;

import net.darkhax.bookshelf.lib.util.EntityUtils;
import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.bookshelf.tileentity.TileEntityBasic;
import net.darkhax.bookshelf.tileentity.TileEntityBasicTickable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;

public class TileEntityFeeder extends TileEntityBasicTickable implements IInventory {
    
    public String foodType;
    
    public int addFood (ItemStack foodStack) {
        
        if (this.isValidFood(foodStack)) {
            
            int food = this.getFood();
            
            if (food == this.getSizeInventory())
                return foodStack.stackSize;
                
            this.setFood(food += foodStack.stackSize);
            
            if (food > this.getSizeInventory()) {
                
                final int remaining = food - this.getSizeInventory();
                this.setFood(this.getSizeInventory());
                return remaining;
            }
        }
        
        return foodStack.stackSize;
    }
    
    public int getFood () {
        
        return this.getWorld().getBlockState(this.pos).getValue(BlockFeeder.FOOD).intValue();
    }
    
    public void setFood (int food) {
        
        this.worldObj.setBlockState(this.pos, this.getStateFromFood(food), 3);
    }
    
    public boolean isValidFood (ItemStack stack) {
        
        if (this.foodType == null || this.foodType.equals("null"))
            this.foodType = ItemStackUtils.writeStackToString(stack);
            
        return ItemStackUtils.isValidStack(stack) && ItemStackUtils.writeStackToString(stack).equals(this.foodType);
    }
    
    @Override
    public void onEntityUpdate () {
        
        if (!this.worldObj.isRemote) {
            
            final int food = this.getFood();
            
            if (food != 0) {
                
                final List<EntityAnimal> animals = EntityUtils.getEntitiesInArea(EntityAnimal.class, this.getWorld(), this.getPos(), 8);
                
                for (final EntityAnimal animal : animals) {
                    
                    final int currentFood = this.getFood();
                    final ItemStack foodStack = ItemStackUtils.createStackFromString(this.foodType);
                    
                    if (animal.getGrowingAge() == 0 && animal.inLove <= 0 && currentFood != 0 && animal.isBreedingItem(foodStack)) {
                        
                        animal.inLove = 1200;
                        this.getWorld().setEntityState(animal, (byte) 18);
                        this.setFood(currentFood - 1);
                    }
                }
            }
            
            else if (food == 0)
                this.foodType = "null";
        }
    }
    
    public IBlockState getStateFromFood (int food) {
        
        return FeatureFeeder.blockFeeder.getStateFromMeta(food);
    }
    
    @Override
    public void writeNBT (NBTTagCompound dataTag) {
        
        dataTag.setString("FoodType", this.foodType == null || this.foodType.isEmpty() ? "null" : this.foodType);
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
    public ITextComponent getDisplayName () {
        
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
        
        stack.stackSize = this.addFood(stack);
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
        
        if (this.getFood() != 10 && this.isValidFood(stack)) {
            
            final Item item = stack.getItem();
            return item == Items.GOLDEN_APPLE || item == Items.GOLDEN_CARROT || item == Items.WHEAT || item == Items.CARROT || item == Items.WHEAT_SEEDS || item == Items.PORKCHOP || item == Items.BEEF || item == Items.CHICKEN || item == Items.RABBIT || item == Items.MUTTON || item == Items.ROTTEN_FLESH || item == Items.COOKED_PORKCHOP || item == Items.COOKED_BEEF || item == Items.COOKED_CHICKEN || item == Items.COOKED_RABBIT || item == Items.COOKED_MUTTON || item == Items.FISH;
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
        
        this.setFood(0);
    }
}