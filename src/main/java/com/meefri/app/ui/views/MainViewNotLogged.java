package com.meefri.app.ui.views;

import com.meefri.app.repositories.RecipeRepository;
import com.meefri.app.repositories.UserRepository;
import com.meefri.app.security.LoggedUser;
import com.meefri.app.ui.components.CustomGrid;
import com.meefri.app.ui.components.LoginComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A Designer generated component for the main-view-not-logged template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("main-view-not-logged")
@JsModule("./src/views/main-view-not-logged.js")
@Route(value = MainViewNotLogged.ROUTE)
public class MainViewNotLogged extends PolymerTemplate<MainViewNotLogged.MainViewNotLoggedModel> {

    static final String ROUTE = "index";
    @Id("loginComponent")
    private LoginComponent loginComponent;
    @Id("recipeLayout")
    private VerticalLayout recipeLayout;
    @Id("registrationButton")
    private Button registrationButton;
    @Id("forgotPasswordButton")
    private Button forgotPasswordButton;
    @Id("mainLayout")
    private HorizontalLayout mainLayout;

    /**
     * Creates a new MainViewNotLogged.
     */
    public MainViewNotLogged(@Autowired RecipeRepository recipeRepository, @Autowired UserRepository userRepository, @Autowired LoggedUser loggedUser) {

        CustomGrid customGrid = new CustomGrid(userRepository,recipeRepository,loggedUser);
        customGrid.createGrid();
        customGrid.setRecipesToGrid(recipeRepository.findAll(),new ArrayList<>());
        recipeLayout.add(customGrid.getGrid());

        registrationButton.addClickListener(e ->{
            Dialog dialog = new Dialog();
            RegistrationView registrationView = new RegistrationView(userRepository);

            registrationView.getCancelButton().addClickListener(c -> {
                dialog.close();
            });

            dialog.add(registrationView);
            recipeLayout.add(dialog);
            dialog.open();
        } );

        forgotPasswordButton.addClickListener(e -> {
            Dialog dialog = new Dialog();
            ForgottenPasswordView forgottenPasswordView = new ForgottenPasswordView(userRepository);
            forgottenPasswordView.getCancelButton().addClickListener(c -> {
                dialog.close();
            });

            dialog.add(forgottenPasswordView);
            recipeLayout.add(dialog);
            dialog.open();
        } );

        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between MainViewNotLogged and main-view-not-logged
     */

    public interface MainViewNotLoggedModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
