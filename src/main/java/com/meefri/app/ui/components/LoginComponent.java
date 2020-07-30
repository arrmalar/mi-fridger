package com.meefri.app.ui.components;

import com.meefri.app.data.User;
import com.meefri.app.data.UserAccountInformation;
import com.meefri.app.repositories.UserRepository;
import com.meefri.app.security.CustomRequestCache;
import com.meefri.app.security.SecurityUtils;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginOverlay;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * A Designer generated component for the new-main-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("login-component")
@JsModule("./src/components/login-component.js")
@Route(value = LoginComponent.ROUTE)
public class LoginComponent extends PolymerTemplate<LoginComponent.NewMainViewModel> {

    static final String ROUTE = "login";

    @Id("login")
    private LoginForm loginForm;

    public LoginComponent(@Autowired UserRepository userRepository, @Autowired AuthenticationManager authenticationManager, @Autowired CustomRequestCache customRequestCache) {

        loginForm.addLoginListener(e -> { //
            try {

                final Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(e.getUsername(), e.getPassword()));

                //SecurityContextHolder.getContext().setAuthentication(authentication);

                User user = userRepository.findByUsername(e.getUsername());

                if(authentication.isAuthenticated() && !user.getUserAccountInformation().isVerified()) {

                    Binder<UserAccountInformation> binder = new Binder<>();
                    Dialog dialog = new Dialog();
                    Label label = new Label("Please insert verification code. Check your email.");
                    label.getStyle().set("align-self","center");
                    TextField textField = new TextField();
                    textField.setPlaceholder("code");
                    textField.getStyle().set("align-self","center");

                    binder.forField(textField)
                            .withValidator((String code) -> {
                                //System.out.println(code + " " + user.getUserAccountInformation().getVerificationCode());
                                return code.equals(user.getUserAccountInformation().getVerificationCode());
                            },"")
                            .bind(UserAccountInformation::getVerificationCode,UserAccountInformation::setVerificationCode);

                    Button verify = new Button("Verify");
                    verify.setThemeName("primary success");
                    verify.getStyle().set("align-self","center");

                    verify.addClickListener(event ->{
                        binder.validate();
                        if(binder.isValid()){
                            user.getUserAccountInformation().setVerified(true);
                            userRepository.save(user);
                            dialog.close();
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            UI.getCurrent().navigate(customRequestCache.resolveRedirectUrl());
                        } else {
                            loginForm.setEnabled(true);
                            dialog.close();
                            CustomDialog customDialog = new CustomDialog();
                            customDialog.openCustomDialog("Code is invalid");
                        }
                    });

                    Button cancel = new Button("Cancel");
                    cancel.setThemeName("primary error");
                    cancel.getStyle().set("align-self","center");

                    cancel.addClickListener(event ->{
                        loginForm.setEnabled(true);
                        dialog.close();
                    });

                    VerticalLayout verticalLayout = new VerticalLayout();
                    verticalLayout.add(label,textField,verify,cancel);

                    dialog.setCloseOnOutsideClick(false);
                    dialog.setCloseOnEsc(false);
                    dialog.add(verticalLayout);
                    dialog.open();

                } else {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    UI.getCurrent().navigate(customRequestCache.resolveRedirectUrl());
                }

            } catch (AuthenticationException ex) { //


                Dialog dialog = new Dialog();
                VerticalLayout verticalLayout = new VerticalLayout();
                Label label = new Label("Invalid username or password.");
                label.getStyle().set("align-self","center");
                label.getStyle().set("color","red");
                Button button = new Button("Accept");
                button.getStyle().set("align-self","center");
                button.setThemeName("primary success");
                button.addClickListener(event ->{
                    loginForm.setEnabled(true);
                    dialog.close();
                });

                dialog.setCloseOnOutsideClick(false);
                dialog.setCloseOnEsc(false);

                verticalLayout.add(label,button);
                dialog.add(verticalLayout);
                dialog.open();

            }
        });

        loginForm.setForgotPasswordButtonVisible(false);
    }

    /**
     * This model binds properties between LoginComponent and new-main-view
     */
    public interface NewMainViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
