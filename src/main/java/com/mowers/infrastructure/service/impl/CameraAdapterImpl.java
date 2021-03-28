package com.mowers.infrastructure.service.impl;

import com.mowers.application.adapter.CameraAdapter;
import com.mowers.domain.model.physics.Position;

public class CameraAdapterImpl implements CameraAdapter {

  public boolean isGrass(Position position){
    return true;
  }

}
