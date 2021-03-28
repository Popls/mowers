package com.mowers.domain.model.physics;

public enum Movement {
  R("R"),
  L("L"),
  M("M");

  private final String letter;

  Movement(final String letter) {
    this.letter = letter;
  }

  @Override
  public String toString() {
    return letter;
  }
}
