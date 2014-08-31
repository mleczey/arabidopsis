package com.mleczey.darkages.place;

import lombok.Getter;

public enum City {

  CALISIA("Calisia"), 
  NOWHERE("Nowhere"), 
  UNKNOWN("unknown");

  @Getter
  private final String officalName;

  private City(String officalName) {
    this.officalName = officalName;
  }
}
