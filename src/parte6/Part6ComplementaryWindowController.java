/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte6;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.Asignatura;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class Part6ComplementaryWindowController implements Initializable {
    
    @FXML
    private TextField codigotexttf;
    @FXML
    private TextField descripciontexttf;
    @FXML
    private Text advisor;
    
    private boolean state;
    private Asignatura newAsignat;
    @FXML
    private Button botoncambios;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        
    }    
    
    @FXML
    private void savechanges(ActionEvent event) {
     
        if ("".equals(codigotexttf.getText())) {
            advisor.setText("Falta Código ");
        } 
         else {

            advisor.setText("");
            if(newAsignat == null){
            newAsignat = new Asignatura();
            newAsignat.setCodigo(codigotexttf.getText());
            newAsignat.setDescripcion(descripciontexttf.getText());
            
            } else {
                
            newAsignat.setCodigo(codigotexttf.getText());
            newAsignat.setDescripcion(descripciontexttf.getText());

            }
            state = true;
            ((Stage) codigotexttf.getScene().getWindow()).close();
        }
    }
    
    @FXML
    private void cancel(ActionEvent event) {
        ((Stage) codigotexttf.getScene().getWindow()).close();
        state = false;
    }
    
    public Asignatura getSubject() {
        return newAsignat;
    }
    
    public boolean getValue() {
        return state;
    }
    
    public void modifySubject(Asignatura a) {
        newAsignat = a;
        codigotexttf.setText(a.getCodigo());
        descripciontexttf.setText(a.getDescripcion());
        state = true;
    }
    
}
