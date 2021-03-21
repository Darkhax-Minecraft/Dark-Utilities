package net.darkhax.darkutils.features.flatblocks.collision;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class CollisionEffectAnchor implements CollisionEffect {
    
    @Override
    public void apply (BlockState state, World world, BlockPos pos, Entity entity) {
        
        final Direction direction = state.get(BlockStateProperties.HORIZONTAL_FACING);
        
        if (!entity.isSneaking()) {
            
            if (world.isRemote && Dist.CLIENT == FMLEnvironment.dist && entity instanceof PlayerEntity && entity.ticksExisted % 20 == 0) {
                
                ITextComponent keyName = Minecraft.getInstance().gameSettings.keyBindSneak.func_238171_j_();
                
                if (keyName instanceof IFormattableTextComponent) {
                    
                    keyName = ((IFormattableTextComponent) keyName).mergeStyle(TextFormatting.LIGHT_PURPLE);
                }
                
                final ITextComponent blockName = state.getBlock().getTranslatedName().mergeStyle(TextFormatting.BLUE);
                final ITextComponent warning = new TranslationTextComponent("gui.message.anchor_plate_warning", keyName, blockName);
                ((PlayerEntity) entity).sendStatusMessage(warning, true);
            }
            
            if (entity instanceof LivingEntity && entity.isNonBoss()) {
                
                final BlockPos offset = pos.offset(direction.getOpposite(), 5);
                final LivingEntity living = (LivingEntity) entity;
                living.lookAt(EntityAnchorArgument.Type.EYES, new Vector3d(offset.getX() + 0.5f, offset.getY() + 1.5f, offset.getZ() + 0.5f));
            }
            
            entity.setPosition(pos.getX() + 0.5f, pos.getY() + 0.0625D, pos.getZ() + 0.5f);
        }
    }
}