package com.mowers.application.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.mowers.application.adapter.BladeAdapter;
import com.mowers.application.adapter.CameraAdapter;
import com.mowers.domain.model.physics.Movement;
import com.mowers.domain.model.place.impl.Plateau;
import com.mowers.domain.model.robot.impl.Mower;
import com.mowers.infrastructure.service.impl.BladeAdapterImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.util.Pair;

public class ExecuteActionsTest {

  private Plateau place = Mockito.mock(Plateau.class);
  private Mower mower = Mockito.mock(Mower.class);
  private BladeAdapter bladeAdapter = Mockito.mock(BladeAdapterImpl.class);
  private CameraAdapter cameraAdapter = Mockito.mock(CameraAdapter.class);
  public ExecuteActions executeActions;

  @Before
  public void setUp() {
    List<Movement> movements = List.of(Movement.M);
    executeActions = new ExecuteActions(place, List.of(Pair.of(mower, movements)), bladeAdapter,
        cameraAdapter);
  }

  @Test
  public void executeIsGrass() {
    doNothing().when(mower).move(any(), any());
    when(cameraAdapter.isGrass(any())).thenReturn(true);
    List<Mower> mowers = executeActions.execute();
    assertEquals(1, mowers.size());
    assertEquals(mower, mowers.get(0));
    verify(bladeAdapter, times(1)).cutGrass();
  }

  @Test
  public void executeIsNotGrass() {
    doNothing().when(mower).move(any(), any());
    when(cameraAdapter.isGrass(any())).thenReturn(false);
    List<Mower> mowers = executeActions.execute();
    assertEquals(mower, mowers.get(0));
    verify(bladeAdapter, times(0)).cutGrass();
  }

}