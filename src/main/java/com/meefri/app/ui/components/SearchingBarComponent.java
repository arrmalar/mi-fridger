package com.meefri.app.ui.components;

import com.meefri.app.data.Recipe;
import com.meefri.app.repositories.RecipeRepository;
import com.meefri.app.ui.views.SearchRecipeView;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import com.meefri.app.data.Ingredient;
import com.meefri.app.repositories.IngredientRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.vaadin.flow.component.combobox.ComboBox.*;

@Tag("searching-bar-component")
@JsModule("./src/components/searching-bar-component.js")
public class SearchingBarComponent extends PolymerTemplate<SearchingBarComponent.SearchIngredientComponentModel> {

    private List<ChoosenIngredient> choosenIngredients = new ArrayList<>();
    private List<Ingredient> ingredients = new ArrayList<>();
    private Grid<ChoosenIngredient> vaadinGrid = new Grid<>(ChoosenIngredient.class);
    private RecipeRepository recipeRepository;


    @Id("comboBox")
    private ComboBox<Ingredient> comboBox;

    @Id("vaadinVerticalLayout")
    private VerticalLayout vaadinVerticalLayout;

    @Id("clearButton")
    private Button clearButton;

    private String title;

    private boolean searchingMode = false;

    private ListDataProvider<ChoosenIngredient> listDataProviderChoosenIngredients = DataProvider.ofCollection(choosenIngredients);

    private SearchRecipeView searchRecipeView;

    public SearchingBarComponent(@Autowired IngredientRepository ingredientRepository, @Autowired RecipeRepository recipeRepository) {

        this.recipeRepository = recipeRepository;

        vaadinGrid.setDataProvider(listDataProviderChoosenIngredients);
        vaadinVerticalLayout.add(vaadinGrid);
        vaadinGrid.removeAllColumns();
        vaadinGrid.addComponentColumn(ChoosenIngredient::getHorizontalLayout).setHeader("Ingredients");

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        ListDataProvider<Ingredient> listDataProvider = DataProvider.ofCollection(ingredientList);
        listDataProvider.setSortComparator(new SerializableComparator<Ingredient>() {
            @Override
            public int compare(Ingredient o1, Ingredient o2) {
                return (o1.getName()).compareTo(o2.getName());
            }
        });

        ItemFilter<Ingredient> itemFilter = new ItemFilter<Ingredient>() {
            @Override
            public boolean test(Ingredient ingredient, String s) {
                return ingredient.getName().startsWith(s);
            }
        };

        comboBox.setDataProvider(itemFilter,listDataProvider);
        comboBox.setPageSize(30);

        clearButton.addClickListener(e ->{
            choosenIngredients.clear();
            listDataProviderChoosenIngredients.refreshAll();
            if(searchingMode)
                searchRecipeView.setCustomGridData(new ArrayList<>(),new ArrayList<>());
        });

        comboBox.setItemLabelGenerator(new ItemLabelGenerator<Ingredient>() {
            @Override
            public String apply(Ingredient s) {
                return s.getName();
            }
        });

        comboBox.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<Ingredient>, Ingredient>>() {
            @Override
            public void valueChanged(AbstractField.ComponentValueChangeEvent<ComboBox<Ingredient>, Ingredient> comboBoxIngredientComponentValueChangeEvent) {
                if(comboBoxIngredientComponentValueChangeEvent.getValue()!= null) {
                    createElement(comboBoxIngredientComponentValueChangeEvent.getValue());
                    if(searchingMode) {
                        searchRecipeView.setCustomGridData(recipeRepository.findDoableRecipeByAvaliableIngredients(getIngredientList()), getIngredientList());
                    }
                }
            }
        });
    }

    public List<Ingredient> getIngredientList(){
        this.ingredients = choosenIngredients.stream().map(ChoosenIngredient::getIngredient).collect(Collectors.toList());
        return ingredients;
    }

    public void createElement(Ingredient ingredient){

        if(choosenIngredients.stream().noneMatch(r -> { return ingredient.getName().equals(r.getIngredient().getName()); })){

            ChoosenIngredient choosenIngredient = new ChoosenIngredient();
            Button deleteButton = new Button("X");
            deleteButton.getStyle().set("width","10px");
            deleteButton.getStyle().set("color-text","white");
            deleteButton.getStyle().set("color","red");

            HorizontalLayout horizontalLayout = new HorizontalLayout();

            deleteButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
                @Override
                public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                    choosenIngredients.remove(choosenIngredient);

                    listDataProviderChoosenIngredients.refreshAll();

                    if(searchingMode){
                        if(!getIngredientList().isEmpty()) {
                            searchRecipeView.setCustomGridData(recipeRepository.findDoableRecipeByAvaliableIngredients(getIngredientList()), getIngredientList());
                        }
                        else
                            searchRecipeView.setCustomGridData(new ArrayList<>(),new ArrayList<>());
                    }
                }
            });

            Label label = new Label(ingredient.getName());
            label.setMaxWidth("65px");
            label.setMinWidth("65px");
            label.getStyle().set("white-space" , "pre-wrap" );
            horizontalLayout.add(label,deleteButton);

            choosenIngredient.setHorizontalLayout(horizontalLayout);
            choosenIngredient.setButton(deleteButton);
            choosenIngredient.setIngredient(ingredient);
            choosenIngredient.setLabel(label);

            choosenIngredients.add(choosenIngredient);

            listDataProviderChoosenIngredients.refreshAll();
        }
    }

    public void setSearchRecipeViewObjectInSearchingBar(SearchRecipeView searchRecipeView){
        this.searchRecipeView = searchRecipeView;
    }

    public void setSearchingModeOn(){
        this.searchingMode = true;
    }

    public void setSearchingModeOff(){
        this.searchingMode = false;
    }

    public ComboBox<Ingredient> getComboBox() {
        return comboBox;
    }

    public interface SearchIngredientComponentModel extends TemplateModel {
    }
}
