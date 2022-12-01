package com.progzesp.stalking.persistance.entity.answer;

public abstract class FileEntity extends NoNavPosEntity {
    public static boolean startsWithPattern(final byte[] byteArray, final byte[] pattern) {
        if (byteArray == null || pattern == null || pattern.length > byteArray.length) {
            return false;
        }
        for (int i = 0; i < pattern.length; i++) {
            if (byteArray[i] != pattern[i]) {
                return false;
            }
        }
        return true;
    }
}
