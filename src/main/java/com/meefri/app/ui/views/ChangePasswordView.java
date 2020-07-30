package com.meefri.app.ui.views;

import com.meefri.app.data.User;
import com.meefri.app.repositories.UserRepository;
import com.meefri.app.security.LoggedUser;
import com.meefri.app.ui.components.CustomDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.PasswordField;
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

/**
 * A Designer generated component for the change-password-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("change-password-view")
@JsModule("./src/views/change-password-view.js")

public class ChangePasswordView extends PolymerTemplate<ChangePasswordView.ChangePasswordViewModel> {

    ;
    @Id("oldPassword")
    private PasswordField oldPassword;
    @Id("newPassword")
    private PasswordField newPassword;
    @Id("newPasswordConfirm")
    private PasswordField newPasswordConfirm;
    @Id("changeButton")
    private Button changeButton;
    @Id("cancelButton")
    private Button cancelButton;

    public ChangePasswordView(@Autowired LoggedUser loggedUser,@Autowired UserRepository userRepository) {

        Binder<User> binderOldPassword = new Binder<>();
        Binder<User> binderNewPassword = new Binder<>();
        Binder<User> binderConfirmNewPassword = new Binder<>();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        binderOldPassword
                .forField(oldPassword)
                .withValidator( (String e) -> {
                    return bCryptPasswordEncoder.matches(e,loggedUser.getLoggedUser().getPassword());
                },"Invalid password.").bind(User::getPassword,User::setUsername);


        binderNewPassword.forField(newPassword)
                .withValidator( (String name) -> {
                    String regex = "^[aA-zZ]\\w{5,29}$";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(name);

                    return m.matches();
                },"Password is invalid")
                .bind(User::getPassword,User::setPassword);

        binderConfirmNewPassword.forField(newPasswordConfirm)
                .withValidator((String name) -> name.equals(newPassword.getValue()),"Passwords are not the same")
                .bind(User::getPassword,User::setPassword);

        changeButton.addClickListener(e -> {
            if(binderConfirmNewPassword.isValid() && binderNewPassword.isValid() && binderOldPassword.isValid()){
                User user = loggedUser.getLoggedUser();
                user.setPassword(bCryptPasswordEncoder.encode(newPassword.getValue()));
                userRepository.save(user);
                cancelButton.click();
                CustomDialog customDialog = new CustomDialog();
                customDialog.openCustomDialog("You have changed your password!");
            }
        });

    }

    public Button getCancelButton(){
        return cancelButton;
    }

    /**
     * This model binds properties between ChangePasswordView and change-password-view
     */
    public interface ChangePasswordViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
