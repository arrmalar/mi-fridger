package com.meefri.app.ui.views;

import com.meefri.app.repositories.UserRepository;
import com.meefri.app.security.LoggedUser;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.meefri.app.ui.components.MenuComponent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A Designer generated component for the account-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("account-view")
@JsModule("./src/views/account-view.js")
@Route(value = AccountView.ROUTE)
public class AccountView extends PolymerTemplate<AccountView.AccountViewModel> {

    static final String ROUTE = "account";

    @Id("menuComponent")
    private MenuComponent menuComponent;
    @Id("changeEmailButton")
    private Button changeEmailButton;
    @Id("changePasswordButton")
    private Button changePasswordButton;
    @Id("vaadinVerticalLayout")
    private VerticalLayout vaadinVerticalLayout;

    /**
     * Creates a new AccountView.
     */
    public AccountView(@Autowired LoggedUser loggedUser, @Autowired UserRepository userRepository) {

        menuComponent.setColorToChoosenButton("myAccountView");

        changeEmailButton.addClickListener(e ->{
            ChangeEmailView changeEmailView = new ChangeEmailView(loggedUser,userRepository);
            Dialog dialog = new Dialog();
            changeEmailView.getCancelButton().addClickListener(c -> {
                dialog.close();
            });

            vaadinVerticalLayout.add(dialog);
            dialog.add(changeEmailView);
            dialog.open();
        });

        changePasswordButton.addClickListener(e -> {
            ChangePasswordView changePasswordView = new ChangePasswordView(loggedUser,userRepository);
            Dialog dialog = new Dialog();
            changePasswordView.getCancelButton().addClickListener(c -> {
                dialog.close();
            });
            vaadinVerticalLayout.add(dialog);
            dialog.add(changePasswordView);
            dialog.open();
        });

    }

    /**
     * This model binds properties between AccountView and account-view
     */
    public interface AccountViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
