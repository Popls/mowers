package com.mowers.domain.model.robot.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import com.mowers.domain.model.physics.Direction;
import com.mowers.domain.model.physics.Movement;
import com.mowers.domain.model.physics.Position;
import com.mowers.domain.model.place.IPlace;
import com.mowers.domain.model.place.impl.Plateau;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

public class MowerTest {

  Mower mower;
  IPlace iPlace = Mockito.mock(Plateau.class);

  @Before
  public void setUp() {
    mower = new Mower(UUID.randomUUID(), Position.builder().x(1).y(1).build(), Direction.N);
  }

  @Test
  public void mowerUUIDIsNotNUll() {
    assertNotNull(mower.getUuid());
  }

  @Test
  public void move() {
    when(iPlace.moveFromTo(any(), any())).thenReturn(true);
    mower.move(iPlace, Movement.M);
    assertEquals(Position.builder().x(1).y(2).build(), mower.getPosition());
    assertEquals(Direction.N, mower.getDirection());
  }

  @Test
  public void moveToInvalidPlace() {
    when(iPlace.moveFromTo(any(), any())).thenReturn(false);
    mower.move(iPlace, Movement.M);
    assertEquals(Position.builder().x(1).y(1).build(), mower.getPosition());
    assertEquals(Direction.N, mower.getDirection());
  }

  @Test
  public void moveTurn() {
    when(iPlace.moveFromTo(any(), any())).thenReturn(false);
    mower.move(iPlace, Movement.L);
    assertEquals(Position.builder().x(1).y(1).build(), mower.getPosition());
    assertEquals(Direction.W, mower.getDirection());
  }

  @Test()
  public void applyMovementNull() throws Exception {
    assertEquals(Position.builder().x(1).y(1).build(), Whitebox.invokeMethod(mower,
        "applyMovement", null));
    assertEquals(Direction.N, mower.getDirection());
  }

  @Test
  public void applyMovementTurnLeft() throws Exception {
    assertEquals(Position.builder().x(1).y(1).build(), Whitebox.invokeMethod(mower,
        "applyMovement", Movement.L));
    assertEquals(Direction.W, mower.getDirection());
  }

  @Test
  public void applyMovementTurnRight() throws Exception {
    assertEquals(Position.builder().x(1).y(1).build(), Whitebox.invokeMethod(mower,
        "applyMovement", Movement.R));
    assertEquals(Direction.E, mower.getDirection());
  }

  @Test
  public void applyMovementMove() throws Exception {
    assertEquals(Position.builder().x(1).y(2).build(), Whitebox.invokeMethod(mower,
        "applyMovement", Movement.M));
    assertEquals(Direction.N, mower.getDirection());
  }

  @Test()
  public void turnLeftFromNorth() throws Exception {
    Whitebox.invokeMethod(mower, "turnLeft");
    assertEquals(Position.builder().x(1).y(1).build(), mower.getPosition());
    assertEquals(Direction.W, mower.getDirection());
  }

  @Test()
  public void turnLeftFromWest() throws Exception {
    mower = new Mower(mower.getUuid(), mower.getPosition(), Direction.W);
    Whitebox.invokeMethod(mower, "turnLeft");
    assertEquals(Position.builder().x(1).y(1).build(), mower.getPosition());
    assertEquals(Direction.S, mower.getDirection());
  }

  @Test()
  public void turnLeftFromSouth() throws Exception {
    mower = new Mower(mower.getUuid(), mower.getPosition(), Direction.S);
    Whitebox.invokeMethod(mower, "turnLeft");
    assertEquals(Position.builder().x(1).y(1).build(), mower.getPosition());
    assertEquals(Direction.E, mower.getDirection());
  }

  @Test()
  public void turnLeftFromEast() throws Exception {
    mower = new Mower(mower.getUuid(), mower.getPosition(), Direction.E);
    Whitebox.invokeMethod(mower, "turnLeft");
    assertEquals(Position.builder().x(1).y(1).build(), mower.getPosition());
    assertEquals(Direction.N, mower.getDirection());
  }

  @Test()
  public void turnRightFromNorth() throws Exception {
    Whitebox.invokeMethod(mower, "turnRight");
    assertEquals(Position.builder().x(1).y(1).build(), mower.getPosition());
    assertEquals(Direction.E, mower.getDirection());
  }

  @Test()
  public void turnRightFromWest() throws Exception {
    mower = new Mower(mower.getUuid(), mower.getPosition(), Direction.W);
    Whitebox.invokeMethod(mower, "turnRight");
    assertEquals(Position.builder().x(1).y(1).build(), mower.getPosition());
    assertEquals(Direction.N, mower.getDirection());
  }

  @Test()
  public void turnRightFromSouth() throws Exception {
    mower = new Mower(mower.getUuid(), mower.getPosition(), Direction.S);
    Whitebox.invokeMethod(mower, "turnRight");
    assertEquals(Position.builder().x(1).y(1).build(), mower.getPosition());
    assertEquals(Direction.W, mower.getDirection());
  }

  @Test()
  public void turnRightFromEast() throws Exception {
    mower = new Mower(mower.getUuid(), mower.getPosition(), Direction.E);
    Whitebox.invokeMethod(mower, "turnRight");
    assertEquals(Position.builder().x(1).y(1).build(), mower.getPosition());
    assertEquals(Direction.S, mower.getDirection());
  }

  @Test()
  public void moveToNorth() throws Exception {
    Whitebox.invokeMethod(mower, "moveForward", mower.getPosition());
    assertEquals(Position.builder().x(1).y(2).build(), mower.getPosition());
    assertEquals(Direction.N, mower.getDirection());
  }

  @Test()
  public void moveToWest() throws Exception {
    mower = new Mower(mower.getUuid(), mower.getPosition(), Direction.W);
    Whitebox.invokeMethod(mower, "moveForward", mower.getPosition());
    assertEquals(Position.builder().x(0).y(1).build(), mower.getPosition());
    assertEquals(Direction.W, mower.getDirection());
  }

  @Test()
  public void moveToSouth() throws Exception {
    mower = new Mower(mower.getUuid(), mower.getPosition(), Direction.S);
    Whitebox.invokeMethod(mower, "moveForward", mower.getPosition());
    assertEquals(Position.builder().x(1).y(0).build(), mower.getPosition());
    assertEquals(Direction.S, mower.getDirection());
  }

  @Test()
  public void moveToEast() throws Exception {
    mower = new Mower(mower.getUuid(), mower.getPosition(), Direction.E);
    Whitebox.invokeMethod(mower, "moveForward", mower.getPosition());
    assertEquals(Position.builder().x(2).y(1).build(), mower.getPosition());
    assertEquals(Direction.E, mower.getDirection());
  }

}