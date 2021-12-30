package purogu.itsjustlights;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class LampColor implements BlockColor, ItemColor {

    @Override
    public int getColor(BlockState state, @Nullable BlockAndTintGetter getter, @Nullable BlockPos pos, int tint) {
        return getLampColor(state.getBlock());
    }

    @Override
    public int getColor(ItemStack stack, int tint) {
        LampItem lampItem = (LampItem) stack.getItem();
        return getLampColor(lampItem.getBlock());
    }

    private int getLampColor(Block block) {
        return ((IColoredBlock)block).getColor().getFireworkColor();
    }

}
