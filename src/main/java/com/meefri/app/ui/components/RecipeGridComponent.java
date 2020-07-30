package com.meefri.app.ui.components;

import com.vaadin.flow.component.button.Button;
import lombok.Data;

@Data
public class RecipeGridComponent {

    private RecipeLayoutGrid recipeLayoutGrid;
    private Button viewButton;

    public RecipeGridComponent(RecipeLayoutGrid recipeLayoutGrid){
        this.recipeLayoutGrid = recipeLayoutGrid;
    }

}
