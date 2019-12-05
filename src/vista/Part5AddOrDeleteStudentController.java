/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;


import modelo.Alumno;
import accesoBD.AccesoBD;
import javafx.scene.control.Label;
import static javafx.scene.paint.Color.BLACK;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class Part5AddOrDeleteStudentController implements Initializable {

    private ObservableList<Alumno> datos = null;

    @FXML
    private Button addButton;
    @FXML
    private Button modButton;
    @FXML
    private Button delButton;
    @FXML
    private TableView<Alumno> studentsTable;
    @FXML
    private TableColumn<Alumno, String> nameColumn;
    @FXML
    private TableColumn<Alumno, String> surnameColumn;
    @FXML
    private TableColumn<Alumno, String> emailColumn;
    @FXML
    Label verificador;
 

 
    public void initializeModel() {
        datos = AccesoBD.getInstance().getTutorias().getAlumnosTutorizados();
        //datos = FXCollections.observableArrayList(myData);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        initializeModel();
        studentsTable.setItems(datos);
        
                // TODOdelButton.disableProperty().bind(Bindings.not(studentsTable   
 //       studentsTable.getSelectionModel().selectedItemProperty().addListener(
 //               (obs, oldSelection, newSelection) -> {
 //                   if (studentsTable.getSelectionModel().selectedItemProperty() == null) {
 //                       modButton.setDisable(false) ;
 //                   }
 //               }
 //       );
        modButton.disableProperty().bind(Bindings.not(studentsTable.focusedProperty()));
        delButton.disableProperty().bind(Bindings.not(studentsTable.focusedProperty()));
        modButton.disableProperty().bind(Bindings.size(datos).isEqualTo(0));
        delButton.disableProperty().bind(Bindings.size(datos).isEqualTo(0));
        
        nameColumn.setCellValueFactory(fila -> fila.getValue().nombreProperty());
        surnameColumn.setCellValueFactory(fila -> fila.getValue().apellidosProperty());
        emailColumn.setCellValueFactory(fila -> fila.getValue().emailProperty());

    }

    @FXML
    private void AddStudent(ActionEvent event) throws IOException {

        FXMLLoader miLoader = new FXMLLoader(getClass().getResource("/vista/Part5ComplementaryWindow.fxml"));
        Parent root = miLoader.load();

        Scene scene = new Scene(root);
        Stage ventana3 = new Stage();
        ventana3.setTitle("Añadir Alumno");
        ventana3.setScene(scene);
        ventana3.setResizable(false);
        ventana3.initModality(Modality.APPLICATION_MODAL);
        ventana3.showAndWait();

        Alumno student = ((Part5ComplementaryWindowController) miLoader.getController()).getStudent();
        if (student != null) {
            datos.add(student);
            verificador.textFillProperty().set(BLACK);
        verificador.setText("Alumno Añadido Correctamente");
        } else{
        verificador.setText("");
        }

        

        AccesoBD.getInstance().salvar();
    }

    @FXML
    private void modifyStudent(ActionEvent event) throws IOException {
        if (!datos.isEmpty() && studentsTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader miLoader = new FXMLLoader(getClass().getResource("/vista/Part5ComplementaryWindow.fxml"));
            Parent root = miLoader.load();

            Scene scene = new Scene(root);
            Stage ventana3 = new Stage();
            ventana3.setTitle("Modificar Alumno");
            ventana3.setScene(scene);
            ventana3.setResizable(false);
            ventana3.initModality(Modality.APPLICATION_MODAL);

            ((Part5ComplementaryWindowController) miLoader.getController()).modifyStudent(studentsTable.getSelectionModel().getSelectedItem());
            //((FXMLPersonaController) miLoader.getController()).setPersonaModificar(personasTable.getSelectionModel().getSelectedItem());

            ventana3.showAndWait(); 
            if(((Part5ComplementaryWindowController) miLoader.getController()).getValue() == true){
                verificador.textFillProperty().set(BLACK);
                verificador.setText("Alumno Modificado Correctamente");
            } else {
                verificador.setText("");
            }
            AccesoBD.getInstance().salvar();
        }
    }

    @FXML
    private void deleteStudent(ActionEvent event) throws IOException {

        if (!datos.isEmpty() && studentsTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader miLoader = new FXMLLoader(getClass().getResource("/vista/Part5YesOrNo.fxml"));
            Parent root = miLoader.load();

            Scene scene = new Scene(root);
            Stage ventana4 = new Stage();
            ventana4.setTitle("");
            ventana4.setScene(scene);
            ventana4.setResizable(false);
            ventana4.initModality(Modality.APPLICATION_MODAL);
            ventana4.showAndWait();

            if (((Part5YesOrNoController) miLoader.getController()).getValue()) {
                datos.remove(studentsTable.getSelectionModel().getSelectedItem());
                verificador.textFillProperty().set(BLACK);
                verificador.setText("Alumno Borrado Correctamente");
                AccesoBD.getInstance().salvar();
            } else {
                verificador.setText("");
            }

        }
    }
}

  
    