package com.meefri.app.ui.views;

import com.meefri.app.data.Recipe;
import com.meefri.app.repositories.RecipeRepository;
import com.meefri.app.repositories.UserRepository;
import com.meefri.app.security.LoggedUser;
import com.meefri.app.ui.components.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * A Designer generated component for the main-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */

@Tag("main-view")
@JsModule("./src/views/main-view.js")
@Route(value = MainView.ROUTE)
public class MainView extends PolymerTemplate<MainView.MainViewModel> {

    static final String ROUTE = "";
    @Id("menuComponent")
    private MenuComponent menuComponent;
    @Id("vaadinVerticalLayout")
    private VerticalLayout vaadinVerticalLayout;

    public MainView(@Autowired RecipeRepository recipeRepository,
                    @Autowired LoggedUser loggedUser,
                    @Autowired UserRepository userRepository) {

        menuComponent.setColorToChoosenButton("mainView");
        CustomGrid customGrid = new CustomGrid(userRepository,recipeRepository,loggedUser);
        customGrid.createGrid();
        customGrid.setRecipesToGrid(recipeRepository.findAll(),new ArrayList<>());
        vaadinVerticalLayout.add(customGrid.getGrid());
    }

    public interface MainViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
