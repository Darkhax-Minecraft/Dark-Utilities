package net.darkhax.darkutils.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBasicInventory extends GuiContainer {
    
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/hopper.png");
    private final IInventory playerInventory;
    private final IInventory inventory;
    
    public GuiBasicInventory(InventoryPlayer playerInv, IInventory inventory) {
        
        super(new ContainerHopper(playerInv, inventory, Minecraft.getMinecraft().thePlayer));
        this.playerInventory = playerInv;
        this.inventory = inventory;
        this.allowUserInput = false;
        this.ySize = 133;
    }
    
    @Override
    public void drawGuiContainerForegroundLayer (int mouseX, int mouseY) {
        
        this.fontRendererObj.drawString(this.inventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    public void drawGuiContainerBackgroundLayer (float partialTicks, int mouseX, int mouseY) {
        
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        final int i = (this.width - this.xSize) / 2;
        final int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }
}