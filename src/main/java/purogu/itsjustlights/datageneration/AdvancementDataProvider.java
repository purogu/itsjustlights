package purogu.itsjustlights.datageneration;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
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

        Advancement.Builder.advancement()
                .parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GLOWSTONE_DUST))
                .rewards(rewardsBuilder)
                .save(consumer, new ResourceLocation(ItsJustLights.ID, "lamp_recipes"), fileHelper);
    }
}
