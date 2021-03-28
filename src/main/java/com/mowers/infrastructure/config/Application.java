package com.mowers.infrastructure.config;

import java.util.List;

import com.mowers.application.adapter.BladeAdapter;
import com.mowers.application.adapter.CameraAdapter;
import com.mowers.application.service.ExecuteActions;
import com.mowers.domain.model.robot.impl.Mower;
import com.mowers.infrastructure.service.impl.ActionReaderImpl;
import com.mowers.infrastructure.service.impl.StatusWriterFileImpl;
import com.mowers.infrastructure.service.impl.BladeAdapterImpl;
import com.mowers.infrastructure.service.impl.CameraAdapterImpl;

public class Application {

  public static void main(String[] args) throws Exception {
    ActionReaderImpl actionReaderImpl = new ActionReaderImpl("src/main/resources/input/block1.txt");
    BladeAdapter bladeAdapter = new BladeAdapterImpl();
    CameraAdapter cameraAdapter = new CameraAdapterImpl();
    ExecuteActions executeActions = new ExecuteActions(actionReaderImpl.getPlace(),
        actionReaderImpl.getActionBlocks(), bladeAdapter, cameraAdapter);
    List<Mower> mowers = executeActions.execute();
    StatusWriterFileImpl statusWriterFileImpl = new StatusWriterFileImpl("src/main/resources/output/block1.txt", mowers);
    statusWriterFileImpl.write();
  }

}
