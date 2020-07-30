package com.meefri.app.data;

import java.util.ArrayList;
import java.util.List;

public class UserFridge {

    private List<Ingredient> ingredients = new ArrayList<>();

    public void addIngredients(Ingredient ingredient)  { ingredients.add(ingredient); }

    public void deleteIngredient(Ingredient ingredient){
        if(!ingredients.isEmpty()) ingredients.remove(ingredient);
    }

    public List<Ingredient> getUserIngredients(){ return ingredients; }

}
