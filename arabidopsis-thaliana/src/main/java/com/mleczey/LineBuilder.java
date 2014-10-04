package com.mleczey;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class LineBuilder {

  private double x1;
  private double y1;
  private double x2;
  private double y2;
  private Paint paint;
  private double strokeWidth;
  private StrokeLineCap strokeLineCap;
  private double strokeDashOffset;

  public LineBuilder() {
    this.x1 = 0.0d;
    this.y1 = 0.0d;
    this.x2 = 1.0d;
    this.y2 = 1.0d;
    this.strokeWidth = 1.0d;
    this.strokeDashOffset = 0.0f;
  }

  public LineBuilder x1(double x1) {
    this.x1 = x1;
    return this;
  }

  public LineBuilder y1(double y1) {
    this.y1 = y1;
    return this;
  }

  public LineBuilder x2(double x2) {
    this.x2 = x2;
    return this;
  }

  public LineBuilder y2(double y2) {
    this.y2 = y2;
    return this;
  }

  public LineBuilder paint(Paint paint) {
    this.paint = paint;
    return this;
  }

  public LineBuilder strokeWidth(double strokeWidth) {
    this.strokeWidth = strokeWidth;
    return this;
  }

  public LineBuilder strokeLineCap(StrokeLineCap strokeLineCap) {
    this.strokeLineCap = strokeLineCap;
    return this;
  }

  public LineBuilder strokeDashOffset(double strokeDashOffset) {
    this.strokeDashOffset = strokeDashOffset;
    return this;
  }

  public Line build() {
    Line line = new Line(this.x1, this.y1, this.x2, this.y2);
    if (null != this.paint) {
      line.setStroke(this.paint);
    }
    line.setStrokeWidth(this.strokeWidth);
    if (null != this.strokeLineCap) {
      line.setStrokeLineCap(this.strokeLineCap);
    }
    line.setStrokeDashOffset(this.strokeDashOffset);
    return line;
  }
}
