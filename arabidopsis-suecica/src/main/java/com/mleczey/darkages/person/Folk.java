package com.mleczey.darkages.person;

import com.mleczey.darkages.place.City;
import lombok.Getter;

public class Folk extends Person {

  public static Folk identify(String name, City placeOfBirth, int age) {
    Folk folk = new Folk(name, placeOfBirth);
    folk.setAge(age);
    return folk;
  }
  
  @Getter
  private final City placeOfBirth;

  public Folk(String name, City placeOfBirth) {
    super(name);
    this.placeOfBirth = placeOfBirth;
  }
}
