package gtmaterials.recipe;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.API;
import codechicken.nei.recipe.ShapedRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
import gtmaterials.GTMItem;
import gtmaterials.recipe.Recipe;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.item.ItemStack;

public class NEI extends TemplateRecipeHandler {
   public static void load() {
      NEI catchRecipe = new NEI();
      API.registerRecipeHandler(catchRecipe);
      API.registerUsageHandler(catchRecipe);
      API.registerGuiOverlay(GuiCrafting.class, catchRecipe.getOverlayIdentifier(), 0, 0);
   }

   public String getOverlayIdentifier() {
      return "Matter";
   }

   public String getRecipeName() {
      return "Matter Recipe";
   }

   public String getGuiTexture() {
      return (new ShapedRecipeHandler()).getGuiTexture();
   }

   public void loadCraftingRecipes(String outputId, Object... results) {
      if(outputId.equals("Matter")) {
         if(Recipe.getInstance().getRecipeMapping().isEmpty()) {
            return;
         }

         for(Entry<ArrayList<Integer>, ItemStack> recipe : Recipe.getInstance().getRecipeMapping().entrySet()) {
            ItemStack item = (ItemStack)recipe.getValue();
            Integer[] in = (Integer[])((ArrayList)recipe.getKey()).toArray(new Integer[9]);
            this.arecipes.add(new NEI.MakeRecipe(in, item));
         }
      } else {
         super.loadCraftingRecipes(outputId, results);
      }

   }

   public void loadCraftingRecipes(ItemStack result) {
      if(!Recipe.getInstance().getRecipeMapping().isEmpty()) {
         for(Entry<ArrayList<Integer>, ItemStack> recipe : Recipe.getInstance().getRecipeMapping().entrySet()) {
            ItemStack item = (ItemStack)recipe.getValue();
            Integer[] in = (Integer[])((ArrayList)recipe.getKey()).toArray(new Integer[9]);
            if(NEIServerUtils.areStacksSameType(item, result)) {
               this.arecipes.add(new NEI.MakeRecipe(in, item));
            }
         }

      }
   }

   public void loadUsageRecipes(ItemStack ingredient) {
      if(!Recipe.getInstance().getRecipeMapping().isEmpty()) {
         for(Entry<ArrayList<Integer>, ItemStack> recipe : Recipe.getInstance().getRecipeMapping().entrySet()) {
            ItemStack item = (ItemStack)recipe.getValue();
            ArrayList<Integer> in = (ArrayList)recipe.getKey();
            if(ingredient.getItem() == GTMItem.Matter && in.contains(Integer.valueOf(ingredient.getItemDamage()))) {
               this.arecipes.add(new NEI.MakeRecipe((Integer[])in.toArray(new Integer[9]), item));
            }
         }

      }
   }

   public void loadTransferRects() {
      this.transferRects.add(new RecipeTransferRect(new Rectangle(90, 24, 22, 15), "Matter", new Object[0]));
   }

   public class MakeRecipe extends CachedRecipe {
      private PositionedStack[] input = new PositionedStack[9];
      private PositionedStack result;

      public MakeRecipe(Integer[] in, ItemStack out) {
         //super(NEI.this);

         for(int i = 0; i < 9 && in.length == 9; ++i) {
            int s = i % 3;
            int f = i / 3;
            this.input[i] = new PositionedStack(new ItemStack(GTMItem.Matter, 1, in[i].intValue()), 25 + s * 18, 6 + f * 18);
         }

         this.result = new PositionedStack(out, 119, 24);
      }

      public PositionedStack getResult() {
         return this.result;
      }

      public List<PositionedStack> getIngredients() {
         return Arrays.asList(this.input);
      }
   }
}
