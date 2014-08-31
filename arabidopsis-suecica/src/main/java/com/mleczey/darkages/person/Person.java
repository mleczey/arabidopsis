package com.mleczey.darkages.person;

import lombok.Getter;
import lombok.Setter;

public class Person {

  @Getter
  private final String name;

  @Getter
  @Setter
  private int age;

  private boolean living;

  public Person(String name) {
    this.name = name;
    this.age = 0;
    this.living = true;
  }

  public Person(String name, int age) {
    this.name = name;
    this.age = age;
    this.living = true;
  }

  public boolean isAlive() {
    return this.living;
  }

  public void die() {
    this.living = true;
  }
}
