package com.mleczey.darkages.person.knight;

import com.mleczey.darkages.person.King;
import com.mleczey.darkages.person.Person;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Knight extends Person implements Fighter {

  private static final int KNIGHT_MIN_HIT_POINTS = 10;
  private static final int KNIGHT_MAX_HIT_POINTS = 20;

  public static final String BLACK = "Black";
  public static final String RED = "Red";
  public static final String WHITE = "White";
  
  private int hitPoints;
  
  private Optional<King> king;

  public Knight(String name) {
    super(name);

    this.hitPoints = ThreadLocalRandom.current().nextInt(KNIGHT_MIN_HIT_POINTS, KNIGHT_MAX_HIT_POINTS);
    this.king = Optional.empty();
  }

  public boolean servs(King king) {
    return this.king
        .map(knightKing -> knightKing.equals(king))
        .orElse(Boolean.FALSE);
  }
  
  
  public void serve(King king) {
    this.king = Optional.ofNullable(king);
  }
  

  @Override
  public void updateHitPoints(int damage) {
    if (this.isAlive()) {
      this.hitPoints = Math.max(0, this.hitPoints - damage);
      if (0 == this.hitPoints) {
        this.die();
      }
    }
  }
}
