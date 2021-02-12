package com.citybank;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/* TODO: https://www.jfx-ensemble.com/?page=sample/jfoenix/Button */

public class Main extends Application {

    private Integer count = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Button btn1 = new Button("Click me");
        btn1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                count++;
                System.out.println("Clicked " + count + " times.");
            }
        });

        Text sampleText = new Text("Text");
        sampleText.setFont(new Font(45));
        sampleText.setX(100);
        sampleText.setY(250);
        sampleText.setFill(Color.WHITE);


        Group root = new Group();
        root.getChildren().addAll(btn1, sampleText);
        Scene scene=new Scene(root,600,400);
        scene.setFill(Color.DARKGRAY);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
