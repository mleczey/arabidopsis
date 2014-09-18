package com.mleczey.darkages;

import com.mleczey.darkages.place.Kingdom;
import java.util.stream.StreamSupport;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.internal.SelfDescribingValue;

public class KingdomsMatcher extends TypeSafeMatcher<Iterable<? super Kingdom>> {

  public static Matcher<? super Iterable<? super Kingdom>> hasOtherKingdoms(Kingdom kingdom) {
    return new KingdomsMatcher(kingdom);
  }

  private final Kingdom kingdom;

  private KingdomsMatcher(Kingdom kingodm) {
    this.kingdom = kingodm;
  }

  @Override
  protected boolean matchesSafely(Iterable<? super Kingdom> item) {
    return StreamSupport.stream(item.spliterator(), false).anyMatch(k -> k.equals(this.kingdom));
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("other kingdeom different from kingdom ")
        .appendDescriptionOf(new SelfDescribingValue<>(this.kingdom));
  }
}
