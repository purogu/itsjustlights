package purogu.itsjustlights;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

public class LampItem extends BlockItem {

    public LampItem(Block blockIn) {
        super(blockIn, new Properties()
                .group(ItemGroup.REDSTONE));
    }

    @Override
    public Set<ResourceLocation> getTags() {
        return super.getTags();
    }
}
