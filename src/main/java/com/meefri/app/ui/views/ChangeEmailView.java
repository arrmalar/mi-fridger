package com.meefri.app.ui.views;

import com.meefri.app.data.User;
import com.meefri.app.repositories.UserRepository;
import com.meefri.app.security.LoggedUser;
import com.meefri.app.ui.components.CustomDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Tag("change-email-view")
@JsModule("./src/views/change-email-view.js")

public class ChangeEmailView extends PolymerTemplate<ChangeEmailView.ChangeEmailViewModel> {


    @Id("oldEmail")
    private TextField oldEmail;
    @Id("changeButton")
    private Button changeButton;
    @Id("cancelButton")
    private Button cancelButton;
    @Id("newEmail")
    private TextField newEmail;
    @Id("confirmNewEmail")
    private TextField confirmNewEmail;

    public ChangeEmailView(@Autowired LoggedUser loggedUser, @Autowired UserRepository userRepository) {

        Binder<User> binderOldEmail = new Binder<>();
        Binder<User> binderNewEmail = new Binder<>();
        Binder<User> binderConfirmNewEmail = new Binder<>();

        binderOldEmail
                .forField(oldEmail)
                .withValidator( (String e) -> {
                    return e.equals(loggedUser.getLoggedUser().getEmail());
                },"Invalid email.")
                .bind(User::getEmail,User::setEmail);

        binderNewEmail.forField(newEmail)
                .withValidator((String name) -> {
                    final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(name);
                    return m.matches();
                },"Given email is invalid")
                .withValidator((String name) -> userRepository.findByEmail(name) == null, "The given email is taken")
                .bind(User::getEmail,User::setEmail);

        binderConfirmNewEmail.forField(confirmNewEmail)
                .withValidator( (String name)-> name.equals(newEmail.getValue()),"Emails are not the same")
                .bind(User::getEmail,User::setEmail);

        changeButton.addClickListener(e -> {
            if(binderConfirmNewEmail.isValid() && binderNewEmail.isValid() && binderOldEmail.isValid()){
                User user = loggedUser.getLoggedUser();
                user.setEmail(newEmail.getValue());
                userRepository.save(user);
                cancelButton.click();
                CustomDialog customDialog = new CustomDialog();
                customDialog.openCustomDialog("You have changed your email address!");

            }
        });


    }

    public Button getCancelButton(){
        return cancelButton;
    }

    /**
     * This model binds properties between ChangeEmailView and change-email-view
     */
    public interface ChangeEmailViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
