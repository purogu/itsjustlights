package purogu.itsjustlights.datageneration;

import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;
import purogu.itsjustlights.ItsJustLights;
import purogu.itsjustlights.LampBlock;
import purogu.itsjustlights.LampItem;
import purogu.itsjustlights.Registry;

import java.util.function.Consumer;

public class RecipeDataProvider extends RecipeProvider {
    public RecipeDataProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        for (int i = 0; i < Registry.NUM_LAMPS; i++) {
            LampItem lampItem = Registry.LAMP_ITEMS.get(i).get();
            LampBlock lampBlock = Registry.LAMP_BLOCKS.get(i).get();
            LampItem litLampItem = Registry.LIT_LAMP_ITEMS.get(i).get();

            String colorKey = lampBlock.getColor().getTranslationKey();
            Tags.IOptionalNamedTag<Item> coloredGlassTag
                    = ItemTags.createOptional(new ResourceLocation("forge", "glass_panes/" + colorKey));
            PlainShapedRecipeBuilder.shapedRecipe(lampItem)
                    .patternLine("gcg")
                    .patternLine("crc")
                    .patternLine("gcg")
                    .key('g', Tags.Items.DUSTS_GLOWSTONE)
                    .key('c', coloredGlassTag)
                    .key('r', Tags.Items.DUSTS_REDSTONE)
                    .setGroup(new ResourceLocation(ItsJustLights.ID, "lamps").toString())
                    .build(consumer);

            PlainShapedRecipeBuilder.shapedRecipe(litLampItem)
                    .patternLine("gcg")
                    .patternLine("crc")
                    .patternLine("gcg")
                    .key('g', Tags.Items.DUSTS_GLOWSTONE)
                    .key('c', coloredGlassTag)
                    .key('r', Items.REDSTONE_TORCH)
                    .setGroup(new ResourceLocation(ItsJustLights.ID, "lit_lamps").toString())
                    .build(consumer);

        }
    }
}
