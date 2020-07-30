package com.meefri.app.ui.components;

import com.meefri.app.data.Ingredient;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.Data;


public class ChoosenIngredient {

    private Button button;

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public HorizontalLayout getHorizontalLayout() {
        return horizontalLayout;
    }

    public void setHorizontalLayout(HorizontalLayout horizontalLayout) {
        this.horizontalLayout = horizontalLayout;
    }

    private Ingredient ingredient;
    private Label label;
    private HorizontalLayout horizontalLayout;
}
