package com.meefri.app.ui.components;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.meefri.app.ui.views.*;

@Tag("menu-component")
@JsModule("./src/components/menu-component.js")
public class MenuComponent extends PolymerTemplate<MenuComponent.MenuComponentModel> {

    @Id("mainViewButton")
    private Button mainViewButton;
    @Id("searchRecipeButton")
    private Button searchRecipeButton;
    @Id("createRecipeButton")
    private Button createRecipeButton;
    @Id("myFridgeButton")
    private Button myFridgeButton;
    @Id("myAccountButton")
    private Button myAccountButton;
    @Id("signOutButton")
    private Button signOutButton;
    @Id("myRecipesButton")
    private Button myRecipesButton;
    @Id("myFavouriteButton")
    private Button myFavouriteButton;

    class InnerButtonColorClass{
        private Button button;
        private String viewName;

        InnerButtonColorClass(Button button,String viewName){
            this.button = button;
            this.viewName = viewName;
        }


    }

    private InnerButtonColorClass[] buttons = {new InnerButtonColorClass(mainViewButton,"mainView"),
            new InnerButtonColorClass(searchRecipeButton,"searchView"),
            new InnerButtonColorClass(createRecipeButton,"createView"),
            new InnerButtonColorClass(myFridgeButton,"myFridgeView"),
            new InnerButtonColorClass(myAccountButton,"myAccountView"),
            new InnerButtonColorClass(myRecipesButton,"myRecipesView"),
            new InnerButtonColorClass(myFavouriteButton,"myFavouriteView")
    };

    public MenuComponent() {
        mainViewButton.addClickListener(e -> { UI.getCurrent().navigate(MainView.class); });
        searchRecipeButton.addClickListener(e -> UI.getCurrent().navigate(SearchRecipeView.class));
        createRecipeButton.addClickListener(e -> UI.getCurrent().navigate(CreateRecipeView.class));
        myRecipesButton.addClickListener(e -> UI.getCurrent().navigate(MyRecipesView.class));
        myFridgeButton.addClickListener(e -> UI.getCurrent().navigate(FridgeView.class));
        myAccountButton.addClickListener(e -> UI.getCurrent().navigate(AccountView.class));
        myFavouriteButton.addClickListener(e -> UI.getCurrent().navigate(MyFavouriteRecipesView.class));
        signOutButton.addClickListener(e -> UI.getCurrent().getPage().executeJs("window.location.href = \"/logout\";"));
    }

    public void setColorToChoosenButton(String choosenButton){

        for(InnerButtonColorClass i : buttons) {
            if (i.viewName.equals(choosenButton)) i.button.getStyle().set("background-color","hsl(216, 100%, 40%)");
             else i.button.setThemeName("primary success");
        }
    }


    public interface MenuComponentModel extends TemplateModel {



    }

}
