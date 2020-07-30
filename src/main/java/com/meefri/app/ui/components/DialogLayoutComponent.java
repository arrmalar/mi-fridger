package com.meefri.app.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the dialog-layout-component template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("dialog-layout-component")
@JsModule("./src/components/dialog-layout-component.js")
public class DialogLayoutComponent extends PolymerTemplate<DialogLayoutComponent.DialogLayoutComponentModel> {

    @Id("dialogLayoutComponent")
    private VerticalLayout dialogLayoutComponent;

    /**
     * Creates a new DialogLayoutComponent.
     */
    public DialogLayoutComponent() {
        // You can initialise any data required for the connected UI components here.
    }

    public void addGridDialogLayoutComponent(Component component){
        dialogLayoutComponent.add(component);
    }

    /**
     * This model binds properties between DialogLayoutComponent and dialog-layout-component
     */
    public interface DialogLayoutComponentModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
