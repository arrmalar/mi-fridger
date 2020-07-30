package com.meefri.app.ui.components;

import com.meefri.app.data.Ingredient;
import com.meefri.app.data.User;
import com.meefri.app.repositories.RecipeRepository;
import com.meefri.app.repositories.UserRepository;
import com.meefri.app.security.LoggedUser;
import com.meefri.app.security.SecurityUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.meefri.app.data.Recipe;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag("recipe-layout-grid")
@JsModule("./src/components/recipe-layout-grid.js")
public class RecipeLayoutGrid extends PolymerTemplate<RecipeLayoutGrid.SearchRecipeGridComponentModel> {

    private Recipe recipe;
    @Id("ingredientsLayout")
    private VerticalLayout ingredientsLayout;
    @Id("imageLayout")
    private HorizontalLayout imageLayout;
    @Id("nameLayout")
    private HorizontalLayout nameLayout;
    @Id("footerLayout")
    private HorizontalLayout footerLayout;
    @Id("recipeContent")
    private TextArea recipeContent;
    @Id("headerLayout")
    private HorizontalLayout headerLayout;


    private LoggedUser loggedUser;
    private RecipeRepository recipeRepository;
    private UserRepository userRepository;
    private CustomGrid customGrid;

    public RecipeLayoutGrid(Recipe recipe,
                            boolean inDetails,
                            @Autowired LoggedUser loggedUser,
                            @Autowired UserRepository userRepository,
                            @Autowired RecipeRepository recipeRepository, CustomGrid customGrid) {

        this.customGrid = customGrid;
        this.loggedUser = loggedUser;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.recipe = recipe;
        Grid<ImageContainer> verticalLayoutGrid = new Grid<>(ImageContainer.class);
        verticalLayoutGrid.setWidthFull();
        verticalLayoutGrid.setHeightByRows(true);
        verticalLayoutGrid.recalculateColumnWidths();
        ImageContainer imageContainer = new ImageContainer();
        verticalLayoutGrid.removeAllColumns();
        verticalLayoutGrid.addComponentColumn(ImageContainer::getVerticalLayoutOne).setKey("one");
        verticalLayoutGrid.addComponentColumn(ImageContainer::getVerticalLayoutTwo).setKey("two");
        verticalLayoutGrid.addComponentColumn(ImageContainer::getVerticalLayoutThree).setKey("three");

        addToTheHeader();

        Label recipeName = new Label(recipe.getName());
        recipeName.getStyle().set("font-size", "24px");
        recipeName.getStyle().set("align-self","center");

        nameLayout.getStyle().set("justify-content","center");
        nameLayout.add(recipeName);

        if (recipe.getListOfImages().isEmpty()) {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("imageNotAvaliable.png")).getFile());
            Image image = new Image(new StreamResource("img.png", () -> {
                try {
                    return new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }), "alt text");

            if (inDetails)
                image.setHeight("250px");
            else
                image.setHeight("200px");

            image.getStyle().set("align-self", "center");

            VerticalLayout verticalLayout = new VerticalLayout(image);

            if (inDetails){
                imageContainer.setVerticalLayoutOne(verticalLayout);
                verticalLayoutGrid.removeColumnByKey("two");
                verticalLayoutGrid.removeColumnByKey("three");
            }
            else
                imageLayout.add(verticalLayout);

        } else {

            int index = 0;

            for (byte[] b : recipe.getListOfImages()) {

                StreamResource resource = new StreamResource("food.jpg", () -> new ByteArrayInputStream(b));
                Image image = new Image(resource, "food.jpg");
                if (inDetails)
                    image.setHeight("250px");
                else
                    image.setHeight("200px");

                image.getStyle().set("align-self", "center");

                VerticalLayout verticalLayout = new VerticalLayout(image);

                if (inDetails) {
                    if (index == 0) imageContainer.setVerticalLayoutOne(verticalLayout);
                    if (index == 1) imageContainer.setVerticalLayoutTwo(verticalLayout);
                    if (index == 2) imageContainer.setVerticalLayoutThree(verticalLayout);
                } else {
                    imageLayout.add(verticalLayout);
                }
                index++;
            }

            if(index == 1) {
                verticalLayoutGrid.removeColumnByKey("two");
                verticalLayoutGrid.removeColumnByKey("three");
            }

            if (index == 2) {
                verticalLayoutGrid.removeColumnByKey("three");
            }
        }

        if (inDetails) {
            verticalLayoutGrid.setItems(imageContainer);
            imageLayout.add(verticalLayoutGrid);
        }

        if(!inDetails) {

            createIngredientsLabels(inDetails);
            VerticalLayout verticalLayout = new VerticalLayout();
            Button showDetailsButton = new Button("Show recipe");
            showDetailsButton.getStyle().set("align-self","center");
            showDetailsButton.getStyle().set("background-color","hsl(120, 100%, 35%)");
            showDetailsButton.getStyle().set("color","white");
            verticalLayout.add(showDetailsButton);
            footerLayout.add(verticalLayout);

            showDetailsButton.addClickListener(event ->{

                Dialog showDetailsdialog = new Dialog();
                showDetailsdialog.setWidth("800px");

                Div div = new Div();
                RecipeLayoutGrid recipeLayoutGrid = new RecipeLayoutGrid(recipe,true,loggedUser,userRepository,recipeRepository,customGrid);
                div.add(recipeLayoutGrid);
                recipeLayoutGrid.addToTheFooter(showDetailsdialog);
                showDetailsdialog.removeAll();
                showDetailsdialog.add(recipeLayoutGrid);
                showDetailsdialog.setWidth("800px");
                showDetailsdialog.open();
            });

        } else {
            createIngredientsLabels(inDetails);
        }
    }


    public void addToTheHeader() {

        headerLayout.removeAll();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        //horizontalLayout.getStyle().set("justify-content", "evenly");
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
        Label likedLabel = new Label(" Added to favourite by: " + recipe.getNumberOfLikes());
        likedLabel.setWidth("100%");
        likedLabel.getStyle().set("font-size", "20px");
        horizontalLayout.add(likedLabel);

        if (SecurityUtils.isUserLoggedIn()) {
            String fileName = "";
            if (loggedUser.getLoggedUser().getUserLikedRecipes().contains(recipe.getId()))
                fileName = "starFilled.png";
            else
                fileName = "starEmpty.png";


                File file = new File(Objects.requireNonNull(getClass()
                        .getClassLoader()
                        .getResource("static/frontend/images/recipeLayoutPictures/" + fileName)).getFile());

                Image image = new Image(new StreamResource("img.png", () -> {
                    try {
                        return new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                }), "alt text");

                image.setHeight("40px");
                //headerLayout.add(image);
                horizontalLayout.add(image);

            }
            headerLayout.add(horizontalLayout);
    }

    private void addToTheFooter(Dialog showDetailsdialog){

        footerLayout.removeAll();
        //footerLayout.getStyle().set("justify-content", "space-between");
        footerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        Button hideButton = new Button("Hide recipe");
        hideButton.setThemeName("primary error");
        hideButton.getStyle().set("color","white");

        hideButton.addClickListener(event -> {
            showDetailsdialog.close();
        });

        if(SecurityUtils.isUserLoggedIn()){

            User user = loggedUser.getLoggedUser();
            Button addToFavourite = new Button("Add to favourite");
            //addToFavourite.getStyle().set("color","black");
            if(!user.getUserLikedRecipes().contains(recipe.getId())) {
                addToFavourite.setText("Add to favourite");
                addToFavourite.getStyle().set("background-color","hsl(48, 100%, 45%)");
                addToFavourite.getStyle().set("color","white");
                addToFavourite.addClickListener(event -> {
                    if (!user.getUserLikedRecipes().contains(recipe.getId())) {
                        user.getUserLikedRecipes().add(recipe.getId());
                        userRepository.save(user);
                        recipe.setNumberOfLikes(recipe.getNumberOfLikes() + 1);
                        recipeRepository.save(recipe);
                        customGrid.refreshOneRecipe(recipe);
                        this.addToTheHeader();
                        this.addToTheFooter(showDetailsdialog);
                    }
                });
            } else {
                addToFavourite.setText("Remove from favourite");
                addToFavourite.getStyle().set("background-color","hsl(0, 100%, 50%)");
                addToFavourite.getStyle().set("color","white");
                addToFavourite.addClickListener(event ->{
                    user.getUserLikedRecipes().remove(recipe.getId());
                    userRepository.save(user);
                    recipe.setNumberOfLikes(recipe.getNumberOfLikes()-1);
                    recipeRepository.save(recipe);
                    customGrid.refreshOneRecipe(recipe);
                    this.addToTheHeader();
                    this.addToTheFooter(showDetailsdialog);
                });
            }

            Button compareWithFridge = new Button("Compare with my fridge");
            compareWithFridge.getStyle().set("background-color","hsl(216, 100%, 40%)");
            compareWithFridge.getStyle().set("color","white");
            compareWithFridge.addClickListener(event -> {

                ingredientsLayout.removeAll();

                for (Ingredient i : recipe.getListOfIngredients()) {
                    Label label = new Label("• " + i.getName());
                    label.setWidth("140px");

                    if (loggedUser.getLoggedUser().getUserFridge().getUserIngredients().contains(i)) {
                        label.getStyle().set("background-color", "hsl(120, 100%, 80%)");
                    } else {
                        label.getStyle().set("background-color", "hsl(0, 100%, 80%)");
                    }
                    ingredientsLayout.add(label);
                }
            });

            footerLayout.add(compareWithFridge,hideButton,addToFavourite);
        }   else {
            footerLayout.add(hideButton);
        }
    }

    private void createIngredientsLabels(boolean inDetails){
        if(inDetails){
            for (Ingredient i : recipe.getListOfIngredients()) {
                ingredientsLayout.add(new Label("• " + i.getName()));
            }

            recipeContent.setValue(recipe.getContent());
            recipeContent.setReadOnly(true);
        } else {

            for(int k = 0 ; k < recipe.getListOfIngredients().size() && k < 3 ; k++){
                ingredientsLayout.add(new Label("• " + recipe.getListOfIngredients().get(k).getName()));
            }

            if(recipe.getListOfIngredients().size() > 3)
                ingredientsLayout.add(new Label("• ..."));

            if (recipe.getContent().length() > 200) {
                recipeContent.setValue( recipe.getContent().substring(0, 200) + " ...");

            } else {
                recipeContent.setValue(recipe.getContent());
            }
        }
    }

    public Recipe getRecipe(){
        return recipe;
    }

    public interface SearchRecipeGridComponentModel extends TemplateModel {
    }
}
