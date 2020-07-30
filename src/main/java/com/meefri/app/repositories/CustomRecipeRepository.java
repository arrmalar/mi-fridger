package com.meefri.app.repositories;

import com.meefri.app.data.Ingredient;
import com.meefri.app.data.Recipe;

import java.util.List;

public interface CustomRecipeRepository {

    List<Recipe> findDoableRecipeByAvaliableIngredients(List<Ingredient> ingredientList);

}
