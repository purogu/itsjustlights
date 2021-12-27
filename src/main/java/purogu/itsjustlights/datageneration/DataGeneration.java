package purogu.itsjustlights.datageneration;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneration {
    @SubscribeEvent
    public static void generateData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if(event.includeServer()) {
            generator.addProvider(new LootTableDataProvider(generator));
            generator.addProvider(new RecipeDataProvider(generator));
            generator.addProvider(new AdvancementDataProvider(generator, event.getExistingFileHelper()));
        }
        if(event.includeClient()) {
            generator.addProvider(new BlockStateDataProvider(generator, event.getExistingFileHelper()));
            generator.addProvider(new EN_US_LanguageDataProvider(generator));
        }
    }
}
