package com.meefri.app.ui.views;

import com.meefri.app.data.Ingredient;
import com.meefri.app.data.Recipe;
import com.meefri.app.data.User;
import com.meefri.app.repositories.RecipeRepository;
import com.meefri.app.repositories.UserRepository;
import com.meefri.app.security.LoggedUser;
import com.meefri.app.ui.components.CustomGrid;
import com.meefri.app.ui.components.SearchingBarComponent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.meefri.app.ui.components.MenuComponent;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Tag("search-recipe-view")
@JsModule("./src/views/search-recipe-view.js")
@Route(value = SearchRecipeView.ROUTE)
@PageTitle("SearchRecipe")
public class SearchRecipeView extends PolymerTemplate<SearchRecipeView.SearchRecipeViewModel> {

    static final String ROUTE = "search";

    @Id("menuComponent")
    private MenuComponent menuComponent;

    @Id("searchingBarComponent")
    private SearchingBarComponent searchingBarComponent;

    private User currentUser;

    @Id("vaadinVerticalLayout1")
    private VerticalLayout vaadinVerticalLayout1;

    @Id("useFridge")
    private Button useFridge;

    private CustomGrid customGrid ;

    public SearchRecipeView(@Autowired LoggedUser loggedUser,
                            @Autowired RecipeRepository recipeRepository,
                            @Autowired UserRepository userRepository) {

        menuComponent.setColorToChoosenButton("searchView");

        this.customGrid = new CustomGrid(userRepository,recipeRepository,loggedUser);
        this.currentUser = loggedUser.getLoggedUser();

        customGrid.createGrid();
        customGrid.setMostAccurateComparator();
        vaadinVerticalLayout1.add(customGrid.getGrid());
        searchingBarComponent.setSearchRecipeViewObjectInSearchingBar(this);
        searchingBarComponent.setSearchingModeOn();

        useFridge.addClickListener(event -> {

            for(Ingredient i : currentUser.getUserFridge().getUserIngredients()) {
                searchingBarComponent.createElement(i);
            }

            if( !searchingBarComponent.getIngredientList().isEmpty()){
                List<Recipe> recipeList = recipeRepository.findDoableRecipeByAvaliableIngredients(searchingBarComponent.getIngredientList());
                customGrid.setRecipesToGrid(recipeList, searchingBarComponent.getIngredientList());
            }
        });
    }

    public void setCustomGridData(List<Recipe> recipeList , List<Ingredient> ingredientList){
        customGrid.setRecipesToGrid(recipeList,ingredientList);
    }


    public interface SearchRecipeViewModel extends TemplateModel {

    }
}
