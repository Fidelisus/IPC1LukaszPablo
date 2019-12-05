/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class Part5YesOrNoController implements Initializable {

    /**
     * Initializes the controller class.
     * 
     */
    private boolean res;
    @FXML
    private Text referencedtext;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void yesdelete(ActionEvent event) {
        res = true;
        ((Stage) referencedtext.getScene().getWindow()).close();
    }

    @FXML
    private void nodelete(ActionEvent event) {
        res = false;
        ((Stage) referencedtext.getScene().getWindow()).close();
    }
    
    public boolean getValue(){
    return res;
    }
}
