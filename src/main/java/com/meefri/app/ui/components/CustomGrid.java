package com.meefri.app.ui.components;

import com.meefri.app.data.Ingredient;
import com.meefri.app.data.Recipe;
import com.meefri.app.repositories.RecipeRepository;
import com.meefri.app.repositories.UserRepository;
import com.meefri.app.security.LoggedUser;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@CssImport(value = "./styles/myGridStyles.css", themeFor = "vaadin-grid")
public class CustomGrid {

    private Grid<RecipeGridComponent> gridComponentGrid = new Grid<>(RecipeGridComponent.class);
    private List<Ingredient> avaliableIngredient = new ArrayList<>();
    List<RecipeGridComponent> recipeGridComponents = new ArrayList<>();
    private ListDataProvider<RecipeGridComponent> recipeGridComponentDataProvider = DataProvider.ofCollection(recipeGridComponents);
    private LoggedUser loggedUser;
    private UserRepository userRepository;
    private RecipeRepository recipeRepository;

    public CustomGrid(@Autowired UserRepository userRepository, @Autowired RecipeRepository recipeRepository, @Autowired LoggedUser loggedUser){
        this.loggedUser = loggedUser;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    public void createGrid(){

        ListBox<String> listBox = new ListBox<>();
        listBox.setItems("Most accurate","Most recent", "Most favourite");
        listBox.setValue("Most accurate");

        listBox.addValueChangeListener(e -> {
            switch(e.getValue()){
                case "Most accurate":
                    setMostAccurateComparator();
                    break;
                case "Most recent":
                    setMostRecentComparator();
                    break;
                case "Most favourite":
                    setMostFavouriteComparator();
                    break;
            }

            recipeGridComponentDataProvider.refreshAll();
        });

        gridComponentGrid = new Grid<>(RecipeGridComponent.class);
        gridComponentGrid.getElement().setAttribute("theme", Lumo.DARK);
        gridComponentGrid.removeAllColumns();
        gridComponentGrid.addComponentColumn(RecipeGridComponent::getRecipeLayoutGrid).setHeader(listBox);
        gridComponentGrid.addClassName("my-grid");
        gridComponentGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        gridComponentGrid.setDataProvider(recipeGridComponentDataProvider);
        recipeGridComponents.clear();
        recipeGridComponentDataProvider.refreshAll();
    }

    public void setRecipesToGrid(List<Recipe> recipes, List<Ingredient> avaliableIngredients){
        this.avaliableIngredient = avaliableIngredients;
        recipeGridComponents.clear();
                for (Recipe r : recipes) {
                    RecipeLayoutGrid recipeLayoutGrid = new RecipeLayoutGrid(r,false,loggedUser,userRepository,recipeRepository,this);
                    RecipeGridComponent recipeGridComponent = new RecipeGridComponent(recipeLayoutGrid);
                    recipeGridComponents.add(recipeGridComponent);
                }
                recipeGridComponentDataProvider.refreshAll();
    }

    public void refreshOneRecipe(Recipe recipe){

       for(RecipeGridComponent r:recipeGridComponents){
           if(r.getRecipeLayoutGrid().getRecipe().getId().equals(recipe.getId())){
               r.getRecipeLayoutGrid().addToTheHeader();
               recipeGridComponentDataProvider.refreshAll();
               return;
           }
       }

        //recipeGridComponents.
      //  RecipeLayoutGrid recipeLayoutGrid = new RecipeLayoutGrid(recipe,false,loggedUser,userRepository,recipeRepository,this);
       // RecipeGridComponent recipeGridComponent = new RecipeGridComponent(recipeLayoutGrid);
       // recipeGridComponents.add(recipeGridComponent);

       // recipeGridComponentDataProvider.refreshAll();

    }

    public void setMostFavouriteComparator(){

        recipeGridComponentDataProvider.setSortComparator(new SerializableComparator<RecipeGridComponent>() {
            @Override
            public int compare(RecipeGridComponent o1, RecipeGridComponent o2) {
                return o2.getRecipeLayoutGrid().getRecipe().getNumberOfLikes() - o1.getRecipeLayoutGrid().getRecipe().getNumberOfLikes();
                //return 0;
            }
        });

    }

    public void setMostAccurateComparator(){
        recipeGridComponentDataProvider.setSortComparator(new SerializableComparator<RecipeGridComponent>() {
            @Override
            public int compare(RecipeGridComponent o1, RecipeGridComponent o2) {
                long x = o1.getRecipeLayoutGrid()
                        .getRecipe()
                        .getListOfIngredients()
                        .stream()
                        .filter(e -> avaliableIngredient.contains(e) )
                        .count()

                        -

                        o2.getRecipeLayoutGrid()
                                .getRecipe()
                                .getListOfIngredients()
                                .stream()
                                .filter(e -> avaliableIngredient.contains(e))
                                .count();

                if(x > 0) return -1;
                if(x < 0) return 1;

                return 0;
            }
        });
    }

    public void setMostRecentComparator(){

        recipeGridComponentDataProvider.setSortComparator(new SerializableComparator<RecipeGridComponent>() {
            @Override
            public int compare(RecipeGridComponent o1, RecipeGridComponent o2) {
                if( o1.getRecipeLayoutGrid().getRecipe().getDate().after(o2.getRecipeLayoutGrid().getRecipe().getDate()) ) return -1;
                if( o1.getRecipeLayoutGrid().getRecipe().getDate().before(o2.getRecipeLayoutGrid().getRecipe().getDate()) ) return 1;
                return 0;
            }
        });

    }

    public Grid<RecipeGridComponent> getGrid(){
        return gridComponentGrid;
    }
}
