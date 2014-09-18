package com.mleczey.darkages;

import static com.mleczey.darkages.KingdomsMatcher.hasOtherKingdoms;
import com.mleczey.darkages.person.Folk;
import com.mleczey.darkages.person.King;
import com.mleczey.darkages.person.knight.BlackKnight;
import com.mleczey.darkages.person.knight.Knight;
import com.mleczey.darkages.person.knight.WhiteKnight;
import com.mleczey.darkages.place.City;
import com.mleczey.darkages.place.Kingdom;
import com.mleczey.darkages.place.World;
import java.util.Comparator;
import static java.util.Comparator.comparing;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class DarkAgesTest {

  private World world;

  private King greatKingdomKing;
  private Kingdom greatKingdom;

  private King nowhereKindomKing;
  private Kingdom nowhereKingdom;
  private Folk theEldestFolk;
  private Folk theYoungestFolk;

  private Knight whiteKnight;
  private Knight blackKnight;

  private final Function<Folk, Character> getFolksNameFirstCharacter
      = folk -> folk.getName().toUpperCase().charAt(0);

  @Before
  public void setUp() {
    this.world = new World();
    this.setUpGreatKingdom();
    this.setUpNowhereKingdom();
    this.blackKnight = new BlackKnight(Knight.BLACK);
  }

  private void setUpGreatKingdom() {
    this.whiteKnight = new WhiteKnight(Knight.WHITE);

    this.greatKingdomKing = new King(King.JOHN);
    this.greatKingdomKing.addKnight(this.whiteKnight);

    this.greatKingdom = new Kingdom(Kingdom.GREAT_KINGDOM, City.CALISIA, this.greatKingdomKing);
    this.greatKingdom.addFolks(
        Folk.identify("Aaa", City.CALISIA, 20),
        Folk.identify("Bbb", City.CALISIA, 45),
        Folk.identify("Ccc", City.NOWHERE, 29));

    this.world.addKingdom(this.greatKingdom);
  }

  private void setUpNowhereKingdom() {
    this.nowhereKindomKing = new King(King.PATRIC);

    this.nowhereKingdom = new Kingdom(Kingdom.NOWHERE, City.NOWHERE, this.nowhereKindomKing);
    this.theYoungestFolk = Folk.identify("Dee", City.CALISIA, 4);
    this.theEldestFolk = Folk.identify("Abb", City.NOWHERE, 77);
    this.nowhereKingdom.addFolks(
        this.theYoungestFolk,
        this.theEldestFolk,
        Folk.identify("Bcc", City.NOWHERE, 54),
        Folk.identify("Cdd", City.NOWHERE, 33));

    this.world.addKingdom(this.nowhereKingdom);
  }

  @Test
  public void testIfGreatKingdomKingHasFolks() {
    assertNotNull(
        "king should have folks",
        this.greatKingdomKing.getFolks());
  }

  @Test
  public void testIfGreatKingdomKingDoesNotHandOverFolks() {
    assertEquals(
        "king should not hand over folks",
        3,
        this.greatKingdomKing.getFolks()
        .count());
  }

  @Test
  public void testIfGreatKingdomKingHasAtLeastTwoFolksFromCapital() {
    //given
    City capital = this.greatKingdomKing.getKingdom()
        .map(kingdom -> kingdom.getCapital())
        .orElse(City.UNKNOWN);
    //when
    long numberOfFolks = this.greatKingdomKing.getFolks()
        .filter(folk -> capital.equals(folk.getPlaceOfBirth()))
        .count();
    //then
    assertThat(
        "king should have folks where he rules",
        numberOfFolks,
        is(greaterThanOrEqualTo(2L)));
  }

  @Test
  public void testIfGreatKingdomKingHasFolksInAtLeastTwoKingdoms() {
    //given
    Kingdom greatKingdomKingKingdom = this.greatKingdomKing.getKingdom().orElse(Kingdom.unknownKingdom());
    //when
    Set<Kingdom> kingdoms = this.greatKingdomKing.getFolks()
        .map(folk -> this.world.getKingdomByCapital(folk.getPlaceOfBirth()))
        .map(kingdom -> kingdom.orElse(Kingdom.unknownKingdom()))
        .filter(kingdom -> !Objects.equals(kingdom, Kingdom.unknownKingdom()))
        .collect(toSet());
    //then
    assertThat(
        "great kingdom king does not rule over unknown kingdom",
        greatKingdomKingKingdom,
        is(not(equalTo(Kingdom.unknownKingdom())))
    );
    assertThat(
        "king should have spies in other kingdoms",
        kingdoms.size(),
        is(greaterThanOrEqualTo(2))
    );
    assertThat(
        "king should have spies in other kingdoms",
        kingdoms,
        both(hasItem(greatKingdomKingKingdom)).and(hasOtherKingdoms(greatKingdomKingKingdom))
    );
  }

  @Test
  public void testIfThereAreAtLeastSevenFolksInTheWorld() {
    //when
    List<Folk> folks = this.world.getFolks().collect(toList());
    //then
    assertThat(
        "world should be inhabited",
        folks.size(),
        is(greaterThanOrEqualTo(7)));
  }

  @Test
  public void testIfTheEldestFolkIsTheEldestInTheWorld() {
    //when
    Optional<Folk> folk = this.world.getFolks().max(Comparator.comparing(Folk::getAge));
    //then
    assertTrue(
        "the eldest folk exists",
        folk.isPresent());
    assertTrue(Objects.deepEquals(
        this.theEldestFolk,
        folk.get()));
  }

  @Test
  public void testIfTheYoungestFolkIsTheYoungestInTheWorld() {
    //when
    Optional<Folk> folk = this.world.getFolks().min(Comparator.comparing(Folk::getAge));
    //then
    assertTrue(
        "the youngest folk exists",
        folk.isPresent());
    assertTrue(
        Objects.deepEquals(this.theYoungestFolk,
            folk.get()));
  }

  @Test
  public void testIfWhiteKnightServsGreatKingdomKing() {
    //then
    assertTrue(
        "white knight should serve a king",
        this.whiteKnight.servs(this.greatKingdomKing));
  }

  @Test
  public void testIfBlackKnightServsNobody() {
    //then
    assertFalse("black knight should not serve a great kingdom king",
        this.blackKnight.servs(this.greatKingdomKing));
    assertFalse("black knight should not serve a nowhere king",
        this.blackKnight.servs(this.nowhereKindomKing));
  }

  @Test
  public void testIfThereAreFolksElderThan30InBothKingdoms() {
    //when
    Set<Kingdom> set = this.world.getFolks()
        .filter(folk -> 30 < folk.getAge())
        .map(folk -> this.world.getKingdomByCapital(folk.getPlaceOfBirth()))
        .map(kingdom -> kingdom.orElse(Kingdom.unknownKingdom()))
        .filter(kingdom -> !Objects.equals(kingdom, Kingdom.unknownKingdom()))
        .collect(toSet());
    //then
    assertEquals(
        "there should be old people in both kingdoms",
        2,
        set.size());
  }

  @Test
  public void testStatistics() {
    //when
    IntSummaryStatistics statistics = this.world.getFolks()
        .mapToInt(Folk::getAge)
        .summaryStatistics();
    //then
    assertEquals(7, statistics.getCount());
    assertEquals(77, statistics.getMax());
    assertEquals(4, statistics.getMin());
    assertEquals(37.42, statistics.getAverage(), 0.01);
    assertEquals(262L, statistics.getSum());
  }

  @Test
  public void testIfFolksAreSortedByTheirAge() {
    //when
    NavigableSet<Folk> sortedFolks = this.world.getFolks()
        .collect(Collectors.toCollection(() -> new TreeSet<>(comparing(Folk::getAge))));
    //then
    assertEquals(
        "first folk is the youngest",
        this.theYoungestFolk,
        sortedFolks.first());
    assertEquals(
        "last folks is the eldest",
        this.theEldestFolk,
        sortedFolks.last());
  }

  @Test
  public void testIfNowhereKingdomHasGreaterPopulation() {
    //given
    Function<Kingdom, Long> getPopulation = kingdom -> kingdom.getFolks().count();
    //when
    Optional<Kingdom> biggestKingdom = this.world.getKingdoms()
        .collect(maxBy(comparing(getPopulation)));
    //then
    assertEquals(
        "nowhere kingdom should have greater population",
        this.nowhereKingdom,
        biggestKingdom.get());
  }

  @Test
  public void testIfAverageKingdomPopulationIsThreeAndAHalf() {
    //given
    final double delta = 0.1;
    //when
    double averagePopulation = this.world.getKingdoms()
        .collect(averagingDouble(kingdom -> kingdom.getFolks().count()));
    //then
    assertEquals(
        "average population should be 3.5",
        3.5,
        averagePopulation,
        delta);
  }

  @Test
  public void testIfAtLeastTwoFolksFromGreatKingdomCanBecomeSoldiers() {
    //when
    Map<Boolean, List<Folk>> partitionedFolks = this.greatKingdom.getFolks()
        .collect(partitioningBy(folk -> 14 < folk.getAge()));
    //then
    assertThat(
        "in great kingdom should be at least two folks, who can become soldiers",
        partitionedFolks.get(Boolean.TRUE).size(),
        is(greaterThanOrEqualTo(2)));
  }

  @Test
  public void testIfFolksInTheWorldCanBeGroupedByNameFirstLetter() {
    //when
    Map<Character, List<Folk>> groupedFolks = this.world.getFolks()
        .collect(groupingBy(this.getFolksNameFirstCharacter));
    //then
    assertEquals(
        "there should be four buckets with names",
        4,
        groupedFolks.size());
  }

  @Test
  public void testIfFolksNamesFromGreatKingdomCanBeListed() {
    //when
    String listedNames = this.greatKingdom.getFolks()
        .map(Folk::getName)
        .collect(joining(", ", "[", "]"));
    //then
    assertEquals(
        "folks names from great kingdom should be listed",
        "[Aaa, Bbb, Ccc]",
        listedNames);
  }

  @Test
  public void testIfFolksInTheWorldCanBeGroupdByNameFirstLetterAndCounted() {
    //when
    Map<Character, Long> groupedFolks = this.world.getFolks()
        .collect(groupingBy(this.getFolksNameFirstCharacter, counting()));
    //then
    assertThat(
        "there should be 2 folks with name that starts with letter 'A'",
        groupedFolks.get('A'),
        is(2L));
    assertThat(
        "there should be 2 folks with name that starts with letter 'B'",
        groupedFolks.get('B'),
        is(2L));
    assertThat(
        "there should be 2 folks with name that starts with letter 'C'",
        groupedFolks.get('C'),
        is(2L));
    assertThat(
        "there should only 1 folk with name that starts with letter 'D'",
        groupedFolks.get('D'),
        is(1L));
  }

  @Test
  public void testIfFolksNamesInTheWoldCanBeGroupedByFirstLetter() {
    Map<Character, Set<String>> groupedFolksNames = this.world.getFolks()
        .collect(groupingBy(this.getFolksNameFirstCharacter, mapping(Folk::getName, toSet())));
    assertThat(
        "there should be names 'Aaa' and 'Abb' in names starting with letter 'A'",
        groupedFolksNames.get('A'),
        both(hasItem("Aaa")).and(hasItem("Abb")));
  }

  @Test
  public void testGatheringNamesMethods() {
    //when
    String folksNames = this.gatherFolksNames();
    String folksNamesForEach = this.gatherFolksNamesWithForEach();
    String folksNamesReduce = this.gatherFolksNamesWithReduce();
    String folksNamesGlue = this.gatherFolksNamesWithNameGlue();
    String folksNamesCollector = this.gatherFolksNamesWithCollector();

    //then
    assertEquals("gathering names with for each and stream is the same", folksNames, folksNamesForEach);
    assertEquals("gathering names with for each and stream is the same", folksNames, folksNamesReduce);
    assertEquals("gathering names with for each and stream is the same", folksNames, folksNamesGlue);
    assertEquals("gathering names with for each and stream is the same", folksNames, folksNamesCollector);
  }

  private String gatherFolksNames() {
    String folksNames = "/";
    for (Folk folk : this.world.getFolks().collect(toList())) {
      if (1 < folksNames.length()) {
        folksNames += ", ";
      }
      folksNames += folk.getName();
    }
    return folksNames + "\\";
  }

  private String gatherFolksNamesWithForEach() {
    StringBuilder folksNames = new StringBuilder("/");
    this.world.getFolks()
        .map(Folk::getName)
        .forEach(name -> {
          if (1 < folksNames.length()) {
            folksNames.append(", ");
          }
          folksNames.append(name);
        });
    folksNames.append("\\");
    return folksNames.toString();
  }

  private String gatherFolksNamesWithReduce() {
    StringBuilder folksNames = this.world.getFolks()
        .map(Folk::getName)
        .reduce(
            new StringBuilder(""),
            (builder, name) -> {
              if (0 < builder.length()) {
                builder.append(", ");
              }
              builder.append(name);
              return builder;
            },
            (left, right) -> left.append(right));
    folksNames.insert(0, "/");
    folksNames.append("\\");
    return folksNames.toString();
  }

  private String gatherFolksNamesWithNameGlue() {
    NameGlue nameGlue = this.world.getFolks()
        .map(Folk::getName)
        .reduce(
            new NameGlue("/", ", ", "\\"),
            NameGlue::glue,
            NameGlue::refill);
    return nameGlue.toString();
  }

  private String gatherFolksNamesWithCollector() {
    return this.world.getFolks()
        .map(Folk::getName)
        .collect(new NameCollector("/", ", ", "\\"));
  }
}
