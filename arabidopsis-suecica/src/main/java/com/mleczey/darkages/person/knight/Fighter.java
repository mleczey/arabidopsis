package com.mleczey.darkages.person.knight;

public interface Fighter {
  public void attack(Fighter knight);
  public void defend(int damage);
  public void updateHitPoints(int damage);
}
