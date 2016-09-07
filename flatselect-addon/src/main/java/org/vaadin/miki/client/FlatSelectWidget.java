package org.vaadin.miki.client;

import com.google.gwt.user.client.ui.Label;

// Extend any GWT Widget
public class FlatSelectWidget extends Label {

    public FlatSelectWidget() {

        // CSS class-name should not be v- prefixed
        setStyleName("flatselect");

        // State is set to widget in FlatSelectConnector
    }

}