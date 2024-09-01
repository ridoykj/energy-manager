package com.itbd.energymanager.constant;

public enum FilePath {
    STORE("store/", ""), //
    EMAIL_ATTACHMENT("email/attachment/", ""), //
    POST_EVENT("post/event/", ""), //
    ORG_LOGO("org/logo/", "org_logo.jpg"), //
    ORG_BANNER("org/banner/", "org_banner.jpg"), //
    ORG_REPORT("org/reports/", "org_banner.jpg"), //
    MOBILE_ANDROID_APP("org/app/android/", "URCL_EGM.apk"), //
    MOBILE_IPHONE_APP("org/app/iphone/", ""), //
    ORGANIZATION_RULES("organization_rules/", ""), //
    GALLERY("gallery/", "");

    private final String path;
    private final String fileName;

    // private enum constructor
    FilePath(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    public String get() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }
}
