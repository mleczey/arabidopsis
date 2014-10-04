package com.mleczey;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ComplexShapes extends Application {

  private static final int WIDTH = 300;
  private static final int HEIGHT = 250;

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Complex shapes");

    Group root = new Group();
    Scene scene = new Scene(root, WIDTH, HEIGHT);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

}
