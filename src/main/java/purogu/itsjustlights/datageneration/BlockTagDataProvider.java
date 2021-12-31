package purogu.itsjustlights.datageneration;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import purogu.itsjustlights.ItsJustLights;
import purogu.itsjustlights.LampBlock;
import purogu.itsjustlights.LitLampBlock;
import purogu.itsjustlights.Registry;

import javax.annotation.Nullable;

public class BlockTagDataProvider extends BlockTagsProvider {

    public BlockTagDataProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, ItsJustLights.ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        for(RegistryObject<LampBlock> lamp : Registry.LAMP_BLOCKS) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(lamp.get());
        }
        for(RegistryObject<LitLampBlock> lamp : Registry.LIT_LAMP_BLOCKS) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(lamp.get());
        }
    }
}
