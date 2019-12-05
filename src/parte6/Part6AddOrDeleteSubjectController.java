/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parte6;

import accesoBD.AccesoBD;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import static javafx.scene.paint.Color.BLACK;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Asignatura;
import vista.Part5YesOrNoController;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class Part6AddOrDeleteSubjectController implements Initializable {

    private ObservableList<Asignatura> datosSubject = null;
    @FXML
    private Button addButton;
    @FXML
    private Button modButton;
    @FXML
    private Button delButton;
    @FXML
    private Label verificadorSub;
    @FXML
    private TableView<Asignatura> subjectsTable;
    @FXML
    private TableColumn<Asignatura, String> CodigoColumn;
    @FXML
    private TableColumn<Asignatura, String> descripcionColumna;

    private Asignatura tfg = new Asignatura("TFG", "Trabajo Fin de Grado");
    private Asignatura tfm = new Asignatura("TFM", "Trabajo Fin de Máster");

    // && subjectsTable.getSelectionModel().getSelectedItem().equals(tfg) && subjectsTable.getSelectionModel().getSelectedItem().equals(tfm)
    public void initializeModel() {
        datosSubject = AccesoBD.getInstance().getTutorias().getAsignaturas();

        if (datosSubject.isEmpty()) {
            datosSubject.add(tfg);
            datosSubject.add(tfm);
        }


        /*       boolean esta = false;
        for(Asignatura a : datosSubject){
            if(a.getCodigo() == "TFG") esta = true;
        }
        if(!esta) datosSubject.add(tfg);
        esta = false;
        
        for(Asignatura a : datosSubject){
            if(a.getCodigo() == "TFM") esta = true;
        }
        
        if(!esta) datosSubject.add(tfm);*/
        //if(datosSubject.){
        //datosSubject.add(tfg);
        //}
        //if(!datosSubject.contains(tfm)){
        //datosSubject.add(tfm);
        //}
        AccesoBD.getInstance().salvar();
    }

    //&& subjectsTable.getSelectionModel().getSelectedItem().equals(tfg) 
    public boolean index() {
        boolean res = false;

        if (subjectsTable.getSelectionModel().getSelectedIndex() == 0) {
            res = true;
        }
        if (subjectsTable.getSelectionModel().getSelectedIndex() == 1) {
            res = true;
        }

        return res;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initializeModel();
        subjectsTable.setItems(datosSubject);
        verificadorSub.textFillProperty().set(BLACK);

        modButton.disableProperty().bind(Bindings.not(subjectsTable.focusedProperty()));
        delButton.disableProperty().bind(Bindings.not(subjectsTable.focusedProperty()));
        modButton.disableProperty().bind(Bindings.size(datosSubject).isEqualTo(2));
        delButton.disableProperty().bind(Bindings.size(datosSubject).isEqualTo(2));

        CodigoColumn.setCellValueFactory(fila -> fila.getValue().codigoProperty());
        descripcionColumna.setCellValueFactory(fila -> fila.getValue().descripcionProperty());

    }

    @FXML
    private void AddSubject(ActionEvent event) throws IOException {
        FXMLLoader miLoader = new FXMLLoader(getClass().getResource("/parte6/Part6ComplementaryWindow.fxml"));
        Parent root = miLoader.load();

        Scene scene = new Scene(root);
        Stage ventana3 = new Stage();
        ventana3.setTitle("Añadir Asignatura");
        ventana3.setScene(scene);
        ventana3.setResizable(false);
        ventana3.initModality(Modality.APPLICATION_MODAL);
        ventana3.showAndWait();

        Asignatura subject = ((Part6ComplementaryWindowController) miLoader.getController()).getSubject();
        if (subject != null) {
            datosSubject.add(subject);
            verificadorSub.setText("Asignatura Añadida Correctamente");
        } else {
            verificadorSub.setText("");
        }

        AccesoBD.getInstance().salvar();
    }

    @FXML
    private void modifySubject(ActionEvent event) throws IOException {

        if (subjectsTable.getSelectionModel().getSelectedItem() != null && index() == false) {
            FXMLLoader miLoader = new FXMLLoader(getClass().getResource("/parte6/Part6ComplementaryWindow.fxml"));
            Parent root = miLoader.load();

            Scene scene = new Scene(root);
            Stage ventana3 = new Stage();
            ventana3.setTitle("Modificar Asignatura");
            ventana3.setScene(scene);
            ventana3.setResizable(false);
            ventana3.initModality(Modality.APPLICATION_MODAL);
            ((Part6ComplementaryWindowController) miLoader.getController()).modifySubject(subjectsTable.getSelectionModel().getSelectedItem());
            ventana3.showAndWait();

            if (((Part6ComplementaryWindowController) miLoader.getController()).getValue() == true) {
                verificadorSub.setText("Asignatura Modificada Correctamente");
            } else {
                verificadorSub.setText("");
            }
            AccesoBD.getInstance().salvar();
        }
    }

    @FXML
    private void deleteSubject(ActionEvent event) throws IOException {
        if (subjectsTable.getSelectionModel().getSelectedItem() != null && index() == false) {
            FXMLLoader miLoader = new FXMLLoader(getClass().getResource("/vista/Part5YesOrNo.fxml")); // WE ARE GOING TO USE THE SAME WINDOW FOR BOTH (ALUMNO y ASIGNATURA)
            Parent root = miLoader.load();

            Scene scene = new Scene(root);
            Stage ventana4 = new Stage();
            ventana4.setTitle("");
            ventana4.setScene(scene);
            ventana4.setResizable(false);
            ventana4.initModality(Modality.APPLICATION_MODAL);
            ventana4.showAndWait();

            if (((Part5YesOrNoController) miLoader.getController()).getValue()) {
                datosSubject.remove(subjectsTable.getSelectionModel().getSelectedItem());
                verificadorSub.setText("Asignatura Borrada Correctamente");
                AccesoBD.getInstance().salvar();
            } else {
                verificadorSub.setText("");
            }

        }
    }

}
