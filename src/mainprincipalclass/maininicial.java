package mainprincipalclass;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import accesoBD.AccesoBD;
import com.sun.javafx.css.StyleManager;


public class maininicial extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/vista/Part1MainPart.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Manejador de tutorias");
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest(e->{AccesoBD.getInstance().salvar();});
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
