package purogu.itsjustlights.datageneration;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import purogu.itsjustlights.ItsJustLights;
import purogu.itsjustlights.LampItem;
import purogu.itsjustlights.Registry;

import java.util.function.Consumer;

public class AdvancementDataProvider extends AdvancementProvider {
    public AdvancementDataProvider(DataGenerator generatorIn, ExistingFileHelper fileHelperIn) {
        super(generatorIn, fileHelperIn);
    }

    @Override
    protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {
        AdvancementRewards.Builder rewardsBuilder = new AdvancementRewards.Builder();
        for(RegistryObject<LampItem> lamp : Registry.LAMP_ITEMS) {
            rewardsBuilder.addRecipe(lamp.getId());
        }
        for(RegistryObject<LampItem> lamp : Registry.LIT_LAMP_ITEMS) {
            rewardsBuilder.addRecipe(lamp.getId());
        }

        Advancement.Builder.builder()
                .withParentId(new ResourceLocation("recipes/root"))
                .withCriterion("has_item", InventoryChangeTrigger.Instance.forItems(Items.GLOWSTONE_DUST))
                .withRewards(rewardsBuilder)
                .save(consumer, new ResourceLocation(ItsJustLights.ID, "lamp_recipes"), fileHelper);
    }
}
