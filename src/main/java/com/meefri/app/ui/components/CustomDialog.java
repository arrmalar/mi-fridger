package com.meefri.app.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CustomDialog extends Dialog {

    public CustomDialog(){
        super();
    }

    public void openCustomDialog(String message){

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.getStyle().set("align-items","center");
        Label label = new Label(message);
        Button button = new Button("Accept");
        button.setThemeName("primary success");
        verticalLayout.add(label,button);
        button.addClickListener(d -> {
            this.close();
        });
        this.add(verticalLayout);
        this.open();
    }

}
