package com.mowers.domain.model.place.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.mowers.domain.model.physics.Position;
import com.mowers.domain.model.place.IPlace;
import com.mowers.domain.model.robot.IRobot;

import lombok.Getter;

@Getter
public class Plateau implements IPlace {

  private UUID id;
  private int upperX;
  private int upperY;
  private Map<Position, IRobot> plane;

  public Plateau(int upperX, int upperY) {
    id = UUID.randomUUID();
    this.upperX = upperX;
    this.upperY = upperY;
    plane = new HashMap<>();
  }

  public boolean addRobot(IRobot robot) {
    boolean result = false;
    if (robot.getPosition() != null
        && isInside(robot.getPosition())
        && !isAnObject(robot.getPosition())) {
      plane.put(robot.getPosition(), robot);
      result = true;
    }
    return result;
  }

  @Override
  public boolean isAnObject(Position position) {
    return plane.containsKey(position);
  }

  public boolean moveFromTo(Position origin, Position destiny) {
    boolean result = false;
    if (origin != null && destiny != null
        && isInside(origin) && isInside(destiny)
        && isAnObject(origin) && !isAnObject(destiny)) {
      plane.put(destiny, plane.get(origin));
      plane.remove(origin);
      result = true;
    }
    return result;
  }

  private boolean isInside(Position position) {
    return position.getX() <= upperX && position.getY() <= upperY
        && position.getX() >= 0 && position.getY() >= 0;
  }

}
