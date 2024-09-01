package com.itbd.energymanager.constant;

public enum FilePathEnum {
    ROOT("/result/")//
    , SOURCE(ROOT.getPath() + "source/") //
    , PROCESS(ROOT.getPath() + "process/");

    String path;

    FilePathEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
