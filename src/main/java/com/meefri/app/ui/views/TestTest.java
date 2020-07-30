package com.meefri.app.ui.views;

import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the test-test template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("test-test")
@JsModule("./test-test.js")
public class TestTest extends PolymerTemplate<TestTest.TestTestModel> {

    /**
     * Creates a new TestTest.
     */
    public TestTest() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between TestTest and test-test
     */
    public interface TestTestModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
