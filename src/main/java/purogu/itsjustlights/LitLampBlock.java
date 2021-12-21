package purogu.itsjustlights;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class LitLampBlock extends Block implements IColoredBlock {
    private DyeColor color;

    public LitLampBlock(DyeColor color) {
        super(LampBlock.generateProperties(color));
        this.color = color;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 15;
    }

    @Override
    public DyeColor getColor() {
        return color;
    }
}
