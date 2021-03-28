package com.mowers.infrastructure.service.impl;

import java.io.FileWriter;
import java.util.List;

import com.mowers.domain.model.robot.impl.Mower;
import com.mowers.infrastructure.service.StatusWriter;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class StatusWriterFileImpl implements StatusWriter {

  String path;
  List<Mower> mowers;

  @SneakyThrows
  public void write() {
    FileWriter writer = new FileWriter(path);
    for (Mower mower : mowers) {
      writer.write(mower.toString() + System.lineSeparator());
    }
    writer.close();
  }

}
