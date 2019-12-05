

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import accesoBD.AccesoBD;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Alumno;
import modelo.Asignatura;
import modelo.Tutoria;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class Part1MainPartController implements Initializable {

    @FXML
    private Button initialbutton;
    @FXML
    private TextArea fechaSeleccionada;
    @FXML
    private ScrollPane tutoriasDelDiaScrollPane;
    @FXML
    private VBox tutoriasDelDiaBox;

    
    private ObservableList<Tutoria> datos = null;
    @FXML
    private VBox calendarioBox;
    @FXML
    private BorderPane caledarioPane;
    @FXML
    private Button asignaturasBoton;
    @FXML
    private Button anadirTutoriaBoton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Calendar calendar = new Calendar(caledarioPane);
        //calendarioBox.getChildren().addAll(split);
        visualizarTutoriasDelDia();
    }

    @FXML
    private void scenep5(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/Part5AddOrDeleteStudent.fxml"));
        Scene scene = new Scene(root);
        Stage ventana2 = new Stage();
        ventana2.setTitle("Add/Modify/Delete Student");
        ventana2.setScene(scene);
        ventana2.initModality(Modality.APPLICATION_MODAL);
        ventana2.showAndWait();
        
        
    }

    private void visualizarTutoriasDelDia() {
        datos = AccesoBD.getInstance().getTutorias().getTutoriasConcertadas();
        datos.clear();
        Tutoria tu = new Tutoria();
        tu.setAsignatura(new Asignatura());
        tu.getAsignatura().setCodigo("COD");
        tu.getAsignatura().setDescripcion("descripcion");
        LocalTime t = LocalTime.now(); 
        tu.setInicio(t);
        tu.setEstado(Tutoria.EstadoTutoria.PEDIDA);
        datos.add(tu);
        
        Tutoria tu1 = new Tutoria();
        tu1.setAsignatura(new Asignatura());
        tu1.getAsignatura().setCodigo("AAA");
        tu1.getAsignatura().setDescripcion("asig");
        t = LocalTime.of(11, 30); 
        tu1.setInicio(t);
        tu1.setEstado(Tutoria.EstadoTutoria.ANULADA);
        datos.add(tu1);
        
        AccesoBD.getInstance().salvar();
        
        if(!datos.isEmpty()){
            tutoriasDelDiaBox.setSpacing(10);
            SplitPane split;
            
            for(Tutoria tutoria : datos){
                split = new SplitPane();
                split.setOrientation(Orientation.VERTICAL);
                split.setStyle("-fx-background-color: cadetblue");
                TextField datos = new TextField (tutoria.getAsignatura().getCodigo() + " " + tutoria.getAsignatura().getDescripcion());
            //LocalTime tt = LocalTime.now(); 
        //tutoria.setInicio(tt);
                TextField tiempo = new TextField(tutoria.getInicio().toString());
                TextField estado = new TextField("Estado: " + tutoria.getEstado());
                /*
                switch(tutoria.getEstado()){
                    case Tutoria.EstadoTutoria.ANULADA:
                        split.setStyle("-fx-background-color: cadetblue");
                        break;
                }*/
                
                split.getItems().addAll(datos, tiempo, estado);
                tutoriasDelDiaBox.getChildren().addAll(split);
        }
        
        tutoriasDelDiaScrollPane.setContent(tutoriasDelDiaBox);
        tutoriasDelDiaScrollPane.setPannable(true);
        }
    }
    
    @FXML
    private void scenep6(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/parte6/Part6AddOrDeleteSubject.fxml"));
        Scene scene = new Scene(root);
        Stage ventana2 = new Stage();
        ventana2.setTitle("Gestionamiento de Asignaturas");
        ventana2.setScene(scene);
        ventana2.initModality(Modality.APPLICATION_MODAL);
        ventana2.showAndWait();
    }
    
        @FXML
    private void anadirTutoria(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/Part2SeleccionarTutoria.fxml"));
        Scene scene = new Scene(root);
        Stage ventana2 = new Stage();
        ventana2.setTitle("Anadir Asignatura");
        ventana2.setScene(scene);
        ventana2.initModality(Modality.APPLICATION_MODAL);
        ventana2.showAndWait();
    }
}

