package pl.dawidpalka;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.logging.SocketHandler;

public class FXMLController {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {

        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    public void initialize() {
        // TODO
    }

}
