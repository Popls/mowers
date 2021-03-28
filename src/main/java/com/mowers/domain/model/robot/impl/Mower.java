package com.mowers.domain.model.robot.impl;

import java.util.UUID;

import com.mowers.domain.model.physics.Direction;
import com.mowers.domain.model.physics.Movement;
import com.mowers.domain.model.physics.Position;
import com.mowers.domain.model.place.IPlace;
import com.mowers.domain.model.robot.IRobot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mower implements IRobot {

  private UUID uuid;
  private Position position;
  private Direction direction;

  @Override
  public void move(IPlace place, Movement movement) {
    Position destiny = applyMovement(movement);
    if (place.moveFromTo(position, destiny)) {
      position = destiny;
    }
  }

  private Position applyMovement(Movement movement) {
    Position newPosition = Position.builder().x(position.getX()).y(position.getY()).build();
    if (Movement.L.equals(movement)) {
      turnLeft();
    } else if (Movement.R.equals(movement)) {
      turnRight();
    } else if (movement != null) {
      moveForward(newPosition);
    }
    return newPosition;
  }

  private void turnLeft() {
    if (direction.equals(Direction.N)) {
      direction = Direction.W;
    } else if (direction.equals(Direction.W)) {
      direction = Direction.S;
    } else if (direction.equals(Direction.S)) {
      direction = Direction.E;
    } else {
      direction = Direction.N;
    }
  }

  private void turnRight() {
    if (direction.equals(Direction.N)) {
      direction = Direction.E;
    } else if (direction.equals(Direction.E)) {
      direction = Direction.S;
    } else if (direction.equals(Direction.S)) {
      direction = Direction.W;
    } else {
      direction = Direction.N;
    }
  }

  private Position moveForward(Position newPosition) {
    if (direction.equals(Direction.N)) {
      newPosition.setY(newPosition.getY() + 1);
    } else if (direction.equals(Direction.E)) {
      newPosition.setX(newPosition.getX() + 1);
    } else if (direction.equals(Direction.S)) {
      newPosition.setY(newPosition.getY() - 1);
    } else {
      newPosition.setX(newPosition.getX() - 1);
    }
    return newPosition;
  }

  @Override
  public String toString() {
    return position.getX() + " " + position.getY() + " " + direction.toString();
  }

}
