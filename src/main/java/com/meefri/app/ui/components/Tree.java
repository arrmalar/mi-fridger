package com.meefri.app.ui.components;

import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.function.ValueProvider;

public class Tree<T> extends TreeGrid<T> {

    public Tree(ValueProvider<T, ?> valueProvider) {
        addHierarchyColumn(valueProvider);
    }
}
