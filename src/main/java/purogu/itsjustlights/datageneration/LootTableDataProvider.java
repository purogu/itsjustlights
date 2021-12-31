package purogu.itsjustlights.datageneration;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ForgeLootTableProvider;
import net.minecraftforge.fml.RegistryObject;
import purogu.itsjustlights.LampItem;
import purogu.itsjustlights.Registry;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTableDataProvider extends ForgeLootTableProvider {
    public LootTableDataProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    private LootTable.Builder createSimpleTable(BlockItem block) {
        LootPool.Builder lootPool = LootPool.lootPool();
        lootPool
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(block));
        return LootTable.lootTable().withPool(lootPool);
    }

    private ResourceLocation lootTableLocation(Block block) {
        ResourceLocation registryName = block.getRegistryName();
        return new ResourceLocation(registryName.getNamespace(), "blocks/" + registryName.getPath());
    }

    private void addTables(BiConsumer<ResourceLocation, LootTable.Builder> lootTables) {
        for(RegistryObject<LampItem> lampItem : Registry.LAMP_ITEMS) {
            lootTables.accept(lootTableLocation(lampItem.get().getBlock()), createSimpleTable(lampItem.get()));
        }
        for(RegistryObject<LampItem> litLampItem : Registry.LIT_LAMP_ITEMS) {
            lootTables.accept(lootTableLocation(litLampItem.get().getBlock()), createSimpleTable(litLampItem.get()));
        }
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(Pair.of(() -> this::addTables, LootParameterSets.BLOCK));
    }
}
