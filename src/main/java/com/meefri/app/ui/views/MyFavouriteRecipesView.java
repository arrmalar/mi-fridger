package com.meefri.app.ui.views;

import com.meefri.app.data.Recipe;
import com.meefri.app.data.User;
import com.meefri.app.data.UserAccountInformation;
import com.meefri.app.repositories.RecipeRepository;
import com.meefri.app.repositories.UserRepository;
import com.meefri.app.security.LoggedUser;
import com.meefri.app.ui.components.CustomGrid;
import com.meefri.app.ui.components.MenuComponent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * A Designer generated component for the code-verification-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("my-favourite-recipes-view")
@JsModule("./src/views/my-favourite-recipes-view.js")
@Route(value = MyFavouriteRecipesView.ROUTE)
public class MyFavouriteRecipesView extends PolymerTemplate<MyFavouriteRecipesView.MyFavouriteRecipesViewModel> {

    static final String ROUTE = "myFavourite";

    @Id("vaadinVerticalLayout")
    private VerticalLayout verticalLayout;

    @Id("menuComponent")
    private MenuComponent menuComponent;

    /**
     * Creates a new MyFavouriteRecipesView.
     */
    public MyFavouriteRecipesView(@Autowired LoggedUser loggedUser,
                                  @Autowired UserRepository userRepository,
                                  @Autowired RecipeRepository recipeRepository) {

        menuComponent.setColorToChoosenButton("myFavouriteView");

        CustomGrid customGrid = new CustomGrid(userRepository,recipeRepository,loggedUser);
        customGrid.createGrid();
        List<Recipe> list = new ArrayList<>();

        for(String id : loggedUser.getLoggedUser().getUserLikedRecipes()){
            list.add(recipeRepository.findRecipeById( id ));
        }

        if(list.isEmpty()){
            Label label = new Label("You didn't add any recipes to your favourites!");
            label.getStyle().set("font-size", "24px");
            label.getStyle().set("align-self","center");
            verticalLayout.add(label);
        } else {
            customGrid.setRecipesToGrid(list, new ArrayList<>());
            verticalLayout.add(customGrid.getGrid());
        }
    }

    /**
     * This model binds properties between MyFavouriteRecipesView and code-verification-view
     */
    public interface MyFavouriteRecipesViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
