package com.mowers.infrastructure.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import com.mowers.domain.model.physics.Direction;
import com.mowers.domain.model.physics.Movement;
import com.mowers.domain.model.physics.Position;
import com.mowers.domain.model.robot.impl.Mower;
import com.mowers.infrastructure.exception.FormatLineException;
import com.mowers.infrastructure.exception.LineNumberException;
import com.mowers.infrastructure.exception.LineSizeException;
import com.mowers.infrastructure.service.impl.ActionReaderImpl;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class ActionReaderImplTest {

  public static final String INPUT_PATH = "src/test/resources/input/";
  public ActionReaderImpl actionReaderImpl;

  @Before
  public void setUp() throws Exception {
    actionReaderImpl = new ActionReaderImpl(INPUT_PATH + "validBlock.txt");
  }

  @Test
  public void actionReader() {
    List<Movement> movementsForFirstRobot = List
        .of(Movement.L, Movement.M, Movement.L, Movement.M, Movement.L,
            Movement.M, Movement.L, Movement.M, Movement.M);
    List<Movement> movementsForSecondRobot = List
        .of(Movement.M, Movement.M, Movement.R, Movement.M, Movement.M,
            Movement.R, Movement.M, Movement.R, Movement.R, Movement.M);
    assertNotNull(actionReaderImpl.getPlace());
    assertEquals(5, actionReaderImpl.getPlace().getUpperX());
    assertEquals(5, actionReaderImpl.getPlace().getUpperY());
    assertEquals(movementsForFirstRobot, actionReaderImpl.getActionBlocks().get(0).getSecond());
    assertEquals(movementsForSecondRobot, actionReaderImpl.getActionBlocks().get(1).getSecond());
    assertEquals(Position.builder().x(1).y(2).build(),
        actionReaderImpl.getActionBlocks().get(0).getFirst().getPosition());
    assertEquals(Position.builder().x(3).y(3).build(),
        actionReaderImpl.getActionBlocks().get(1).getFirst().getPosition());
    assertEquals(Direction.N, actionReaderImpl.getActionBlocks().get(0).getFirst().getDirection());
    assertEquals(Direction.E, actionReaderImpl.getActionBlocks().get(1).getFirst().getDirection());
  }

  @Test(expected = LineNumberException.class)
  public void actionReaderInvalidBlock() throws Exception {
    actionReaderImpl = new ActionReaderImpl(INPUT_PATH + "invalidBlock.txt");
  }

  @Test
  public void extractPlace() throws Exception {
    Whitebox.invokeMethod(actionReaderImpl, "extractPlace", "5 5");
    assertEquals(5, actionReaderImpl.getPlace().getUpperX());
    assertEquals(5, actionReaderImpl.getPlace().getUpperY());
  }

  @Test(expected = LineSizeException.class)
  public void extractPlaceInvalidLine() throws Exception {
    Whitebox.invokeMethod(actionReaderImpl, "extractPlace", "5 5 1");
  }

  @Test(expected = NumberFormatException.class)
  public void extractPlaceInvalidValues() throws Exception {
    Whitebox.invokeMethod(actionReaderImpl, "extractPlace", "A B");
  }

  @Test
  public void parseRobot() throws Exception {
    Mower mower = Whitebox.invokeMethod(actionReaderImpl, "parseRobot", "1 2 N");
    assertNotNull(mower.getUuid());
    assertEquals(Position.builder().x(1).y(2).build(), mower.getPosition());
    assertEquals(Direction.N, mower.getDirection());
  }

  @Test(expected = LineSizeException.class)
  public void parseRobotInvalidLine() throws Exception {
    Whitebox.invokeMethod(actionReaderImpl, "parseRobot", "1 2 N N");
  }

  @Test(expected = NumberFormatException.class)
  public void parseRobotInvalidPositons() throws Exception {
    Whitebox.invokeMethod(actionReaderImpl, "parseRobot", "A B C");
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseRobotInvalidDirection() throws Exception {
    Whitebox.invokeMethod(actionReaderImpl, "parseRobot", "1 2 3");
  }

  @Test
  public void parseMovements() throws Exception {
    List<Movement> expected = List.of(Movement.L, Movement.R, Movement.M);
    List<Movement> movements = Whitebox.invokeMethod(actionReaderImpl, "parseMovements", "LRM");
    assertNotNull(movements);
    assertEquals(expected, movements);
  }

  @Test(expected = FormatLineException.class)
  public void parseMovementsInvalidLine() throws Exception {
    List<Movement> movements = Whitebox.invokeMethod(actionReaderImpl, "parseMovements", "L R M");
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseMovementsInvalidMovements() throws Exception {
    List<Movement> movements = Whitebox.invokeMethod(actionReaderImpl, "parseMovements", "ABC");
  }

}