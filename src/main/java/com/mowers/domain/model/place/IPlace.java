package com.mowers.domain.model.place;

import com.mowers.domain.model.physics.Position;
import com.mowers.domain.model.robot.IRobot;

public interface IPlace {

  boolean addRobot(IRobot robot);

  boolean isAnObject(Position position);

  boolean moveFromTo(Position origin, Position destiny);

}
