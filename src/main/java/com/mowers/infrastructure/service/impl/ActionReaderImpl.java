package com.mowers.infrastructure.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.mowers.domain.model.physics.Direction;
import com.mowers.domain.model.physics.Movement;
import com.mowers.domain.model.physics.Position;
import com.mowers.domain.model.place.impl.Plateau;
import com.mowers.domain.model.robot.impl.Mower;
import com.mowers.infrastructure.exception.FormatLineException;
import com.mowers.infrastructure.exception.LineNumberException;
import com.mowers.infrastructure.exception.LineSizeException;
import com.mowers.infrastructure.service.ActionReader;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.util.Pair;

import lombok.Getter;

@Getter
public class ActionReaderImpl implements ActionReader<Plateau, Mower, Movement> {

  private Plateau place;
  private List<Pair<Mower, List<Movement>>> actionBlocks;

  public ActionReaderImpl(String path) throws Exception {
    actionBlocks = new ArrayList<>();
    List<String> lines = Files.readAllLines(Path.of(path));
    extractPlace(lines.remove(0));
    if (lines.size() % 2 != 0) {
      throw new LineNumberException("The number of lines is not correct: " + lines.size());
    }
    for (int i = 0; i < lines.size(); i = i + 2) {
      extractRobotAndMovements(lines.get(i), lines.get(i + 1));
    }
  }

  private void extractPlace(String place) throws LineSizeException {
    String[] coordinates = place.split(" ");
    if (coordinates.length != 2) {
      throw new LineSizeException("Place line must have two arguments");
    }
    this.place = new Plateau(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
  }

  private void extractRobotAndMovements(String robot, String movements) throws Exception {
    Mower robotParsed = parseRobot(robot);
    place.addRobot(robotParsed);
    actionBlocks.add(Pair.of(robotParsed, parseMovements(movements)));
  }

  private Mower parseRobot(String robot) throws LineSizeException {
    String[] robotValues = robot.split(" ");
    if (robotValues.length != 3) {
      throw new LineSizeException("Robot line must have three arguments");
    }
    Position position = Position.builder().x(Integer.parseInt(robotValues[0]))
        .y(Integer.parseInt(robotValues[1])).build();
    Direction direction = Direction.valueOf(robotValues[2]);
    return new Mower(UUID.randomUUID(), position, direction);
  }

  private List<Movement> parseMovements(String movements) throws FormatLineException {
    if (!movements.matches("[A-Z]+")) {
      throw new FormatLineException("The format line is not correct");
    }
    Character[] movementsValues = ArrayUtils.toObject(movements.toCharArray());
    return Arrays.stream(movementsValues).map(movement -> Movement.valueOf(movement.toString()))
        .collect(Collectors.toList());
  }

}
