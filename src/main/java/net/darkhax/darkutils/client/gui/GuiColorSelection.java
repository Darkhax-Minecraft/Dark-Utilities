package net.darkhax.darkutils.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiColorSelection extends GuiScreen {
    
    private static final ResourceLocation background = new ResourceLocation("darkutils:textures/gui/background.png");
    
    private GuiTextField itemName;
    private GuiButton buttonConfirm;
    private GuiButton buttonCancel;
    private GuiSlider sliderRed;
    private GuiSlider sliderGreen;
    private GuiSlider sliderBlue;
    private BlockPos position;
    private int dimension;
    
    private int backgroundWidth;
    private int backgroundHeight;
    
    public GuiColorSelection(BlockPos pos, int dimension) {
        
        this.backgroundWidth = 176;
        this.backgroundHeight = 222;
        this.position = pos;
        this.dimension = dimension;
    }
    
    @Override
    public void updateScreen () {
        
        this.itemName.updateCursorCounter();
    }
    
    @Override
    public void onGuiClosed () {
        
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void keyTyped (char typedChar, int keyCode) throws IOException {
        
        if (keyCode == 1)
            this.actionPerformed(this.buttonCancel);
            
        else if (keyCode == 28)
            this.actionPerformed(this.buttonConfirm);
            
        this.itemName.textboxKeyTyped(typedChar, keyCode);
    }
    
    @Override
    protected void mouseClicked (int mouseX, int mouseY, int mouseButton) throws IOException {
        
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.itemName.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void initGui () {
        
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.buttonConfirm = new GuiButton(0, this.width / 2 - 64, this.height / 4 + 150 + 12, 50, 20, I18n.translateToLocal("gui.done")));
        this.buttonList.add(this.buttonCancel = new GuiButton(1, this.width / 2 + 16, this.height / 4 + 150 + 12, 50, 20, I18n.translateToLocal("gui.cancel")));
        
        int x = ((this.width - this.backgroundWidth) / 2) - (140 / 2) + backgroundWidth / 2;
        this.buttonList.add(this.sliderRed = new GuiSlider(2, x, this.height / 4 + 75, 140, 20, ChatFormatting.RED + "Red: ", "", 0, 255, 255, false, true));
        this.buttonList.add(this.sliderGreen = new GuiSlider(3, x, this.height / 4 + 105, 140, 20, ChatFormatting.GREEN + "Green: ", "", 0, 255, 255, false, true));
        this.buttonList.add(this.sliderBlue = new GuiSlider(4, x, this.height / 4 + 135, 140, 20, ChatFormatting.BLUE + "Blue: ", "", 0, 255, 255, false, true));
        
        this.itemName = new GuiTextField(5, this.fontRendererObj, x, this.height / 4 + 45, 140, 20);
        this.itemName.setMaxStringLength(128);
        this.itemName.setFocused(true);
        this.itemName.setText(I18n.translateToLocal("item.darkutils.foci.recall.name"));
    }
    
    @Override
    protected void actionPerformed (GuiButton button) throws IOException {
        
        if (button.id == 1)
            this.mc.displayGuiScreen((GuiScreen) null);
            
        else if (button.id == 0) {
            
            // DarkUtils.network.sendToServer(new PacketSyncColor(this.position,
            // this.dimension, new Color(sliderRed.getValueInt(), sliderGreen.getValueInt(),
            // sliderBlue.getValueInt()).getRGB(), itemName.getText()));
            this.mc.displayGuiScreen((GuiScreen) null);
        }
    }
    
    @Override
    public void drawScreen (int mouseX, int mouseY, float partialTicks) {
        
        this.drawDefaultBackground();
        
        color3I(sliderRed.getValueInt(), sliderGreen.getValueInt(), sliderBlue.getValueInt());
        this.mc.getTextureManager().bindTexture(background);
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, backgroundWidth, backgroundHeight);
        
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawCenteredString(this.fontRendererObj, I18n.translateToLocal("gui.darkutils.color.title"), this.width / 2, this.height / 2 - 95, 0xffffff);
        this.drawCenteredString(this.fontRendererObj, String.format("X:%d Y:%d Z:%d", position.getX(), position.getY(), position.getZ()), this.width / 2, this.height / 2 - 70, 0xffffff);
        this.itemName.drawTextBox();
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    private void color3I (int red, int green, int blue) {
        
        GlStateManager.color((float) ((float) red / 255f), (float) ((float) green / 255f), (float) ((float) blue / 255f), 1.0F);
    }
}