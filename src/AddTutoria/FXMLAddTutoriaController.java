/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddTutoria;

import AddAsignatura.Part6ComplementaryWindowController;
import accesoBD.AccesoBD;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Alumno;
import modelo.Asignatura;
import vista.Part5ComplementaryWindowController;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class FXMLAddTutoriaController implements Initializable {

    public ObservableList<Alumno> datosAlumnos = null;
    private ObservableList<Asignatura> datosAsignatura = null;

    @FXML
    private Button modificarAlumnos;
    @FXML
    private Button borrarAlumnos;
    @FXML
    private TableView<Alumno> alumnosTabla;
    @FXML
    private TextField textoAlumnos;
    @FXML
    private TextField textoAsignatura;
    @FXML
    private TableColumn<Alumno, String> nombreColumna;
    @FXML
    private TableColumn<Alumno, String> apellidosColumna;
    @FXML
    private TableColumn<Alumno, String> emailColumna;
    @FXML
    private Button modificarAsignatura;
    @FXML
    private Button borrarAsignatura;
    @FXML
    private TableView<Asignatura> asignaturasTabla;
    @FXML
    private TableColumn<Asignatura, String> codigoColumna;
    @FXML
    private TableColumn<Asignatura, String> descripcionColumna;
    @FXML
    private Spinner<LocalTime> horaseleccionar;
    @FXML
    private Spinner<LocalTime> minutosseleccionar;
    @FXML
    private Text tiemposlider;
    @FXML
    private Slider slider;

    /**
     * Initializes the controller class.
     */
    public void initializeModel() {
        datosAlumnos = AccesoBD.getInstance().getTutorias().getAlumnosTutorizados();
        datosAsignatura = AccesoBD.getInstance().getTutorias().getAsignaturas();

    }

    public void initialize(URL url, ResourceBundle rb) {

        initializeModel();
        alumnosTabla.setItems(datosAlumnos);
        asignaturasTabla.setItems(datosAsignatura);

        //LISTENERS
        textoAlumnos.disableProperty().bind(Bindings.size(datosAlumnos).isEqualTo(0));
        textoAsignatura.disableProperty().bind(textoAlumnos.disableProperty());
        horaseleccionar.disableProperty().bind(textoAsignatura.disableProperty());
        minutosseleccionar.disableProperty().bind(horaseleccionar.disableProperty());
        slider.disableProperty().bind(minutosseleccionar.disableProperty());
       
       tiemposlider.textProperty().bind(slider.valueProperty().asString("%.1f"));


        nombreColumna.setCellValueFactory(fila -> fila.getValue().nombreProperty());
        apellidosColumna.setCellValueFactory(fila -> fila.getValue().apellidosProperty());
        emailColumna.setCellValueFactory(fila -> fila.getValue().emailProperty());

        codigoColumna.setCellValueFactory(fila -> fila.getValue().codigoProperty());
        descripcionColumna.setCellValueFactory(fila -> fila.getValue().descripcionProperty());
    }

    @FXML
    private void alumnosAñadirAccion(ActionEvent event) throws IOException {

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
            datosAlumnos.add(student);
        }
        textoAlumnos.setText("");
        AccesoBD.getInstance().salvar();

    }

    @FXML
    private void modificarAlumnosAccion(ActionEvent event) throws IOException {

        if (!datosAlumnos.isEmpty() && alumnosTabla.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader miLoader = new FXMLLoader(getClass().getResource("/vista/Part5ComplementaryWindow.fxml"));
            Parent root = miLoader.load();

            Scene scene = new Scene(root);
            Stage ventana3 = new Stage();
            ventana3.setTitle("Modificar Alumno");
            ventana3.setScene(scene);
            ventana3.setResizable(false);
            ventana3.initModality(Modality.APPLICATION_MODAL);

            ((Part5ComplementaryWindowController) miLoader.getController()).modifyStudent(alumnosTabla.getSelectionModel().getSelectedItem());
            ventana3.showAndWait();
        }
        textoAlumnos.setText("");
        AccesoBD.getInstance().salvar();
    }

    @FXML
    private void borrarAlumnosAccion(ActionEvent event) {
        if (!datosAlumnos.isEmpty() && alumnosTabla.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("AVISO");
            alert.setHeaderText("Estás borrando un alumno...");
            alert.setContentText("¿Seguro que quieres continuar?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                datosAlumnos.remove(alumnosTabla.getSelectionModel().getSelectedItem());
            }
        }
        textoAlumnos.setText("");
        AccesoBD.getInstance().salvar();
    }

    @FXML
    private void alumnoSeleccionado(MouseEvent event) {
        if (!"".equals(alumnosTabla.getSelectionModel().getSelectedItem())) {
            textoAlumnos.setText(alumnosTabla.getSelectionModel().getSelectedItem().getNombre());
        } else {
            textoAlumnos.setText("");
        }

    }

    @FXML
    private void añadirAsignaturaAccion(ActionEvent event) throws IOException {
        FXMLLoader miLoader = new FXMLLoader(getClass().getResource("/AddAsignatura/Part6ComplementaryWindow.fxml"));
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
            datosAsignatura.add(subject);

        }
        textoAsignatura.setText("");
        AccesoBD.getInstance().salvar();
    }

    public boolean index() {
        boolean res = false;

        if (asignaturasTabla.getSelectionModel().getSelectedIndex() == 0) {
            res = true;
        }
        if (asignaturasTabla.getSelectionModel().getSelectedIndex() == 1) {
            res = true;
        }

        return res;
    }

    @FXML
    private void modificarAsignaturaAccion(ActionEvent event) throws IOException {
        if (asignaturasTabla.getSelectionModel().getSelectedItem() != null && index() == false) {
            FXMLLoader miLoader = new FXMLLoader(getClass().getResource("/AddAsignatura/Part6ComplementaryWindow.fxml"));
            Parent root = miLoader.load();

            Scene scene = new Scene(root);
            Stage ventana3 = new Stage();
            ventana3.setTitle("Modificar Asignatura");
            ventana3.setScene(scene);
            ventana3.setResizable(false);
            ventana3.initModality(Modality.APPLICATION_MODAL);
            ((Part6ComplementaryWindowController) miLoader.getController()).modifySubject(asignaturasTabla.getSelectionModel().getSelectedItem());
            ventana3.showAndWait();
            textoAsignatura.setText("");
            AccesoBD.getInstance().salvar();
        }
    }

    @FXML
    private void borrarAsignaturaAccion(ActionEvent event) {
        if (asignaturasTabla.getSelectionModel().getSelectedItem() != null && index() == false) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("AVISO");
            alert.setHeaderText("Estás borrando un alumno...");
            alert.setContentText("¿Seguro que quieres continuar?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                datosAsignatura.remove(asignaturasTabla.getSelectionModel().getSelectedItem());
            }
            textoAsignatura.setText("");
            AccesoBD.getInstance().salvar();

        }
    }

    @FXML
    private void asignaturaSeleccionada(MouseEvent event) {
        if (!"".equals(asignaturasTabla.getSelectionModel().getSelectedItem())) {
            textoAsignatura.setText(asignaturasTabla.getSelectionModel().getSelectedItem().getCodigo());
        } else {
            textoAsignatura.setText("");
        }
    }

}
