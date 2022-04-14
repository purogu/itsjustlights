package purogu.itsjustlights.datageneration;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class PlainShapedRecipeBuilder {
    private final Item result;
    private final int count;
    private final List<String> pattern = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private String group;

    protected PlainShapedRecipeBuilder(ItemLike resultIn, int countIn) {
        this.result = resultIn.asItem();
        this.count = countIn;
    }

    /**
     * Creates a new builder for a shaped recipe.
     */
    public static PlainShapedRecipeBuilder shapedRecipe(ItemLike resultIn) {
        return shapedRecipe(resultIn, 1);
    }

    /**
     * Creates a new builder for a shaped recipe.
     */
    public static PlainShapedRecipeBuilder shapedRecipe(ItemLike resultIn, int countIn) {
        return new PlainShapedRecipeBuilder(resultIn, countIn);
    }

    /**
     * Adds a key to the recipe pattern.
     */
    public PlainShapedRecipeBuilder key(Character symbol, TagKey<Item> tagIn) {
        return this.key(symbol, Ingredient.of(tagIn));
    }

    /**
     * Adds a key to the recipe pattern.
     */
    public PlainShapedRecipeBuilder key(Character symbol, ItemLike itemIn) {
        return this.key(symbol, Ingredient.of(itemIn));
    }

    /**
     * Adds a key to the recipe pattern.
     */
    public PlainShapedRecipeBuilder key(Character symbol, Ingredient ingredientIn) {
        if (this.key.containsKey(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        } else if (symbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(symbol, ingredientIn);
            return this;
        }
    }

    /**
     * Adds a new entry to the patterns for this recipe.
     */
    public PlainShapedRecipeBuilder patternLine(String patternIn) {
        if (!this.pattern.isEmpty() && patternIn.length() != this.pattern.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.pattern.add(patternIn);
            return this;
        }
    }

    public PlainShapedRecipeBuilder setGroup(String groupIn) {
        this.group = groupIn;
        return this;
    }

    /**
     * Builds this recipe into an {@link FinishedRecipe}.
     */
    public void build(Consumer<FinishedRecipe> consumerIn) {
        this.build(consumerIn, this.result.getRegistryName());
    }

    /**
     * Builds this recipe into an {@link FinishedRecipe}.
     */
    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        this.validate(id);
        consumerIn.accept(new Result(id, this.result, this.count,
                this.group == null ? "" : this.group, this.pattern, this.key));
    }

    /**
     * Makes sure that this recipe is valid.
     */
    private void validate(ResourceLocation id) {
        if (this.pattern.isEmpty()) {
            throw new IllegalStateException("No pattern is defined for shaped recipe " + id + "!");
        } else {
            Set<Character> set = Sets.newHashSet(this.key.keySet());
            set.remove(' ');

            for (String s : this.pattern) {
                for (int i = 0; i < s.length(); ++i) {
                    char c0 = s.charAt(i);
                    if (!this.key.containsKey(c0) && c0 != ' ') {
                        throw new IllegalStateException("Pattern in recipe " + id + " uses undefined symbol '" + c0 + "'");
                    }

                    set.remove(c0);
                }
            }

            if (!set.isEmpty()) {
                throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + id);
            } else if (this.pattern.size() == 1 && this.pattern.get(0).length() == 1) {
                throw new IllegalStateException("Shaped recipe " + id + " only takes in a single item - should it be a shapeless recipe instead?");
            }
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final String group;
        private final List<String> pattern;
        private final Map<Character, Ingredient> key;

        public Result(ResourceLocation idIn, Item resultIn, int countIn, String groupIn, List<String> patternIn, Map<Character, Ingredient> keyIn) {
            this.id = idIn;
            this.result = resultIn;
            this.count = countIn;
            this.group = groupIn;
            this.pattern = patternIn;
            this.key = keyIn;
        }

        public void serializeRecipeData(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }

            JsonArray jsonarray = new JsonArray();

            for(String s : this.pattern) {
                jsonarray.add(s);
            }

            json.add("pattern", jsonarray);
            JsonObject jsonobject = new JsonObject();

            for(Map.Entry<Character, Ingredient> entry : this.key.entrySet()) {
                jsonobject.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
            }

            json.add("key", jsonobject);
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.addProperty("item", this.result.getRegistryName().toString());
            if (this.count > 1) {
                jsonobject1.addProperty("count", this.count);
            }

            json.add("result", jsonobject1);
        }

        public RecipeSerializer<?> getType() {
            return RecipeSerializer.SHAPED_RECIPE;
        }

        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
