/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        
        //Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        //Application.setUserAgentStylesheet(getClass().getResource("mainCSS.css").toExternalForm());	
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
