package purogu.itsjustlights.datageneration;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.MultiLayerModel;
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

    @Override
    protected void registerStatesAndModels() {
        ResourceLocation onTexture = modLoc("block/lamp_on");
        ResourceLocation offTexture = modLoc("block/lamp_off");
        ResourceLocation highlightTexture = modLoc("block/lamp_highlight");

        BlockModelBuilder onBlockSolidModel = models()
                .nested()
                .texture("all", onTexture)
                .parent(new ModelFile.UncheckedModelFile("block/block"))
                .element()
                    .from(0.5f, 0.5f, 0.5f)
                    .to(15.5f, 15.5f, 15.5f)
                    .allFaces((direction, faceBuilder) -> faceBuilder
                            .tintindex(0)
                            .texture("#all")
                            .cullface(direction))
                .end();

        BlockModelBuilder onBlockTranslucentModel = models()
                .nested()
                .parent(new ModelFile.UncheckedModelFile("block/block"))
                .texture("all", highlightTexture)
                .element()
                    .allFaces((direction, faceBuilder) -> faceBuilder
                            .tintindex(0)
                            .texture("#all")
                            .cullface(direction))
                .end();

        ModelFile onBlockModel = models()
                .withExistingParent(modLoc("lamp_on").toString(), "block/block")
                .texture("particle", onTexture)
                .customLoader(MultiLayerModelBuilder::begin)
                    .submodel(RenderType.getSolid(), onBlockSolidModel)
                    .submodel(RenderType.getTranslucent(), onBlockTranslucentModel)
                .end();

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
