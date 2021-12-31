package purogu.itsjustlights;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

public class LampItem extends BlockItem {

    public LampItem(Block blockIn) {
        super(blockIn, new Properties()
                .tab(ItemGroup.TAB_REDSTONE));
    }

    @Override
    public Set<ResourceLocation> getTags() {
        return super.getTags();
    }
}
