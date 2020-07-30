package com.meefri.app.ui.views;

import com.meefri.app.data.User;
import com.meefri.app.repositories.UserRepository;
import com.meefri.app.security.LoggedUser;
import com.meefri.app.ui.components.CustomDialog;
import com.meefri.app.ui.components.CustomGrid;
import com.meefri.app.ui.components.SearchingBarComponent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import elemental.json.Json;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.meefri.app.data.Recipe;
import com.meefri.app.repositories.RecipeRepository;
import com.meefri.app.ui.components.MenuComponent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Tag("create-recipe-view")
@JsModule("./src/views/create-recipe-view.js")
@Route(value = CreateRecipeView.ROUTE)
@PageTitle("CreateRecipe")
public class CreateRecipeView extends PolymerTemplate<CreateRecipeView.CreateRecipeViewModel> {

    static final String ROUTE = "create";
    List<byte[]> imageList = new ArrayList<>();

    @Id("menuComponent")
    private MenuComponent menuComponent;

    @Id("vaadinUpload")
    private Upload vaadinUpload;

    @Id("vaadinHorizontalLayout")
    private HorizontalLayout vaadinHorizontalLayout;

    @Id("searchingBarComponent")
    private SearchingBarComponent searchingBarComponent;

    @Id("recipeName")
    private TextField recipeName;

    @Id("recipeContent")
    private TextArea recipeContent;

    @Id("vaadinButton")
    private Button saveButton;

    public CreateRecipeView(@Autowired RecipeRepository recipeRepository, @Autowired UserRepository userRepository, @Autowired LoggedUser loggedUser) {

        menuComponent.setColorToChoosenButton("createView");
        searchingBarComponent.setSearchingModeOff();
        vaadinHorizontalLayout.getStyle().set("overflow-x", "auto");
        vaadinHorizontalLayout.setWidth("100%");
        vaadinUpload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        MemoryBuffer memoryBuffer = new MemoryBuffer();
        vaadinUpload.setReceiver(memoryBuffer);
        vaadinUpload.setMaxFiles(4);
        vaadinUpload.setMaxFileSize(500000);
        vaadinUpload.getElement().setPropertyJson("files", Json.createArray());

        NativeButton uploadButton = new NativeButton("Upload pictures");
        vaadinUpload.setUploadButton(uploadButton);

        Span dropLabel = new Span("Drag and drop pictures here");
        vaadinUpload.setDropLabel(dropLabel);

        Span dropIcon = new Span("");
        vaadinUpload.setDropLabelIcon(dropIcon);

        vaadinUpload.addFinishedListener(e -> {

            InputStream inputStream = memoryBuffer.getInputStream();
            InputStream inputStreamBytes = memoryBuffer.getInputStream();
            byte[] bytes = new byte[0];

            StreamResource resource = new StreamResource("image.jpg", () -> inputStream);
            Image imageFromStream = new Image(resource , "image");
            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.getStyle().clear();
            Button button = new Button();
            button.setThemeName("primary error");
            button.setText("Delete");
            button.getStyle().set("align-self" , "left");

            try {
                bytes = IOUtils.toByteArray(inputStreamBytes);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            byte[] finalBytes = bytes;
            button.addClickListener(l -> {
                vaadinUpload.setMaxFiles(1);
                Span x = new Span("");
                vaadinUpload.setDropLabelIcon(x);
                imageList.remove(finalBytes);
                vaadinHorizontalLayout.remove(verticalLayout);
                if(imageList.isEmpty()) vaadinHorizontalLayout.setHeight(null);
            });

            imageFromStream.setHeight("200px");
            verticalLayout.add(imageFromStream,button);
            vaadinHorizontalLayout.add(verticalLayout);
            //vaadinHorizontalLayout.setHeight("110%");
            vaadinUpload.getElement().setPropertyJson("files", Json.createArray());
            vaadinUpload.setDropLabelIcon(dropIcon);
            imageList.add(bytes);

            if(imageList.size() > 2){
                Span x = new Span("Only 3 files are allowed!");
                vaadinUpload.setMaxFiles(0);
                vaadinUpload.setDropLabelIcon(x);
            }

        });

        vaadinUpload.addFileRejectedListener(e -> {
            Span x = new Span(e.getErrorMessage());
            vaadinUpload.setDropLabelIcon(x);
        });

        saveButton.addClickListener(e -> {
            Recipe recipe = new Recipe();
            User user = loggedUser.getLoggedUser();
            Date dateobj = new Date();

            recipe.setContent(recipeContent.getValue());
            recipe.setName(recipeName.getValue());
            recipe.setListOfImages(imageList);
            recipe.setListOfIngredients(searchingBarComponent.getIngredientList());
            recipe.setUserId(user.getId());
            recipe.setDate(dateobj);
            recipe.setNumberOfLikes(0);

            if(recipeContent.getValue().isEmpty() || recipeName.getValue().isEmpty() || recipe.getListOfIngredients().isEmpty()) {
                CustomDialog customDialog = new CustomDialog();
                customDialog.openCustomDialog("CONTENT, NAME and LIST OF INGREDIENTS cant' be empty. Check your recipe!");
            } else {
               // recipeRepository.save(recipe);

                Thread thread = new Thread() {
                    public void run() {
                        recipeRepository.save(recipe);
                        user.getUserCreatedRecipes().add(recipe.getId());
                        userRepository.save(user);
                    }
                };

                thread.start();

                Dialog dialog = new Dialog();
                VerticalLayout verticalLayout = new VerticalLayout();
                Label label = new Label("You have created a recipe.");
                label.getStyle().set("align-self", "center");
                Button button = new Button("Accept");
                button.getStyle().set("align-self", "center");
                button.setThemeName("primary success");
                button.addClickListener(event -> {
                    dialog.close();
                    UI.getCurrent().navigate(MyRecipesView.class);
                });

                // dialog.setCloseOnOutsideClick(false);
                // dialog.setCloseOnEsc(false);

                dialog.addDialogCloseActionListener(event -> {
                    dialog.close();
                    UI.getCurrent().navigate(MyRecipesView.class);
                });

                verticalLayout.add(label, button);
                dialog.add(verticalLayout);
                dialog.open();
            }
        });
    }

    public interface CreateRecipeViewModel extends TemplateModel {

    }
}
