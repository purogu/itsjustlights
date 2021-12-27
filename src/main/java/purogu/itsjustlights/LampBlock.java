package purogu.itsjustlights;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

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
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.get(LIT) ? 15 : 0;
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isRemote) {
            boolean flag = state.get(LIT);
            if (flag != worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, state.cycleValue(LIT), 2);
            }

        }
    }

    public static Properties generateProperties(DyeColor color) {
        return Properties.create(Material.GLASS, color)
                .hardnessAndResistance(2)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .notSolid();
    }
}
