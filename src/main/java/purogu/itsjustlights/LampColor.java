package purogu.itsjustlights;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

import javax.annotation.Nullable;

public class LampColor implements IBlockColor, IItemColor {

    @Override
    public int getColor(BlockState state, @Nullable IBlockDisplayReader reader, @Nullable BlockPos pos, int tint) {
        return getLampColor(state.getBlock());
    }

    @Override
    public int getColor(ItemStack stack, int tint) {
        LampItem lampItem = (LampItem) stack.getItem();
        return getLampColor(lampItem.getBlock());
    }

    private int getLampColor(Block block) {
        return ((IColoredBlock)block).getColor().getColorValue();
    }
}
