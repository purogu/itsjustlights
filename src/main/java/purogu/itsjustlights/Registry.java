package purogu.itsjustlights;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class Registry {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ItsJustLights.ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ItsJustLights.ID);

    public static final int NUM_LAMPS = DyeColor.values().length;

    public static final List<RegistryObject<LampBlock>> LAMP_BLOCKS = new ArrayList<>(NUM_LAMPS);
    public static final List<RegistryObject<LampItem>> LAMP_ITEMS = new ArrayList<>(NUM_LAMPS);

    public static final List<RegistryObject<LitLampBlock>> LIT_LAMP_BLOCKS = new ArrayList<>(NUM_LAMPS);
    public static final List<RegistryObject<LampItem>> LIT_LAMP_ITEMS = new ArrayList<>(NUM_LAMPS);

    static {
        for (DyeColor color : DyeColor.values()) {
            final String name = "lamp_" + color;
            RegistryObject<LampBlock> blockRegistry = BLOCKS.register(name, () -> new LampBlock(color));
            RegistryObject<LampItem> itemRegistry = ITEMS.register(name, () -> new LampItem(blockRegistry.get()));
            LAMP_BLOCKS.add(blockRegistry);
            LAMP_ITEMS.add(itemRegistry);

            final String litName = name + "_lit";
            RegistryObject<LitLampBlock> litBlockRegistry = BLOCKS.register(litName, () -> new LitLampBlock(color));
            RegistryObject<LampItem> litItemRegistry = ITEMS.register(litName, () -> new LampItem(litBlockRegistry.get()));
            LIT_LAMP_BLOCKS.add(litBlockRegistry);
            LIT_LAMP_ITEMS.add(litItemRegistry);
        }
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }

    public static Block[] collectColoredBlocks() {
        Block[] blocks = new Block[NUM_LAMPS * 2];
        for(int i = 0; i < NUM_LAMPS; i++) {
            blocks[i] = LAMP_BLOCKS.get(i).get();
        }
        for(int i = 0; i < NUM_LAMPS; i++) {
            blocks[i + NUM_LAMPS] = LIT_LAMP_BLOCKS.get(i).get();
        }
        return blocks;
    }

    public static LampItem[] collectColoredItems() {
        LampItem[] items = new LampItem[NUM_LAMPS * 2];
        for(int i = 0; i < NUM_LAMPS; i++) {
            items[i] = LAMP_ITEMS.get(i).get();
        }
        for(int i = 0; i < NUM_LAMPS; i++) {
            items[i + NUM_LAMPS] = LIT_LAMP_ITEMS.get(i).get();
        }
        return items;
    }
}
