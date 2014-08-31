package com.mleczey.darkages.person;

import com.mleczey.darkages.person.knight.Knight;
import com.mleczey.darkages.place.Kingdom;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import lombok.Getter;

public class King extends Person {

  public static final String JOHN = "John";
  public static final String PATRIC = "Patric";

  @Getter
  private Optional<Kingdom> kingdom;
  private final Set<Knight> knights;

  public King(String name) {
    super(name);
    this.kingdom = Optional.empty();
    this.knights = new HashSet<>(16);
  }

  public void setKingdom(Kingdom kingdom) {
    this.kingdom = Optional.ofNullable(kingdom);
  }

  public void addKnight(Knight knight) {
    this.knights.add(knight);
    if (!knight.servs(this)) {
      knight.serve(this);
    }
  }

  public Stream<Knight> getKnights() {
    return this.knights.stream();
  }
  
  public Stream<Folk> getFolks() {
    return this.kingdom
        .map(k -> k.getFolks())
        .orElse(Stream.empty());
  }
}
