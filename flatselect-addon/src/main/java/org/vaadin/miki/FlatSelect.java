package org.vaadin.miki;

import org.vaadin.miki.client.FlatSelectClientRpc;
import org.vaadin.miki.client.FlatSelectServerRpc;
import org.vaadin.miki.client.FlatSelectState;

import com.vaadin.shared.MouseEventDetails;

// This is the server-side UI component that provides public API 
// for FlatSelect
public class FlatSelect extends com.vaadin.ui.AbstractComponent {

    private int clickCount = 0;

    // To process events from the client, we implement ServerRpc
    private FlatSelectServerRpc rpc = new FlatSelectServerRpc() {

        // Event received from client - user clicked our widget
        public void clicked(MouseEventDetails mouseDetails) {
            
            // Send nag message every 5:th click with ClientRpc
            if (++clickCount % 5 == 0) {
                getRpcProxy(FlatSelectClientRpc.class)
                        .alert("Ok, that's enough!");
            }
            
            // Update shared state. This state update is automatically 
            // sent to the client. 
            getState().text = "You have clicked " + clickCount + " times";
        }
    };

    public FlatSelect() {

        // To receive events from the client, we register ServerRpc
        registerRpc(rpc);
    }

    // We must override getState() to cast the state to FlatSelectState
    @Override
    protected FlatSelectState getState() {
        return (FlatSelectState) super.getState();
    }
}
