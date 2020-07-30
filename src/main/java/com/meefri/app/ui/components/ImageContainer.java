package com.meefri.app.ui.components;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Data;


public class ImageContainer {

    VerticalLayout verticalLayoutOne;
    VerticalLayout verticalLayoutTwo;
    VerticalLayout verticalLayoutThree;

    public VerticalLayout getVerticalLayoutOne() {
        return verticalLayoutOne;
    }

    public void setVerticalLayoutOne(VerticalLayout verticalLayoutOne) {
        this.verticalLayoutOne = verticalLayoutOne;
    }

    public VerticalLayout getVerticalLayoutTwo() {
        return verticalLayoutTwo;
    }

    public void setVerticalLayoutTwo(VerticalLayout verticalLayoutTwo) {
        this.verticalLayoutTwo = verticalLayoutTwo;
    }

    public VerticalLayout getVerticalLayoutThree() {
        return verticalLayoutThree;
    }

    public void setVerticalLayoutThree(VerticalLayout verticalLayoutThree) {
        this.verticalLayoutThree = verticalLayoutThree;
    }

    public ImageContainer() {
        this.verticalLayoutOne = new VerticalLayout();
        this.verticalLayoutTwo = new VerticalLayout();
        this.verticalLayoutThree = new VerticalLayout();
    }
}
