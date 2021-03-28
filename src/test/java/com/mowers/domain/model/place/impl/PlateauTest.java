package com.mowers.domain.model.place.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import com.mowers.domain.model.physics.Direction;
import com.mowers.domain.model.physics.Position;
import com.mowers.domain.model.robot.IRobot;
import com.mowers.domain.model.robot.impl.Mower;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class PlateauTest {

  Plateau plateau;

  @Before
  public void setUp() {
    plateau = new Plateau(2, 2);
  }

  @Test
  public void plateauPlaneAndUUIDNotNull() {
    assertNotNull(plateau.getId());
    assertNotNull(plateau.getPlane());
  }

  @Test
  public void addRobot() {
    IRobot robot = new Mower(UUID.randomUUID(), Position.builder().x(0).y(0).build(),
        Direction.N);
    assertTrue(plateau.addRobot(robot));
    assertEquals(1, plateau.getPlane().size());
    assertEquals(robot, plateau.getPlane().get(robot.getPosition()));
  }

  @Test
  public void addRobotWithNullPosition() {
    IRobot robot = new Mower(UUID.randomUUID(), null, Direction.N);
    assertFalse(plateau.addRobot(robot));
    assertEquals(0, plateau.getPlane().size());
  }

  @Test
  public void addRobotOutsidePlane() {
    IRobot robot = new Mower(UUID.randomUUID(), Position.builder().x(10).y(10).build(),
        Direction.N);
    assertFalse(plateau.addRobot(robot));
    assertEquals(0, plateau.getPlane().size());
  }

  @Test
  public void addRobotWhereAlreadyAreARobot() {
    IRobot robot1 = new Mower(UUID.randomUUID(), Position.builder().x(0).y(0).build(),
        Direction.N);
    IRobot robot2 = new Mower(UUID.randomUUID(), Position.builder().x(0).y(0).build(),
        Direction.N);
    assertTrue(plateau.addRobot(robot1));
    assertEquals(1, plateau.getPlane().size());
    assertEquals(robot1, plateau.getPlane().get(robot1.getPosition()));
    assertFalse(plateau.addRobot(robot2));
    assertEquals(1, plateau.getPlane().size());
    assertEquals(robot1, plateau.getPlane().get(robot1.getPosition()));
  }

  @Test
  public void isAnObject() {
    IRobot robot = new Mower(UUID.randomUUID(), Position.builder().x(0).y(0).build(),
        Direction.N);
    plateau.addRobot(robot);
    assertTrue(plateau.isAnObject(robot.getPosition()));
  }

  @Test
  public void isAnObjectNull() {
    IRobot robot = new Mower(UUID.randomUUID(), Position.builder().x(0).y(0).build(),
        Direction.N);
    plateau.addRobot(robot);
    assertFalse(plateau.isAnObject(null));
  }

  @Test
  public void isNotAnObject() {
    IRobot robot = new Mower(UUID.randomUUID(), Position.builder().x(0).y(0).build(),
        Direction.N);
    plateau.addRobot(robot);
    assertFalse(plateau.isAnObject(Position.builder().x(1).y(1).build()));
  }

  @Test
  public void moveFromTo() {
    Position origin = Position.builder().x(0).y(0).build();
    Position destiny = Position.builder().x(1).y(1).build();
    IRobot robot = new Mower(UUID.randomUUID(), origin, Direction.N);
    plateau.addRobot(robot);
    assertTrue(plateau.isAnObject(origin));
    assertTrue(plateau.moveFromTo(origin, destiny));
    assertTrue(plateau.isAnObject(destiny));
  }

  @Test
  public void moveFromToNull() {
    Position origin = Position.builder().x(0).y(0).build();
    Position destiny = Position.builder().x(1).y(1).build();
    IRobot robot = new Mower(UUID.randomUUID(), origin, Direction.N);
    plateau.addRobot(robot);
    assertTrue(plateau.isAnObject(origin));
    assertFalse(plateau.moveFromTo(null, null));
    assertFalse(plateau.isAnObject(destiny));
  }

  @Test
  public void moveFromToInvalidPlace() {
    Position origin = Position.builder().x(0).y(0).build();
    Position destiny = Position.builder().x(-1).y(-1).build();
    IRobot robot = new Mower(UUID.randomUUID(), origin, Direction.N);
    plateau.addRobot(robot);
    assertTrue(plateau.isAnObject(origin));
    assertFalse(plateau.moveFromTo(origin, destiny));
    assertFalse(plateau.isAnObject(destiny));
  }

  @Test
  public void moveFromToOccupiedPlace() {
    Position origin = Position.builder().x(0).y(0).build();
    Position destiny = Position.builder().x(1).y(1).build();
    IRobot robot1 = new Mower(UUID.randomUUID(), origin, Direction.N);
    IRobot robot2 = new Mower(UUID.randomUUID(), destiny, Direction.N);
    plateau.addRobot(robot1);
    plateau.addRobot(robot2);
    assertTrue(plateau.isAnObject(origin));
    assertTrue(plateau.isAnObject(destiny));
    assertFalse(plateau.moveFromTo(origin, destiny));
    assertEquals(robot1, plateau.getPlane().get(robot1.getPosition()));
    assertEquals(robot2, plateau.getPlane().get(robot2.getPosition()));
  }

  @Test
  public void isInside() throws Exception {
    assertTrue(Whitebox.invokeMethod(plateau,
        "isInside", Position.builder().x(0).y(0).build()));
  }

  @Test
  public void isNotInside() throws Exception {
    assertFalse(Whitebox.invokeMethod(plateau,
        "isInside", Position.builder().x(10).y(10).build()));
  }

  @Test(expected = NullPointerException.class)
  public void isInsideNull() throws Exception {
    assertFalse(Whitebox.invokeMethod(plateau,
        "isInside", null));
  }
}