/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import AddTutoria.FXMLAddTutoriaController;
import accesoBD.AccesoBD;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Alumno;
import modelo.Asignatura;
import modelo.Tutoria;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class Part1MainPartController implements Initializable {

    private ObservableList<Tutoria> datos = null;
    @FXML
    private BorderPane caledarioPane;
    @FXML
    private Button anadirTutoriaBoton;
    @FXML
    private TableView<Tutoria> tabelaTutorias;
    @FXML
    private TableColumn<Tutoria, String> nombreColumn;
    @FXML
    private TableColumn<Tutoria, String> duracionColumn;
    @FXML
    private TableColumn<Tutoria, String> estadoColumn;

    DatePicker datePicker;
    private ObservableList<Tutoria> tutoriasDia;
    @FXML
    private Text fechaActual;
    @FXML
    private Button anular;
    @FXML
    private Button confirmar;
    @FXML
    private Button noAsistida;
    @FXML
    private Button anadirComentario;
    @FXML
    private Text seleccionadaNombre;
    @FXML
    private Text seleccionadaComentarios;
    @FXML
    private Text SeleccionadaAlumno;
    @FXML
    private Text seleccionadaDuracion;
    @FXML
    private Text seleccionadaEstado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datos = AccesoBD.getInstance().getTutorias().getTutoriasConcertadas();

        createCalendar();
        datePickerUpdate();
        setBotones();
        setFactories();
        visualizarTutoriasDelDia();
    }

private void setBotones() {
        seleccionadaNombre.setText(" ");
        seleccionadaDuracion.setText(" ");
        seleccionadaEstado.setText(" ");
        seleccionadaComentarios.setText(" ");
        SeleccionadaAlumno.setText(" ");

        datePicker.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            datePickerUpdate();
        });

        tabelaTutorias.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                noAsistida.setDisable(false);
                anular.setDisable(false);
                confirmar.setDisable(false);
                anadirComentario.setDisable(false);

                switch (newSelection.getEstado()) {
                    case NO_ASISTIDA:
                        noAsistida.setDisable(true);
                        break;
                    case ANULADA:
                        anular.setDisable(true);
                        break;
                    case REALIZADA:
                        confirmar.setDisable(true);
                        break;
                }

                seleccionadaNombre.setText(newSelection.getAsignatura().getCodigo() + " " + newSelection.getAsignatura().getDescripcion());
                seleccionadaDuracion.setText("Hora: " + newSelection.getInicio().toString() + " - " + newSelection.getInicio().plus(newSelection.getDuracion()).toString());
                seleccionadaEstado.setText("Estado: " + newSelection.getEstado());
                seleccionadaComentarios.setText(newSelection.getAnotaciones());

                String alumnos = "";
                for (Alumno alumno : newSelection.getAlumnos()) {
                    alumnos += alumno.getNombre() + " " + alumno.getApellidos() + " " + alumno.getEmail();
                }
                SeleccionadaAlumno.setText(alumnos);
            } else {
                noAsistida.setDisable(true);
                anular.setDisable(true);
                confirmar.setDisable(true);
                anadirComentario.setDisable(true);
                seleccionadaNombre.setText(" ");
                seleccionadaDuracion.setText(" ");
                seleccionadaEstado.setText(" ");
                seleccionadaComentarios.setText(" ");
                SeleccionadaAlumno.setText(" ");
            }
        });
    }

    private void setFactories() {
        tabelaTutorias.setRowFactory(tv -> new TableRow<Tutoria>() {
            @Override
            public void updateItem(Tutoria item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null) {
                    setStyle("");
                } else {
                    switch (item.getEstado()) {
                        case NO_ASISTIDA:
                            setStyle("-fx-background-color: lightsteelblue;");
                            break;
                        case ANULADA:
                            setStyle("-fx-background-color: tomato;");
                            break;
                        case REALIZADA:
                            setStyle("-fx-background-color: aquamarine;");
                            break;
                        case PEDIDA:
                            setStyle("-fx-background-color: khaki;");
                            break;
                    }
                }
            }
        });

        nombreColumn.setCellValueFactory(fila -> {
            String value = fila.getValue().getAsignatura().getCodigo() + " " + fila.getValue().getAsignatura().getDescripcion();
            StringProperty str = new SimpleStringProperty(value);
            return str;
        });
        duracionColumn.setCellValueFactory(fila -> {
            String value = fila.getValue().getInicio().toString() + " - " + fila.getValue().getInicio().plus(fila.getValue().getDuracion()).toString();
            StringProperty str = new SimpleStringProperty(value);
            return str;
        });
        estadoColumn.setCellValueFactory(fila -> {
            String value = fila.getValue().getEstado().toString();
            StringProperty str = new SimpleStringProperty(value);
            switch (fila.getValue().getEstado()) {
                case NO_ASISTIDA:
                    noAsistida.setDisable(true);
                    break;
                case ANULADA:
                    anular.setDisable(true);
                    break;
                case REALIZADA:
                    confirmar.setDisable(true);
                    break;
            }
            return str;
        });
    }

    private void visualizarTutoriasDelDia() {
        datos = AccesoBD.getInstance().getTutorias().getTutoriasConcertadas();

        tutoriasDia = FXCollections.observableArrayList();

        for (Tutoria tutoria : datos) {
            if (tutoria.getFecha() != null && tutoria.getFecha().compareTo(datePicker.getValue()) == 0) {
                tutoriasDia.add(tutoria);
            }
        }
        tutoriasDia.sort(Comparator.comparing(Tutoria::getInicio));
        tabelaTutorias.setItems(tutoriasDia);
        tabelaTutorias.refresh();
        fechaActual.setText(datePicker.getValue().toString());
    }

    @FXML
    private void anular(ActionEvent event) {
        if (!datos.isEmpty() && tabelaTutorias.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("AVISO");
            alert.setHeaderText("Estás anulando una tutoria...");
            alert.setContentText("¿Seguro que quieres continuar?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                for (Tutoria tutoria : datos) {
                    if (tabelaTutorias.getSelectionModel().getSelectedItem().getFecha() == tutoria.getFecha()
                            && tabelaTutorias.getSelectionModel().getSelectedItem().getInicio() == tutoria.getInicio()) {
                        tutoria.setEstado(Tutoria.EstadoTutoria.ANULADA);
                    }
                }
            }
        }
        AccesoBD.getInstance().salvar();
        LocalDate t = datePicker.getValue();
        createCalendar();
        datePicker.setValue(t);
        datePickerUpdate();
        visualizarTutoriasDelDia();
    }

    @FXML
    private void confirmar(ActionEvent event) {
        if (!datos.isEmpty() && tabelaTutorias.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("AVISO");
            alert.setHeaderText("Estás confirmando una tutoria...");
            alert.setContentText("¿Seguro que quieres continuar?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                for (Tutoria tutoria : datos) {
                    if (tabelaTutorias.getSelectionModel().getSelectedItem().getFecha() == tutoria.getFecha()
                            && tabelaTutorias.getSelectionModel().getSelectedItem().getInicio() == tutoria.getInicio()) {
                        tutoria.setEstado(Tutoria.EstadoTutoria.REALIZADA);
                    }
                }
            }
        }
        AccesoBD.getInstance().salvar();
        visualizarTutoriasDelDia();
    }

    @FXML
    private void noAsistida(ActionEvent event) {
        if (!datos.isEmpty() && tabelaTutorias.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("AVISO");
            alert.setHeaderText("Estás marcando una tutoria como no asistida...");
            alert.setContentText("¿Seguro que quieres continuar?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                for (Tutoria tutoria : datos) {
                    if (tabelaTutorias.getSelectionModel().getSelectedItem().getFecha() == tutoria.getFecha()
                            && tabelaTutorias.getSelectionModel().getSelectedItem().getInicio() == tutoria.getInicio()) {
                        tutoria.setEstado(Tutoria.EstadoTutoria.NO_ASISTIDA);
                    }
                }
            }
        }
        AccesoBD.getInstance().salvar();
        visualizarTutoriasDelDia();
    }

    @FXML
    private void anadirComentario(ActionEvent event) throws IOException {
        if (!datos.isEmpty() && tabelaTutorias.getSelectionModel().getSelectedItem() != null) {
            for (Tutoria tutoria : datos) {
                if (tabelaTutorias.getSelectionModel().getSelectedItem().getFecha() == tutoria.getFecha()
                        && tabelaTutorias.getSelectionModel().getSelectedItem().getInicio() == tutoria.getInicio()) {
                    TextInputDialog dialog = new TextInputDialog("Anadir anotacion");
                    dialog.setTitle("Anadir anotacion");
                    dialog.setHeaderText("Introduce anotacion:");
                    dialog.setContentText("");
                    Optional<String> result = dialog.showAndWait();	
                    if (result.isPresent()) {
                        tabelaTutorias.getSelectionModel().getSelectedItem().setAnotaciones(result.get());
                    }
                }
            }
            AccesoBD.getInstance().salvar();
            visualizarTutoriasDelDia();
        }
    }

    private void createCalendar() {
        try {
            datePicker = new DatePicker(LocalDate.now());
            datePicker.setShowWeekNumbers(false);
            datePicker.setDayCellFactory(cel -> new DiaCelda());

            DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
            Node popupContent = datePickerSkin.getPopupContent();
            caledarioPane.setCenter(popupContent);
            datePicker.valueProperty().addListener((a, b, c) -> visualizarTutoriasDelDia());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void datePickerUpdate() {
        if (esDiaLibre(datePicker.getValue().atStartOfDay())) {
            anadirTutoriaBoton.setDisable(false);
        } else {
            anadirTutoriaBoton.setDisable(true);
        }
        DayOfWeek day = DayOfWeek.from(datePicker.getValue());
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            anadirTutoriaBoton.setDisable(true);
        }
    }

    class DiaCelda extends DateCell {

        String newline = System.getProperty("line.separator");

        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            DayOfWeek day = DayOfWeek.from(item);
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                this.setTextFill(Color.ROSYBROWN);
                this.setDisable(true);

                this.setText(this.getText() + "\r");
            } else {
                if (esDiaLibre(item.atStartOfDay())) {
                    this.setText(this.getText() + "\rlibre");
                } else {
                    this.setText(this.getText() + "\rocupado");
                }
            }
        }
    }

    private boolean esDiaLibre(LocalDateTime day) {
        LocalTime t = LocalTime.of(0, 0);
        for (Tutoria tutoria : datos) {
            if (tutoria.getFecha() != null && tutoria.getFecha().compareTo(day.toLocalDate()) == 0 && tutoria.getEstado() != Tutoria.EstadoTutoria.ANULADA) {
                t = t.plus(tutoria.getDuracion());
            }
        }
        return t.isBefore(LocalTime.of(12, 0));
    }

    @FXML
    private void anadirTutoria(ActionEvent event) throws IOException {
        FXMLLoader miLoader = new FXMLLoader(getClass().getResource("/AddTutoria/FXMLAddTutoria.fxml"));
        Parent root = miLoader.load();
        Scene scene = new Scene(root);
        Stage ventana2 = new Stage();
        ventana2.setTitle("Anadir Asignatura");
        ventana2.setScene(scene);
        ventana2.initModality(Modality.APPLICATION_MODAL);
        ((FXMLAddTutoriaController) miLoader.getController()).setFechaSeleccionada(datePicker.getValue());
        ventana2.showAndWait();
        visualizarTutoriasDelDia();
        tabelaTutorias.refresh();
    }
}