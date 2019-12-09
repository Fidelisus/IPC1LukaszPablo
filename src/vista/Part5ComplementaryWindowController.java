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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.Alumno;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class Part5ComplementaryWindowController implements Initializable {

    @FXML
    private TextField nametextf;
    @FXML
    private TextField surnametextf;
    @FXML
    private TextField emailtextf;

    private Alumno newAlumn;
    @FXML
    private Text advisor;

    private boolean state;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void savechanges(ActionEvent event) {
        if ("".equals(nametextf.getText())) {
            advisor.setText("Falta Nombre ");
        } else if ("".equals(surnametextf.getText())) {
            advisor.setText("Falta Apellido ");
        } else if ("".equals(emailtextf.getText())) {
            advisor.setText("Falta E-mail ");
        } else {

            advisor.setText("");
            if(newAlumn == null){
            newAlumn = new Alumno();
            newAlumn.setNombre(nametextf.getText());
            newAlumn.setApellidos(surnametextf.getText());
            newAlumn.setEmail(emailtextf.getText());
            
            } else {
                
            newAlumn.setApellidos(surnametextf.getText());
            newAlumn.setNombre(nametextf.getText());
            newAlumn.setEmail(emailtextf.getText());
            
            }
            state = true;
            ((Stage) nametextf.getScene().getWindow()).close();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        ((Stage) nametextf.getScene().getWindow()).close();
        state = false;

    }

    public Alumno getStudent() {
        return newAlumn;
    }
    
    public void modifyStudent (Alumno s){
    newAlumn = s;
    nametextf.setText(s.getNombre());
    surnametextf.setText(s.getApellidos());
    emailtextf.setText(s.getEmail());
    state = true;
    }
    
    public boolean getValue(){
    
    return state;
    }
}
