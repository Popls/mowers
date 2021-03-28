package com.mowers.domain.model.action;

import com.mowers.domain.model.physics.Movement;
import com.mowers.domain.model.place.IPlace;

public interface IMove {

  void move(IPlace place, Movement movement);

}
