/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datepic;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.time.DayOfWeek;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author jsoler
 */

    
    public class Datepic extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 400, 400);
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            DatePicker datePicker = new DatePicker(LocalDate.now());
            datePicker.setShowWeekNumbers(false);
            datePicker.setDayCellFactory(cel-> new DiaCelda());

            DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
            Node popupContent = datePickerSkin.getPopupContent();

            root.setCenter(popupContent);
       //     datePicker.valueProperty().addListener(listener);
                    

            
            
            
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public static void main(String[] args) {
            launch(args);
        }

        class DiaCelda extends DateCell {
            
             String newline = System.getProperty("line.separator");
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
 
                // Show Weekends in blue color
                DayOfWeek day = DayOfWeek.from(item);
                if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                    this.setTextFill(Color.ROSYBROWN);
                    this.setDisable(true);
                  
                 
                 
                 
                 this.setText(this.getText()+"\r");
                }
                else this.setText(this.getText()+"\rlibre");
            }

        
    }
}
    
   
    

