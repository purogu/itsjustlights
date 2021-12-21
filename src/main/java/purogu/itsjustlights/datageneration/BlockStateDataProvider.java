package purogu.itsjustlights.datageneration;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import purogu.itsjustlights.ItsJustLights;
import purogu.itsjustlights.LampBlock;
import purogu.itsjustlights.LitLampBlock;
import purogu.itsjustlights.Registry;

public class BlockStateDataProvider extends BlockStateProvider {

    public BlockStateDataProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ItsJustLights.ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ResourceLocation onTexture = modLoc("block/lamp_on");
        ResourceLocation offTexture = modLoc("block/lamp_off");
        ResourceLocation highlightTexture = modLoc("block/lamp_highlight");

        ModelFile onBlockModel = models()
                .withExistingParent(modLoc("lamp_on").toString(), "block/block")
                .texture("base", onTexture)
//                .texture("highlight", highlightTexture)
                .texture("particle", onTexture)
                .element()
                    .shade(false)
                    .allFaces((direction, faceBuilder) -> faceBuilder
                        .tintindex(0)
                        .texture("#base")
                        .cullface(direction))
                .end();
//                .element()
//                    .shade(false)
//                    .from(-.75f, -.75f, -.75f)
//                    .to(16.75f, 16.75f, 16.75f)
//                    .allFaces((direction, faceBuilder) -> faceBuilder
//                            .tintindex(0)
//                            .uvs(0, 0, 16, 16)
//                            .texture("#highlight"))
//                .end();

        ModelFile offBlockModel = models()
                .withExistingParent(modLoc("lamp_off").toString(), "block/block")
                .texture("all", offTexture)
                .texture("particle", offTexture)
                .element()
                    .shade(true)
                    .allFaces((direction, faceBuilder) -> faceBuilder
                            .tintindex(0)
                            .texture("#all")
                            .cullface(direction))
                .end();
        for(RegistryObject<LampBlock> lamp : Registry.LAMP_BLOCKS) {
            getVariantBuilder(lamp.get())
                    .forAllStates(state -> {
                        if(state.get(LampBlock.LIT)) {
                            return new ConfiguredModel[] {new ConfiguredModel(onBlockModel)};
                        }
                        else {
                            return new ConfiguredModel[] {new ConfiguredModel(offBlockModel)};
                        }
                    });
            simpleBlockItem(lamp.get(), offBlockModel);
        }

        for(RegistryObject<LitLampBlock> lamp : Registry.LIT_LAMP_BLOCKS) {
            simpleBlock(lamp.get(), onBlockModel);
            simpleBlockItem(lamp.get(), onBlockModel);
        }
    }
}
