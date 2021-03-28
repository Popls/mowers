package com.mowers.domain.model.physics;

public enum Direction {
  N("N"),
  S("S"),
  E("E"),
  W("W");

  private final String letter;

  Direction(final String letter) {
    this.letter = letter;
  }

  @Override
  public String toString() {
    return letter;
  }
}
