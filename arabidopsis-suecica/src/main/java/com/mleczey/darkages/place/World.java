package com.mleczey.darkages.place;

import com.mleczey.darkages.person.Folk;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class World {

  private final Set<Kingdom> kingdoms;

  public World() {
    this.kingdoms = new HashSet<>(16);
  }

  public void addKingdom(Kingdom kingdom) {
    this.kingdoms.add(kingdom);
  }

  public void addKingdoms(Collection<Kingdom> kingdoms) {
    this.kingdoms.addAll(kingdoms);
  }

  public Stream<Kingdom> getKingdoms() {
    return this.kingdoms.stream();
  }

  public Stream<Kingdom> getOtherKingdoms(Kingdom kingdom) {
    return this.kingdoms.stream()
        .filter(k -> !kingdom.getName().equalsIgnoreCase(k.getName()));
  }

  public Optional<Kingdom> getKingdomByCapital(City capital) {
    return this.kingdoms.stream()
        .filter(kingdom -> capital.equals(kingdom.getCapital()))
        .findFirst();
  }

  public Stream<Folk> getFolks() {
    return this.kingdoms.stream().flatMap(Kingdom::getFolks);
  }
}
