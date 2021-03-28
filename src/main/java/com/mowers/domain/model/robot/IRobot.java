package com.mowers.domain.model.robot;

import com.mowers.domain.model.action.IMove;
import com.mowers.domain.model.physics.Direction;
import com.mowers.domain.model.physics.Position;

public interface IRobot extends IMove {

  Position getPosition();
  Direction getDirection();

}
