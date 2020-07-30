package com.meefri.app.ui.views;

import com.awesomecontrols.subwindow.SubWindow;
import com.awesomecontrols.subwindow.SubWindowDesktop;
import com.meefri.app.data.Ingredient;
import com.meefri.app.data.User;
import com.meefri.app.repositories.IngredientRepository;
import com.meefri.app.repositories.UserRepository;
import com.meefri.app.security.LoggedUser;
import com.meefri.app.ui.components.MyFridgeCatgoryElement;
import com.meefri.app.ui.components.Tree;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.meefri.app.ui.components.MenuComponent;
import net.bytebuddy.pool.TypePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Tag("fridge-view")
@JsModule("./src/views/fridge-view.js")
@Route(value = FridgeView.ROUTE)
@StyleSheet("fridgeViewStyle.css")
public class FridgeView extends PolymerTemplate<FridgeView.FridgeViewModel> {

    static final String ROUTE = "fridge";

    @Id("menuComponent")
    private MenuComponent menuComponent;

    private Ingredient draggedIngredient = null;

    //private Grid<MyFridgeCatgoryElement> myFridgeCatgoryElementGrid = new Grid<>(MyFridgeCatgoryElement.class);
    //private List<MyFridgeCatgoryElement> listFridgeCategoryElements = new ArrayList<>();

    private User currentUser;

    @Id("verticalLayoutCategory")
    private VerticalLayout verticalLayoutCategory;

    @Id("vaadinHorizontalLayout")
    private HorizontalLayout vaadinHorizontalLayout;



    public FridgeView(@Autowired LoggedUser loggedUser,@Autowired IngredientRepository ingredientRepository,@Autowired UserRepository userRepository) {

        menuComponent.setColorToChoosenButton("myFridgeView");

        Grid<MyFridgeCatgoryElement> myFridgeCatgoryElementGrid = new Grid<>(MyFridgeCatgoryElement.class);
        List<MyFridgeCatgoryElement> listFridgeCategoryElements = new ArrayList<>();

        myFridgeCatgoryElementGrid.removeAllColumns();
        myFridgeCatgoryElementGrid.addComponentColumn(MyFridgeCatgoryElement::getFirstColumn);
        myFridgeCatgoryElementGrid.addComponentColumn(MyFridgeCatgoryElement::getSecondColumn);
        myFridgeCatgoryElementGrid.addComponentColumn(MyFridgeCatgoryElement::getThirdColumn);
        myFridgeCatgoryElementGrid.addComponentColumn(MyFridgeCatgoryElement::getFourthColumn);
        myFridgeCatgoryElementGrid.addComponentColumn(MyFridgeCatgoryElement::getFifthColumn);

        this.currentUser = loggedUser.getLoggedUser();
        List<Ingredient> fridgeIngredients = new ArrayList<>(currentUser.getUserFridge().getUserIngredients().isEmpty()
                ? Collections.emptyList() : new ArrayList<>(currentUser.getUserFridge().getUserIngredients()));
        List<Ingredient> avaliableIngredients = ingredientRepository.findAll().stream().filter(r -> !fridgeIngredients.contains(r)).collect(Collectors.toList());

        String[] categories = {"AddedSweeteners.png","Alcohol.png","Beverages.png","Condiments.png","Dairy.png",
                "DairyAlternatives.png","Desserts&Snacks.png","Fish.png","Fruits.png","Grains.png",
                "Legumes.png","Meats.png","Nuts.png","Oils.png","Sauces.png",
                "Seafood.png", "Seasonings.png", "Soup.png", "Spices.png", "Vegetables.png"};

        String[] categoriesNames = {"Added Sweeteners","Alcohol","Beverages","Condiments","Dairy",
                "Dairy Alternatives","Desserts & Snacks","Fish","Fruits","Grains",
                "Legumes","Meats","Nuts","Oils","Sauces",
                "Seafood", "Seasonings", "Soup", "Spices", "Vegetables"};

        UI.getCurrent().access(new Command() {
            @Override
            public void execute() {

                MyFridgeCatgoryElement myFridgeCatgoryElement = new MyFridgeCatgoryElement();

                for (int k = 0; k < categories.length; k++) {

                    if (k % 5 == 0) {
                        myFridgeCatgoryElement = new MyFridgeCatgoryElement();
                    }
                    File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("static/frontend/images/categoryPictures/" + categories[k])).getFile());
                    Image image = new Image(new StreamResource("img.png", () -> {
                        try {
                            return new FileInputStream(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }), "alt text");

                    image.setHeight("65px");

                    Button button = new Button(categoriesNames[k]);
                    button.getStyle().set("height", "auto");
                    button.getStyle().set("white-space", "normal");
                    VerticalLayout verticalLayout = new VerticalLayout();
                    button.setThemeName("primary");

                    List<Ingredient> tempFridgeIngredients = fridgeIngredients
                            .stream()
                            .filter(r -> r.getCategory().equals(button.getText()))
                            .collect(Collectors.toList());

                    List<Ingredient> tempAvaliableIngredients = avaliableIngredients
                            .stream()
                            .filter(r -> r.getCategory().equals(button.getText()))
                            .collect(Collectors.toList());

                    ListDataProvider<Ingredient> fridgeIngredientsDataProvider = DataProvider.ofCollection(tempFridgeIngredients);
                    ListDataProvider<Ingredient> avaliableIngredientsDataProvider = DataProvider.ofCollection(tempAvaliableIngredients);

                    fridgeIngredientsDataProvider.setSortComparator(new SerializableComparator<Ingredient>() {
                        @Override
                        public int compare(Ingredient o1, Ingredient o2) {
                            return (o1.getName()).compareTo(o2.getName());
                        }
                    });

                    avaliableIngredientsDataProvider.setSortComparator(new SerializableComparator<Ingredient>() {
                        @Override
                        public int compare(Ingredient o1, Ingredient o2) {
                            return (o1.getName()).compareTo(o2.getName());
                        }
                    });

                    Grid<Ingredient> ingredientGrid = new Grid<>(Ingredient.class);
                    ingredientGrid.removeAllColumns();
                    Label label = new Label("INGREDIENTS:");

                    ingredientGrid.addColumn(Ingredient::getName).setHeader(label);
                    ingredientGrid.setDataProvider(fridgeIngredientsDataProvider);

                    button.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
                        @Override
                        public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {

                            Dialog dialog = new Dialog();
                            dialog.setWidth("450px");

                            HorizontalLayout dialogHorizontalLayout = new HorizontalLayout();

                            Grid<Ingredient> gridIngredients = new Grid<>(Ingredient.class);

                            Grid<Ingredient> gridFridge = new Grid<>(Ingredient.class);

                            gridFridge.setDataProvider(fridgeIngredientsDataProvider);
                            gridIngredients.setDataProvider(avaliableIngredientsDataProvider);

                            gridIngredients.removeAllColumns();
                            gridIngredients.addColumn(Ingredient::getName).setHeader("Avaliable ingredients:");

                            gridFridge.removeAllColumns();
                            gridFridge.addColumn(Ingredient::getName).setHeader("Ingredients in the Fridge:");

                            gridIngredients.setRowsDraggable(true);
                            gridFridge.setRowsDraggable(true);

                            gridIngredients.setDropMode(GridDropMode.ON_GRID);
                            gridFridge.setDropMode(GridDropMode.ON_GRID);

                            gridFridge.addDragStartListener(event -> {
                                draggedIngredient = event.getDraggedItems().get(0);
                                gridIngredients.setVerticalScrollingEnabled(false);

                            });

                            gridIngredients.addFocusListener(new ComponentEventListener<FocusNotifier.FocusEvent<Grid<Ingredient>>>() {
                                @Override
                                public void onComponentEvent(FocusNotifier.FocusEvent<Grid<Ingredient>> gridFocusEvent) {
                                    gridIngredients.setVerticalScrollingEnabled(true);
                                }
                            });

                            gridIngredients.addDragStartListener(event -> {
                                draggedIngredient = event.getDraggedItems().get(0);
                                gridIngredients.setVerticalScrollingEnabled(false);
                            });

                            gridFridge.addDropListener(event -> {
                                if (!fridgeIngredients.contains(draggedIngredient)) {

                                    avaliableIngredients.remove(draggedIngredient);
                                    fridgeIngredients.add(draggedIngredient);
                                    tempFridgeIngredients.add(draggedIngredient);
                                    tempAvaliableIngredients.remove(draggedIngredient);

                                    currentUser.getUserFridge().addIngredients(draggedIngredient);
                                    // userRepository.save(currentUser);
                                }

                                fridgeIngredientsDataProvider.refreshAll();
                                avaliableIngredientsDataProvider.refreshAll();
                            });

                            gridIngredients.addDropListener(event -> {

                                if (!avaliableIngredients.contains(draggedIngredient)) {

                                    fridgeIngredients.remove(draggedIngredient);
                                    avaliableIngredients.add(draggedIngredient);
                                    tempFridgeIngredients.remove(draggedIngredient);
                                    tempAvaliableIngredients.add(draggedIngredient);

                                    currentUser.getUserFridge().deleteIngredient(draggedIngredient);
                                    // userRepository.save(currentUser);

                                }

                                fridgeIngredientsDataProvider.refreshAll();
                                avaliableIngredientsDataProvider.refreshAll();

                            });

                            dialog.addDialogCloseActionListener(event -> {
                                Thread thread = new Thread() {
                                    public void run() {
                                        userRepository.save(currentUser);
                                    }
                                };

                                thread.start();
                                dialog.close();
                            });


                            dialogHorizontalLayout.add(gridIngredients, gridFridge);
                            VerticalLayout verticalLayoutHeader = new VerticalLayout();
                            VerticalLayout verticalLyoutFoot = new VerticalLayout();

                            Label label = new Label("Drag element between tables");
                            label.getStyle().set("align-self", "center");
                            verticalLayoutHeader.add(label);

                            Button button = new Button("Done");
                            button.getStyle().set("align-self", "center");
                            button.setThemeName("primary success");
                            button.addClickListener(event -> {
                                Thread thread = new Thread() {
                                    public void run() {
                                        userRepository.save(currentUser);
                                    }
                                };
                                thread.start();
                                dialog.close();
                            });
                            verticalLyoutFoot.add(button);

                            dialog.add(verticalLayoutHeader, dialogHorizontalLayout, verticalLyoutFoot);
                            vaadinHorizontalLayout.add(dialog);

                            dialog.open();
                        }
                    });

                    verticalLayout.add(image, button, ingredientGrid);

                    switch (k % 5) {
                        case 0:
                            myFridgeCatgoryElement.setFirstColumn(verticalLayout);
                            break;
                        case 1:
                            myFridgeCatgoryElement.setSecondColumn(verticalLayout);
                            break;
                        case 2:
                            myFridgeCatgoryElement.setThirdColumn(verticalLayout);
                            break;
                        case 3:
                            myFridgeCatgoryElement.setFourthColumn(verticalLayout);
                            break;
                        case 4: {
                            myFridgeCatgoryElement.setFifthColumn(verticalLayout);
                            listFridgeCategoryElements.add(myFridgeCatgoryElement);
                        }
                        break;

                    }
                }

                myFridgeCatgoryElementGrid.setItems(listFridgeCategoryElements);
                verticalLayoutCategory.add(myFridgeCatgoryElementGrid);

            }});
    }



    private void createDialog(){

    }

    public interface FridgeViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
