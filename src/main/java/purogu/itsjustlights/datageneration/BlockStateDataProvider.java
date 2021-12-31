package purogu.itsjustlights.datageneration;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.client.model.generators.loaders.MultiLayerModelBuilder;
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

    private BlockModelBuilder nestedWithParent(String parent) {
        return models()
                .nested()
                .parent(new ModelFile.UncheckedModelFile(parent));
    }

    private ModelFile buildMultiLayerLamp(String modelName, BlockModelBuilder solidModel, BlockModelBuilder translucentModel, ResourceLocation particleTexture) {
        return models()
            .withExistingParent(modLoc(modelName).toString(), "block/block")
            .texture("particle", particleTexture)
            .customLoader(MultiLayerModelBuilder::begin)
                .submodel(RenderType.solid(), solidModel)
                .submodel(RenderType.translucent(), translucentModel)
            .end();
    }

    private ModelFile buildLampOnModel(ResourceLocation onTexture, ResourceLocation highlightTexture) {
        BlockModelBuilder onBlockSolidModel = nestedWithParent("block/block")
                .texture("all", onTexture)
                .element()
                    .from(0.5f, 0.5f, 0.5f)
                    .to(15.5f, 15.5f, 15.5f)
                    .allFaces((direction, faceBuilder) -> faceBuilder
                            .tintindex(0)
                            .texture("#all"))
    //                      .cullface(direction))
                .end();

        BlockModelBuilder onBlockTranslucentModel = nestedWithParent("block/block")
                .texture("all", highlightTexture)
                .element()
                    .allFaces((direction, faceBuilder) -> faceBuilder
                            .tintindex(0)
                            .texture("#all"))
//                          .cullface(direction))
                .end();

        return buildMultiLayerLamp("lamp_on", onBlockSolidModel, onBlockTranslucentModel, onTexture);
    }

    private ModelFile buildLampOffModel(ResourceLocation offTexture, ResourceLocation emptyTexture) {
        BlockModelBuilder offBlockSolidModel = nestedWithParent("block/block")
                .texture("all", offTexture)
                .element()
                    .shade(true)
                    .allFaces((direction, faceBuilder) -> faceBuilder
                            .tintindex(0)
                            .texture("#all")
                            .cullface(direction))
                .end();

        BlockModelBuilder offBlockTranslucentModel = nestedWithParent("block/cube_all")
                .texture("all", emptyTexture);

        return buildMultiLayerLamp("lamp_off", offBlockSolidModel, offBlockTranslucentModel, offTexture);
    }

    @Override
    protected void registerStatesAndModels() {
        ResourceLocation onTexture = modLoc("block/lamp_on");
        ResourceLocation offTexture = modLoc("block/lamp_off");
        ResourceLocation highlightTexture = modLoc("block/lamp_highlight");
        ResourceLocation emptyTexture = modLoc("block/empty");

        ModelFile onBlockModel = buildLampOnModel(onTexture, highlightTexture);
        ModelFile offBlockModel = buildLampOffModel(offTexture, emptyTexture);

        for(RegistryObject<LampBlock> lamp : Registry.LAMP_BLOCKS) {
            getVariantBuilder(lamp.get())
                .forAllStates(state -> {
                    if(state.getValue(LampBlock.LIT)) {
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
