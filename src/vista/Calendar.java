/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.time.DayOfWeek;
import java.time.LocalDate;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author jsoler
 */

    
    public class Calendar {

    Calendar(BorderPane root) {
        try {
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            DatePicker datePicker = new DatePicker(LocalDate.now());
            datePicker.setShowWeekNumbers(false);
            datePicker.setDayCellFactory(cel-> new DiaCelda());
            /*
            datePicker.setStyle("-fx-font: 45px \"Arial\";");
            
            //datePicker.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            datePicker.setPrefHeight(10);
            datePicker.setMaxHeight(10);
            datePicker.setMinHeight(10);
            */
            DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
            Node popupContent = datePickerSkin.getPopupContent();
            //popupContent.set  .setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            root.setCenter(popupContent);
       //     datePicker.valueProperty().addListener(listener);
       
       
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        class DiaCelda extends DateCell {
            
             String newline = System.getProperty("line.separator");
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                setStyle("-fx-font: 20px \"Arial\";");
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
    
   
    

