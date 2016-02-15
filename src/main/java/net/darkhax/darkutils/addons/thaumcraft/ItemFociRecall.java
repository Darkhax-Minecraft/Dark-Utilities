package net.darkhax.darkutils.addons.thaumcraft;

import java.awt.Color;
import java.util.List;

import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.handler.GuiHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWand;

public class ItemFociRecall extends ItemFocusBasic {
    
    public ItemFociRecall() {
        
        super("darkutils:recall", -1);
        this.setUnlocalizedName("darkutils.foci.recall");
        this.setCreativeTab(DarkUtils.tab);
        this.maxStackSize = 1;
    }
    
    @Override
    public boolean onFocusActivation (ItemStack wandstack, World world, EntityLivingBase player, MovingObjectPosition movingobjectposition, int count) {
        
        ItemWand wand = (ItemWand) wandstack.getItem();
        ItemStack focusStack = wand.getFocusStack(wandstack);
        
        if (teleportUser(player, focusStack))
            return true;
            
        player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + StatCollector.translateToLocal("chat.darkutils.warning.recall")));
        return false;
    }
    
    @Override
    public ItemStack onItemRightClick (ItemStack stack, World world, EntityPlayer player) {
        
        if ((stack.hasTagCompound() && stack.getTagCompound().hasKey("posData")) || !player.worldObj.isRemote)
            return stack;
            
        BlockPos pos = player.getPosition();
        player.openGui(DarkUtils.instance, GuiHandler.COLOR, world, pos.getX(), pos.getY(), pos.getZ());
        return stack;
    }
    
    @Override
    public AspectList getVisCost (ItemStack itemstack) {
        
        return new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.AIR, 5);
    }
    
    @Override
    public WandFocusAnimation getAnimation (ItemStack itemstack) {
        
        return WandFocusAnimation.WAVE;
    }
    
    @Override
    public int getFocusColor (ItemStack stack) {
        
        ItemWand wand = (ItemWand) stack.getItem();
        ItemStack focusStack = wand.getFocusStack(stack);
        
        return (focusStack.hasTagCompound() && focusStack.getTagCompound().hasKey("colorData")) ? focusStack.getTagCompound().getInteger("colorData") : 10511680;
    }
    
    @Override
    public String getSortingHelper (ItemStack focusStack) {
        
        final String color = (focusStack.hasTagCompound() && focusStack.getTagCompound().hasKey("colorData")) ? focusStack.getTagCompound().getInteger("colorData") + "" : "";
        return "darkutils:recall" + "-" + focusStack.getDisplayName() + "-" + color;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack (ItemStack stack, int renderPass) {
        
        return (stack.hasTagCompound() && stack.getTagCompound().hasKey("colorData")) ? stack.getTagCompound().getInteger("colorData") : 16777215;
    }
    
    @Override
    public boolean canApplyUpgrade (ItemStack focusstack, EntityPlayer player, FocusUpgradeType type, int rank) {
        
        return false;
    }
    
    @Override
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        
        super.addInformation(stack, player, list, advanced);
        list.add(EnumChatFormatting.AQUA + "" + EnumChatFormatting.ITALIC + StatCollector.translateToLocal("tooltip.darkutils.focus." + (stack.hasTagCompound() && stack.getTagCompound().hasKey("posData") ? "bound" : "unbound") + ".desc"));
    }
    
    /**
     * Creates an ItemStack containing a Recall focus that will teleport the user to the
     * specified position.
     * 
     * @param pos: The XYZ position to teleport the player to.
     * @param dimension: The ID of the dimension that this focus should work in.
     * @return ItemStack: The newly created ItemStack.
     */
    public static ItemStack createFociFromPos (BlockPos pos, int dimension) {
        
        ItemStack stack = new ItemStack(DarkUtilsThaumcraftAddon.itemRecallFocus);
        return setFocusPosition(stack, pos, dimension);
    }
    
    /**
     * Writes a position and dimension to an ItemStack. Intended for use with the recall focus.
     * 
     * @param stack: The ItemStack to set the position to.
     * @param pos: The position to set.
     * @param dimension: The dimension ID to set.
     * @return ItemStack: The same stack instance passed to this method.
     */
    public static ItemStack setFocusPosition (ItemStack stack, BlockPos pos, int dimension) {
        
        NBTTagCompound tag = (stack.hasTagCompound()) ? stack.getTagCompound() : new NBTTagCompound();
        tag.setIntArray("posData", new int[] { pos.getX(), pos.getY(), pos.getZ(), dimension });
        stack.setTagCompound(tag);
        return stack;
    }
    
    /**
     * Attempts to teleport an entity to the position data stored on an ItemStack. For this to
     * work, the player must be in the same dimension as the location, and the ItemStack passed
     * must have the proper nbt data.
     * 
     * @param entity: The entity to teleport.
     * @param stack: The ItemStack containing the warp data.
     * @return boolean: Whether or not the teleportation was successful.
     */
    public static boolean teleportUser (EntityLivingBase entity, ItemStack stack) {
        
        if (ItemStackUtils.isValidStack(stack) && stack.hasTagCompound()) {
            
            NBTTagCompound tag = stack.getTagCompound();
            
            if (tag.hasKey("posData")) {
                
                int[] posData = tag.getIntArray("posData");
                
                if (posData.length == 4 && posData[3] == entity.dimension) {
                    
                    entity.setPosition(posData[0], posData[1], posData[2]);
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Gets an array of display stacks that represent the colors from AWT Color.
     * 
     * @return ItemStack[]: The display stacks.
     */
    public static ItemStack[] getDisplayStacks () {
        
        return new ItemStack[] { withColor(Color.BLACK), withColor(Color.BLUE), withColor(Color.CYAN), withColor(Color.DARK_GRAY), withColor(Color.GRAY), withColor(Color.GREEN), withColor(Color.LIGHT_GRAY), withColor(Color.MAGENTA), withColor(Color.ORANGE), withColor(Color.PINK), withColor(Color.RED), withColor(Color.WHITE), withColor(Color.YELLOW) };
    }
    
    /**
     * Creates a new ItemStack with the specific color applied. Not intended for actual use,
     * and should instead be used purely in rendering.
     * 
     * @param color: The color to render the stack with.
     * @return ItemStack: The ItemStack that was created.
     */
    public static ItemStack withColor (Color color) {
        
        ItemStack stack = new ItemStack(DarkUtilsThaumcraftAddon.itemRecallFocus);
        ItemStackUtils.prepareDataTag(stack);
        stack.getTagCompound().setInteger("colorData", color.getRGB());
        return stack;
    }
}