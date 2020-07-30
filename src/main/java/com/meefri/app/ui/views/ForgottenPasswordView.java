package com.meefri.app.ui.views;

import com.meefri.app.data.User;
import com.meefri.app.repositories.UserRepository;
import com.meefri.app.services.EmailService;
import com.meefri.app.ui.components.CustomDialog;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * A Designer generated component for the forgotten-password-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */

@Tag("forgotten-password-view")
@JsModule("./src/views/forgotten-password-view.js")

public class ForgottenPasswordView extends PolymerTemplate<ForgottenPasswordView.ForgottenPasswordViewModel> {

    @Id("emailTextField")
    private TextField emailTextField;
    @Id("sendPasswordButton")
    private Button sendPasswordButton;
    @Id("cancelButton")
    private Button cancelButton;

    /**
     * Creates a new ForgottenPasswordView.
     */
    public ForgottenPasswordView(@Autowired UserRepository userRepository) {

        Binder<User> binderEmail = new Binder<>();
        binderEmail.forField(emailTextField)
        .withValidator((String email) -> userRepository.findByEmail(email) != null, "Please enter a valid email address.")
        .bind(User::getEmail,User::setEmail);

        sendPasswordButton.addClickListener(e -> {

            binderEmail.validate();

            if(binderEmail.isValid()) {
                EmailService emailService = new EmailService();
                User user = userRepository.findByEmail(emailTextField.getValue());
                emailService.createVerificationCode();
                String newPassword = emailService.getVerificationCode();
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

                user.setPassword(bCryptPasswordEncoder.encode(newPassword));

                try {
                    emailService.sendMail(emailTextField.getValue()," Your new temporary password is: " + "<b>" +  newPassword +
                            "</b> <br> Please change this password after logging in.");
                } catch (IOException | MessagingException ex) {
                    ex.printStackTrace();
                }

                userRepository.save(user);

                cancelButton.click();
                CustomDialog customDialog = new CustomDialog();
                customDialog.openCustomDialog("We have sent you a new password. Check your email.");
            }
        });

       // cancelButton.addClickListener(e ->{
        //    UI.getCurrent().navigate(MainViewNotLogged.class);
       // });

    }

    public Button getCancelButton(){
        return cancelButton;
    }

    /**
     * This model binds properties between ForgottenPasswordView and forgotten-password-view
     */
    public interface ForgottenPasswordViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
