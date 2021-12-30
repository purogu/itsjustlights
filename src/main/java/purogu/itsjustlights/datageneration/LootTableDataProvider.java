package purogu.itsjustlights.datageneration;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.fmllegacy.RegistryObject;
import purogu.itsjustlights.LampItem;
import purogu.itsjustlights.Registry;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTableDataProvider extends LootTableProvider {
    public LootTableDataProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    private LootTable.Builder createSimpleTable(BlockItem block) {
        LootPool.Builder lootPool = LootPool.lootPool();
        lootPool.setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block.asItem()));
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
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((p_218436_2_, p_218436_3_) -> {
            LootTables.validate(validationtracker, p_218436_2_, p_218436_3_);
        });
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(Pair.of(() -> this::addTables, LootContextParamSets.BLOCK));
    }
}
