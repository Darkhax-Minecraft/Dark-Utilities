package net.darkhax.darkutils.features.flatblocks.collision;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class CollisionEffectPush implements CollisionEffect {
    
    /**
     * The amount of velocity to apply to entities.
     */
    private final double velocity;
    
    public CollisionEffectPush(double velocity) {
        
        this.velocity = velocity;
    }
    
    @Override
    public void apply (BlockState state, World world, BlockPos pos, Entity entity) {
        
        if (!entity.isSneaking()) {
            
            if (world.isRemote && Dist.CLIENT == FMLEnvironment.dist && entity instanceof PlayerEntity && entity.ticksExisted % 20 == 0) {
                
                ITextComponent keyName = Minecraft.getInstance().gameSettings.keyBindSneak.func_238171_j_();
                
                if (keyName instanceof IFormattableTextComponent) {
                    
                    keyName = ((IFormattableTextComponent) keyName).mergeStyle(TextFormatting.LIGHT_PURPLE);
                }
                
                final ITextComponent blockName = state.getBlock().getTranslatedName().mergeStyle(TextFormatting.BLUE);
                final ITextComponent warning = new TranslationTextComponent("gui.message.vector_plate_warning", keyName, blockName);
                ((PlayerEntity) entity).sendStatusMessage(warning, true);
            }
            
            final Direction direction = state.get(BlockStateProperties.HORIZONTAL_FACING);            
            entity.setMotion(entity.getMotion().add(this.velocity * (direction.getXOffset() * 1.5), 0, this.velocity * (direction.getZOffset() * 1.5)));
            this.additionalEffects(state, world, pos, entity);
        }
    }
    
    /**
     * Applied to an entity when it is pushed by this effect. Allows for new types of pushing
     * blocks that have other effects, like the ability to cook items.
     *
     * @param state The state of the block.
     * @param world Instance of the world.
     * @param pos The current block position.
     * @param entity The entity that collided with the block.
     */
    public void additionalEffects (BlockState state, World world, BlockPos pos, Entity entity) {
        
        // Allows children to add additional effects to the entity being moved.
    }
}