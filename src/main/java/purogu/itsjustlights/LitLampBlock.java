package purogu.itsjustlights;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class LitLampBlock extends Block implements IColoredBlock {
    private DyeColor color;

    public LitLampBlock(DyeColor color) {
        super(LampBlock.generateProperties(color));
        this.color = color;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        return 15;
    }

    @Override
    public DyeColor getColor() {
        return color;
    }
}
