package org.vaadin.miki.client;

import org.vaadin.miki.FlatSelect;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;

// Connector binds client-side widget class to server-side component class
// Connector lives in the client and the @Connect annotation specifies the
// corresponding server-side component
@Connect(FlatSelect.class)
public class FlatSelectConnector extends AbstractComponentConnector {

    // ServerRpc is used to send events to server. Communication implementation
    // is automatically created here
    FlatSelectServerRpc rpc = RpcProxy.create(FlatSelectServerRpc.class, this);

    public FlatSelectConnector() {
        
        // To receive RPC events from server, we register ClientRpc implementation 
        registerRpc(FlatSelectClientRpc.class, new FlatSelectClientRpc() {
            public void alert(String message) {
                Window.alert(message);
            }
        });

        // We choose listed for mouse clicks for the widget
        getWidget().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                final MouseEventDetails mouseDetails = MouseEventDetailsBuilder
                        .buildMouseEventDetails(event.getNativeEvent(),
                                getWidget().getElement());
                
                // When the widget is clicked, the event is sent to server with ServerRpc
                rpc.clicked(mouseDetails);
            }
        });

    }

    // We must implement getWidget() to cast to correct type 
    // (this will automatically create the correct widget type)
    @Override
    public FlatSelectWidget getWidget() {
        return (FlatSelectWidget) super.getWidget();
    }

    // We must implement getState() to cast to correct type
    @Override
    public FlatSelectState getState() {
        return (FlatSelectState) super.getState();
    }

    // Whenever the state changes in the server-side, this method is called
    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        // State is directly readable in the client after it is set in server
        final String text = getState().text;
        getWidget().setText(text);
    }
}
