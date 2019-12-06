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


public class maininicial extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/vista/Part1MainPart.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Welcome ");
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest(e->{AccesoBD.getInstance().salvar();});
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
