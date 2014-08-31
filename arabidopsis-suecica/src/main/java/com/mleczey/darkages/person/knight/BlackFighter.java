package com.mleczey.darkages.person.knight;

public interface BlackFighter extends Fighter {
  public static final int ATTACK = 5;
  public static final int DEFENCE = 2;
  
  
  @Override
  public default void attack(Fighter knight) {
    knight.defend(ATTACK);
  }

  @Override
  public default void defend(int damage) {
    this.updateHitPoints(damage - Math.max(0, damage - DEFENCE));
  }
}
