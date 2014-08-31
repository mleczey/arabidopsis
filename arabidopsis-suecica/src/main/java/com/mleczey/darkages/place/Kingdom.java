package com.mleczey.darkages.place;

import com.mleczey.darkages.person.Folk;
import com.mleczey.darkages.person.King;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;

public class Kingdom {

  public static final String GREAT_KINGDOM = "Great Kingdom";
  public static final String NOWHERE = "Nowhere";
  public static final String UNKNOWN = "Unknown";

  private static final Kingdom UNKNOWN_KINGDOM = new Kingdom(Kingdom.UNKNOWN, City.UNKNOWN, null);

  public static Kingdom unknownKingdom() {
    return UNKNOWN_KINGDOM;
  }

  @Getter
  private final String name;
  @Getter
  private final City capital;
  @Getter
  private final Optional<King> king;
  private final List<Folk> folks;

  public Kingdom(String name, City capital, King king) {
    this.name = name;
    this.capital = capital;
    this.king = Optional.ofNullable(king);
    this.folks = new ArrayList<>(16);
    
    this.king.ifPresent(k -> k.setKingdom(this));
  }

  public Stream<Folk> getFolks() {
    return this.folks.stream();
  }

  public void addFolk(Folk folk) {
    this.folks.add(folk);
  }

  public void addFolks(Folk... folks) {
    this.folks.addAll(Arrays.asList(folks));
  }

  public void addFolks(Collection<Folk> folks) {
    this.folks.addAll(folks);
  }
}
