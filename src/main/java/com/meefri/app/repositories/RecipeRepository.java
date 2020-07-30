package com.meefri.app.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.meefri.app.data.Ingredient;
import com.meefri.app.data.Member;
import com.meefri.app.data.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String>, CustomRecipeRepository {

    boolean findByListOfIngredientsContains(Ingredient ingredient);
    List<Recipe> findByUserId(String id);
    List<Recipe> findByListOfIngredientsIsLessThanEqual(List<Ingredient> ingredientList);
    Recipe findRecipeById(String id);
}
