package com.meefri.app.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the footer-component template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("footer-component")
@JsModule("./src/components/footer-component.js")
public class FooterComponent extends PolymerTemplate<FooterComponent.FooterComponentModel> {

    @Id("footerHorizontalLayout")
    private HorizontalLayout footerHorizontalLayout;
    @Id("compareButton")
    private Button compareButton;
    @Id("addFavouriteButton")
    private Button addFavouriteButton;

    /**
     * Creates a new FooterComponent.
     */
    public FooterComponent() {
        // You can initialise any data required for the connected UI components here.
    }

    public HorizontalLayout getFooterHorizontalLayout(){
        return footerHorizontalLayout;
    }

    public Button getCompareButton(){
        return compareButton;
    }

    public Button getAddFavouriteButton(){
        return addFavouriteButton;
    }


    /**
     * This model binds properties between FooterComponent and footer-component
     */
    public interface FooterComponentModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
