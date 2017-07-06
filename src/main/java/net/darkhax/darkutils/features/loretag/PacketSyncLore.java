package net.darkhax.darkutils.features.loretag;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.bookshelf.network.SerializableMessage;
import net.darkhax.bookshelf.util.StackUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncLore extends SerializableMessage {

    public String lore;

    public PacketSyncLore () {

    }

    public PacketSyncLore (String lore) {

        this.lore = lore;
    }

    @Override
    public IMessage handleMessage (MessageContext context) {

        final EntityPlayer player = context.getServerHandler().player;

        if (player != null) {

            final ItemStack stack = player.getHeldItemMainhand();

            if (stack.getItem() instanceof ItemFormatLoreTag) {

                StackUtils.setLore(stack, this.lore);
            }

            else {

                player.sendMessage(new TextComponentTranslation("chat.darkutils.loretag.error.sync"));

            }
        }
        return null;
    }

}
