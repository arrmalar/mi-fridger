package com.meefri.app.ui.views;

import com.meefri.app.data.*;
import com.meefri.app.repositories.UserRepository;
import com.meefri.app.services.EmailService;
import com.meefri.app.ui.components.CustomDialog;
import com.meefri.app.validators.UsernameValidator;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.meefri.app.ui.components.LoginComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Designer generated component for the main-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("registration-view")
@JsModule("./src/views/registration-view.js")
public class RegistrationView extends PolymerTemplate<RegistrationView.MainViewModel> {

    @Id("cancelButton")
    private Button cancelButton;
    @Id("usernameTexField")
    private TextField usernameTexField;
    @Id("signUpButton")
    private Button signUpButton;
    @Id("password")
    private PasswordField password;
    @Id("confirmPassword")
    private PasswordField confirmPassword;
    @Id("email")
    private EmailField email;
    @Id("confirmEmail")
    private EmailField confirmEmail;

    /**
     * Creates a new RegistrationView.
     */
    public RegistrationView(@Autowired UserRepository userRepository) {

        Binder<User> binderUsername = new Binder<>();
        Binder<User> binderPassword = new Binder<>();
        Binder<User> binderConfirmPassword = new Binder<>();
        Binder<User> binderEmail = new Binder<>();
        Binder<User> binderConfirmEmail = new Binder<>();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        binderUsername.forField(usernameTexField)
                .withValidator((String name) -> {
                    String regex = "^[aA-zZ]\\w{5,29}$";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(name);

                    return m.matches();
                },"The given username is invalid. Valid username contains at least 6 characters. Only upper and lower case letters.")
                .withValidator( (String name) -> userRepository.findByUsername(name) == null, "Username exists")
                .bind(User::getUsername,User::setUsername);

        binderPassword.forField(password)
                .withValidator( (String name) -> {
                    String regex = "^[aA-zZ].{6,29}$";
                    String regexSpecial = ".*[$&+,:;=\\\\?@#|/'<>.^*()%!-]+.*";

                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(name);

                    Pattern pSpecial = Pattern.compile(regexSpecial);
                    Matcher mSpecial = pSpecial.matcher(name);

                    return m.matches() && mSpecial.matches();

                },"Password is invalid. Correct password starts with a letter, contains at least 7 characters and includes at least one special character.")
                .bind(User::getPassword,User::setPassword);

        binderConfirmPassword.forField(confirmPassword)
                .withValidator((String name) -> name.equals(password.getValue()),"Passwords are not the same")
                .bind(User::getPassword,User::setPassword);

        binderEmail.forField(email)
                .withValidator((String name) -> {
                    final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(name);
                    return m.matches();
                },"Given email is invalid")
                .withValidator((String name) -> userRepository.findByEmail(name) == null, "The given email is taken")
                .bind(User::getEmail,User::setEmail);

        binderConfirmEmail.forField(confirmEmail)
                .withValidator( (String name)-> name.equals(email.getValue()),"Emails are not the same"
                ).bind(User::getEmail,User::setEmail);

        usernameTexField.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<TextField, String>>() {
            @Override
            public void valueChanged(AbstractField.ComponentValueChangeEvent<TextField, String> textFieldStringComponentValueChangeEvent) {
                binderUsername.validate();
            }
        });

        password.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<PasswordField, String>>() {
            @Override
            public void valueChanged(AbstractField.ComponentValueChangeEvent<PasswordField, String> passwordFieldStringComponentValueChangeEvent) {
                binderPassword.validate();
                if(!confirmPassword.getValue().isEmpty()) binderConfirmPassword.validate();
            }
        });

        confirmPassword.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<PasswordField, String>>() {
            @Override
            public void valueChanged(AbstractField.ComponentValueChangeEvent<PasswordField, String> passwordFieldStringComponentValueChangeEvent) {
                binderConfirmPassword.validate();
            }
        });

        email.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<EmailField, String>>() {
            @Override
            public void valueChanged(AbstractField.ComponentValueChangeEvent<EmailField, String> emailFieldStringComponentValueChangeEvent) {
                binderEmail.validate();
                if(!confirmEmail.getValue().isEmpty()) binderConfirmEmail.validate();
            }
        });

        confirmEmail.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<EmailField, String>>() {
            @Override
            public void valueChanged(AbstractField.ComponentValueChangeEvent<EmailField, String> emailFieldStringComponentValueChangeEvent) {
                binderConfirmEmail.validate();
            }
        });

        signUpButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {

                if(binderUsername.isValid() && binderPassword.isValid() && binderConfirmPassword.isValid()
                        && binderEmail.isValid() && binderConfirmEmail.isValid()) {

                    EmailService emailService = new EmailService();
                    emailService.createVerificationCode();
                    try {
                        emailService.sendMail(email.getValue(),"<p> Verification Code: " + "<b>" + emailService.getVerificationCode() + "</b> </p>" +
                                                                        "<p> Username: " + "<b>" + usernameTexField.getValue() + "</b></p>" +
                                                                        "<p> Password: " + "<b>" + password.getValue()+ "</b></p>" +
                                                                        "<p> Now verify your account by logging in and entering your verification code. Good luck. </p>");
                    } catch (IOException | MessagingException e) {
                        e.printStackTrace();
                    }

                    User user = new User();
                    user.setUsername(usernameTexField.getValue());
                    user.setPassword(bCryptPasswordEncoder.encode(password.getValue()));
                    user.setEmail(email.getValue());
                    user.setUserFridge(new UserFridge());
                    user.setUserAccountInformation(new UserAccountInformation(emailService.getVerificationCode()));
                    user.setUserActivityInformation(new UserActivityInformation());
                    user.setUserPersonalInformation(new UserPersonalInformation());
                    user.setUserCreatedRecipes(new ArrayList<>());
                    user.setUserLikedRecipes(new ArrayList<>());
                    userRepository.save(user);

                    cancelButton.click();
                    CustomDialog customDialog = new CustomDialog();
                    customDialog.openCustomDialog("You have created your account. Check your email for the VERIFICATION CODE.");

                } else {

                    binderConfirmEmail.validate();
                    binderConfirmPassword.validate();
                    binderEmail.validate();
                    binderPassword.validate();
                    binderUsername.validate();
                }
            }
        });

    }

    public Button getCancelButton() {
        return cancelButton;
    }

    /**
     * This model binds properties between RegistrationView and main-view
     */
    public interface MainViewModel extends TemplateModel {

    }
}
