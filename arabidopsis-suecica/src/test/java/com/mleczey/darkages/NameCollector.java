package com.mleczey.darkages;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class NameCollector implements Collector<String, NameGlue, String> {
  private final String begin;
  private final String glue;
  private final String end;

  public NameCollector(String begin, String glue, String end) {
    this.begin = begin;
    this.glue = glue;
    this.end = end;
  }

  @Override
  public Supplier<NameGlue> supplier() {
    return () -> new NameGlue(this.begin, this.glue, this.end);
  }

  @Override
  public BiConsumer<NameGlue, String> accumulator() {
    return NameGlue::glue;
  }

  @Override
  public BinaryOperator<NameGlue> combiner() {
    return NameGlue::refill;
  }

  @Override
  public Function<NameGlue, String> finisher() {
    return NameGlue::toString;
  }

  @Override
  public Set<Characteristics> characteristics() {
    return EnumSet.noneOf(Characteristics.class);
  }
  
}
