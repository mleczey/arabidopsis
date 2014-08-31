package com.mleczey.darkages.person.knight;

public interface RedFighter extends BlackFighter, WhiteFighter {

  @Override
  public default void attack(Fighter knight) {
    WhiteFighter.super.attack(knight);
  }

  @Override
  public default void defend(int damage) {
    BlackFighter.super.defend(damage);
  }
}
