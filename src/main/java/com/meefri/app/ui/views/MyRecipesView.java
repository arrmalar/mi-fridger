package com.meefri.app.ui.views;

import com.meefri.app.data.Recipe;
import com.meefri.app.data.User;
import com.meefri.app.repositories.RecipeRepository;
import com.meefri.app.repositories.UserRepository;
import com.meefri.app.security.LoggedUser;
import com.meefri.app.ui.components.CustomGrid;
import com.meefri.app.ui.components.MenuComponent;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Tag("my-recipes-view")
@JsModule("./src/views/my-recipes-view.js")
@Route(value = MyRecipesView.ROUTE)
public class MyRecipesView extends PolymerTemplate<MyRecipesView.MyRecipesViewModel> {

    static final String ROUTE = "myRecipes";

    @Id("menuComponent")
    private MenuComponent menuComponent;
    @Id("vaadinVerticalLayout")
    private VerticalLayout vaadinVerticalLayout;

    /**
     * Creates a new MyRecipesView.
     */
    public MyRecipesView(@Autowired LoggedUser loggedUser,
                         @Autowired UserRepository userRepository ,
                         @Autowired RecipeRepository recipeRepository) {

        menuComponent.setColorToChoosenButton("myRecipesView");

        CustomGrid customGrid = new CustomGrid(userRepository,recipeRepository,loggedUser);
        User user = loggedUser.getLoggedUser();
        List<Recipe> recipeList = new ArrayList<>();

        for(String id : loggedUser.getLoggedUser().getUserCreatedRecipes()){
            recipeList.add(recipeRepository.findRecipeById( id ));
        }

        if(recipeList.isEmpty()) {
            Label label = new Label("You haven't created any recipes!");
            label.getStyle().set("font-size", "24px");
            label.getStyle().set("align-self","center");
            vaadinVerticalLayout.add(label);
        } else {
            customGrid.createGrid();
            customGrid.setRecipesToGrid(recipeList,new ArrayList<>());
            vaadinVerticalLayout.add(customGrid.getGrid());
        }

    }

    public interface MyRecipesViewModel extends TemplateModel {

    }
}
