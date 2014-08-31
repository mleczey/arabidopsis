package com.mleczey.darkages;

public class NameGlue {

  protected final String begin;
  protected final String glue;
  private final String end;
  protected final StringBuilder names;

  public NameGlue(String begin, String glue, String end) {
    this.begin = begin;
    this.glue = glue;
    this.end = end;
    this.names = new StringBuilder("");
  }

  public StringBuilder getNames() {
    return this.names;
  }

  public NameGlue glue(String name) {
    if (0 == this.names.length()) {
      this.names.append(this.begin);
    } else {
      this.names.append(this.glue);
    }
    this.names.append(name);
    return this;
  }

  public NameGlue refill(NameGlue nameGlue) {
    this.names.append(nameGlue.getNames());
    return this;
  }

  @Override
  public String toString() {
    return this.names.toString() + this.end;
  }

}
