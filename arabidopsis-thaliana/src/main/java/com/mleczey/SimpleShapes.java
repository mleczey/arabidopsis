package com.mleczey;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SimpleShapes extends Application {

  private static final int WIDTH = 300;
  private static final int HEIGHT = 250;
  private static final double OFFSET = 10.0d;

  private static final String MESSAGE = "Stroke dash offset %.2f";

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Simple shapes");

    Group root = new Group();
    Scene scene = new Scene(root, WIDTH, HEIGHT);

    Text text = new Text(String.format(MESSAGE, 0.0d));
    text.setX(OFFSET);
    text.setY(7 * OFFSET);
    text.setStroke(Color.BLACK);
    root.getChildren().add(text);

    Slider slider = new Slider(0.0d, 10.0d, 0.0d);
    slider.setLayoutX(OFFSET);
    slider.setLayoutY(9 * OFFSET);
    slider.valueProperty().addListener((observable, oldValue, newValue) -> text.setText(String.format(MESSAGE, newValue)));
    root.getChildren().add(slider);

    root.getChildren().add(getRedLine(slider));
    root.getChildren().add(getGreenLine(slider));
    root.getChildren().add(getBlueLine(slider));

    root.getChildren().add(getRectangle(slider));

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private Line getRedLine(Slider slider) {
    Line line = new LineBuilder()
        .x1(OFFSET).y1(OFFSET).x2(WIDTH - OFFSET).y2(OFFSET)
        .paint(Color.RED)
        .strokeWidth(5.0d)
        .strokeLineCap(StrokeLineCap.BUTT)
        .strokeDashOffset(0.0d)
        .build();

    line.getStrokeDashArray().addAll(10.0d, 5.0d, 15.0d, 5.0d, 20.0d);
    line.strokeDashOffsetProperty().bind(slider.valueProperty());

    return line;
  }

  private Line getGreenLine(Slider slider) {
    Line line = new LineBuilder()
        .x1(OFFSET).y1(0).x2(WIDTH - OFFSET).y2(0)
        .paint(Color.GREENYELLOW)
        .strokeLineCap(StrokeLineCap.ROUND)
        .build();

    line.strokeWidthProperty().bind(slider.valueProperty().add(1.0d));
    line.layoutYProperty().bind(slider.valueProperty().add(2 * OFFSET));

    return line;
  }

  private Line getBlueLine(Slider slider) {
    Line line = new LineBuilder()
        .x1(OFFSET).y1(0).x2(WIDTH - OFFSET).y2(0)
        .paint(Color.AQUAMARINE)
        .build();

    line.strokeWidthProperty().bind(slider.valueProperty().add(1.0d));
    line.layoutYProperty().bind(slider.valueProperty().add(3 * OFFSET));

    return line;
  }

  private Rectangle getRectangle(Slider slider) {
    Rectangle rectangle = new Rectangle();
    rectangle.setX(OFFSET);
    rectangle.setY(11 * OFFSET);
    rectangle.setWidth(WIDTH - 2 * OFFSET);
    rectangle.setHeight(10 * OFFSET);
    rectangle.setArcWidth(5.0d);
    rectangle.setArcHeight(5.0d);
    rectangle.setFill(Color.WHITE);
    rectangle.setStroke(Color.BLACK);
    rectangle.setStrokeWidth(5.0d);

    rectangle.arcWidthProperty().bind(slider.valueProperty());
    rectangle.arcHeightProperty().bind(slider.valueProperty());

    return rectangle;
  }
}
