package purogu.itsjustlights;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;

public class LampItem extends BlockItem {

    public LampItem(Block blockIn) {
        super(blockIn, new Properties()
                .tab(CreativeModeTab.TAB_REDSTONE));
    }
}
