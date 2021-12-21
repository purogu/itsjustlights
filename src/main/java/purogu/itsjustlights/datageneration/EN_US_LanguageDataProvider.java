package purogu.itsjustlights.datageneration;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;
import purogu.itsjustlights.*;

public class EN_US_LanguageDataProvider extends LanguageProvider {

    public EN_US_LanguageDataProvider(DataGenerator gen) {
        super(gen, ItsJustLights.ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        for(RegistryObject<LampBlock> lampBlock : Registry.LAMP_BLOCKS) {
            LampBlock lamp = lampBlock.get();
            String colorName = Utils.toTitleCase(lamp.getColor().getTranslationKey().replace('_', ' '));
            add(lamp.asItem(), colorName + " Lamp");
        }
        for(RegistryObject<LitLampBlock> litLampBlock : Registry.LIT_LAMP_BLOCKS) {
            LitLampBlock lamp = litLampBlock.get();
            String colorName = Utils.toTitleCase(lamp.getColor().getTranslationKey().replace('_', ' '));
            add(lamp.asItem(), "Lit " + colorName + " Lamp");
        }
    }
}
