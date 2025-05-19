package com.dinlurceis.smartmed.domain;

public enum S3Folder {
    AVATAR("avatars"),
    MEDICINE("medicines"),
    DISEASE("diseases"),
    SYMPTOMS("symptoms"),
    REVIEW("reviews");

    private final String folderName;

    S3Folder(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}