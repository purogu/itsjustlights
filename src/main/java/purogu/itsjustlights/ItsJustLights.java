package purogu.itsjustlights;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@Mod(ItsJustLights.ID)
public class ItsJustLights
{
    public static final String ID = "itsjustlights";

    public ItsJustLights() {
        Registry.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerItemColor(ColorHandlerEvent.Item event) {
        LampColor lampColor = new LampColor();
        event.getBlockColors().register(lampColor, Registry.collectColoredBlocks());
        event.getItemColors().register(lampColor, Registry.collectColoredItems());
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event)
    {
        for(Block block : Registry.collectColoredBlocks()) {
            RenderTypeLookup.setRenderLayer(block, (layer) -> {
                return layer == RenderType.solid() || layer == RenderType.translucent();
            });
        }
    }
}
