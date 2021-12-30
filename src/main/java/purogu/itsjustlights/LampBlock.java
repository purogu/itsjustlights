package purogu.itsjustlights;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class LampBlock extends RedstoneLampBlock implements IColoredBlock {

    private final DyeColor color;

    public LampBlock(DyeColor color) {
        super(generateProperties(color));
        this.color = color;
    }

    public DyeColor getColor() {
        return color;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getValue(LIT) ? 15 : 0;
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isClientSide) {
            boolean flag = state.getValue(LIT);
            if (flag != worldIn.hasNeighborSignal(pos)) {
                worldIn.setBlock(pos, state.cycle(LIT), 2);
            }

        }
    }

    public static Properties generateProperties(DyeColor color) {
        return Properties.of(Material.GLASS, color)
                .requiresCorrectToolForDrops()
                .strength(2)
                .noOcclusion();
    }
}
