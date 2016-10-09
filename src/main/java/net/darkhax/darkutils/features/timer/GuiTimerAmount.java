package net.darkhax.darkutils.features.timer;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import net.darkhax.bookshelf.lib.util.NumericUtils;
import net.darkhax.darkutils.DarkUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTimerAmount extends GuiScreen {
    
    private final TileEntityTimer timer;
    
    private GuiTextField delayTextField;
    private GuiButton doneBtn;
    private GuiButton cancelBtn;
    
    public GuiTimerAmount(TileEntityTimer timer) {
        
        this.timer = timer;
    }
    
    @Override
    public void updateScreen () {
        
        this.delayTextField.updateCursorCounter();
    }
    
    @Override
    public void initGui () {
        
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.doneBtn = new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(this.cancelBtn = new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.delayTextField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 150, 50, 300, 20);
        this.delayTextField.setMaxStringLength(5);
        this.delayTextField.setFocused(true);
        this.delayTextField.setText("" + this.timer.getDelayTime());
        this.doneBtn.enabled = this.delayTextField.getText().trim().length() > 0 && StringUtils.isNumeric(this.delayTextField.getText());
    }
    
    @Override
    public void onGuiClosed () {
        
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed (GuiButton button) throws IOException {
        
        if (button.enabled)
            if (button.id == 1)
                this.mc.displayGuiScreen((GuiScreen) null);
            else if (button.id == 0 && StringUtils.isNumeric(this.delayTextField.getText())) {
                
                DarkUtils.NETWORK.sendToServer(new PacketSyncTimer(this.timer.getPos(), Integer.parseInt(this.delayTextField.getText())));
                this.mc.displayGuiScreen((GuiScreen) null);
            }
    }
    
    @Override
    protected void keyTyped (char typedChar, int keyCode) throws IOException {
        
        if (NumericUtils.isKeyCodeNumeric(keyCode) || keyCode == 14) {
            
            this.delayTextField.textboxKeyTyped(typedChar, keyCode);
            this.doneBtn.enabled = this.delayTextField.getText().trim().length() > 0 && StringUtils.isNumeric(this.delayTextField.getText());
            return;
        }
        
        if (keyCode == 1)
            this.actionPerformed(this.cancelBtn);
        
        else if (keyCode == 28)
            this.actionPerformed(this.doneBtn);
    }
    
    @Override
    protected void mouseClicked (int mouseX, int mouseY, int mouseButton) throws IOException {
        
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.delayTextField.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void drawScreen (int mouseX, int mouseY, float partialTicks) {
        
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("gui.darkutils.timer.title", new Object[0]), this.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("gui.darkutils.timer.delay", new Object[0]), this.width / 2 - 150, 37, 10526880);
        this.delayTextField.drawTextBox();
        
        final int i = 75;
        int j = 0;
        this.drawString(this.fontRendererObj, I18n.format("gui.darkutils.timer.desc.1", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("gui.darkutils.timer.desc.2", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("gui.darkutils.timer.desc.3", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("gui.darkutils.timer.desc.4", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}