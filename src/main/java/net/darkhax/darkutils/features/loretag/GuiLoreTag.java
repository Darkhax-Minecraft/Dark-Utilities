package net.darkhax.darkutils.features.loretag;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.bookshelf.util.StackUtils;
import net.darkhax.darkutils.DarkUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLoreTag extends GuiScreen {

    private final ItemStack stack;

    private final TextFormatting format;

    private final LoreType type;

    private GuiTextField loreText;

    private GuiButton doneBtn;

    private GuiButton cancelBtn;

    public GuiLoreTag (ItemStack stack) {

        this.stack = stack;
        this.format = ItemFormatLoreTag.getFormatting(stack);
        this.type = ItemFormatLoreTag.getLore(stack);
    }

    @Override
    public void updateScreen () {

        this.loreText.updateCursorCounter();
    }

    @Override
    public void initGui () {

        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.doneBtn = new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(this.cancelBtn = new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.loreText = new GuiTextField(2, this.fontRenderer, this.width / 2 - 150, 50, 300, 20);
        this.loreText.setMaxStringLength(48);
        this.loreText.setFocused(true);
        this.loreText.setTextColor(this.type.getColor());

        final List<String> lore = StackUtils.getLore(this.stack);

        if (!lore.isEmpty()) {
            this.loreText.setText(lore.get(0));
        }

        this.doneBtn.enabled = this.loreText.getText().trim().length() > 0 && StringUtils.isNumeric(this.loreText.getText());
    }

    @Override
    public void onGuiClosed () {

        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed (GuiButton button) throws IOException {

        if (button.enabled) {

            if (button.id == 1) {

                this.mc.displayGuiScreen((GuiScreen) null);
            }

            else if (button.id == 0 && !this.loreText.getText().isEmpty()) {

                DarkUtils.NETWORK.sendToServer(new PacketSyncLore(this.format + "" + ChatFormatting.ITALIC + this.loreText.getText()));
                StackUtils.appendLore(this.stack, this.format + "" + ChatFormatting.ITALIC + this.loreText.getText());
                this.mc.displayGuiScreen((GuiScreen) null);
            }
        }
    }

    @Override
    protected void keyTyped (char typedChar, int keyCode) throws IOException {

        if (keyCode == 1) {

            this.actionPerformed(this.cancelBtn);
        }

        else if (keyCode == 28) {

            this.actionPerformed(this.doneBtn);
        }

        else {

            this.loreText.textboxKeyTyped(typedChar, keyCode);
            this.doneBtn.enabled = !this.loreText.getText().isEmpty();
        }
    }

    @Override
    protected void mouseClicked (int mouseX, int mouseY, int mouseButton) throws IOException {

        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.loreText.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen (int mouseX, int mouseY, float partialTicks) {

        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format("gui.darkutils.loretag.title", new Object[0]), this.width / 2, 20, 16777215);
        this.drawString(this.fontRenderer, I18n.format("gui.darkutils.loretag.field", new Object[0]), this.width / 2 - 150, 37, 10526880);
        this.loreText.drawTextBox();

        final int i = 75;
        int j = 0;
        this.drawString(this.fontRenderer, I18n.format("gui.darkutils.loretag.desc.1", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRenderer, I18n.format("gui.darkutils.loretag.desc.2", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRenderer, I18n.format("gui.darkutils.loretag.desc.3", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRenderer, I18n.format("gui.darkutils.loretag.desc.4", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}