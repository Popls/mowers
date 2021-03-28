package com.mowers.application.service;

import java.util.List;
import java.util.stream.Collectors;

import com.mowers.application.adapter.BladeAdapter;
import com.mowers.application.adapter.CameraAdapter;
import com.mowers.domain.model.physics.Movement;
import com.mowers.domain.model.place.impl.Plateau;
import com.mowers.domain.model.robot.impl.Mower;
import org.springframework.data.util.Pair;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExecuteActions {

  private Plateau place;
  private List<Pair<Mower, List<Movement>>> actionBlocks;
  private BladeAdapter bladeAdapter;
  private CameraAdapter cameraAdapter;

  public List<Mower> execute() {
    actionBlocks.forEach(mowerListPair -> {
      mowerListPair.getSecond().forEach(movement -> {
        mowerListPair.getFirst().move(place, movement);
        if (cameraAdapter.isGrass(mowerListPair.getFirst().getPosition())) {
          bladeAdapter.cutGrass();
        }
      });
    });
    return actionBlocks.stream().map(mowerListPair -> mowerListPair.getFirst())
        .collect(Collectors.toList());
  }

}
