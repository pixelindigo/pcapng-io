package me.pixelindigo.pcapng;

public class UnsupportedVersionException extends Exception {
    UnsupportedVersionException(int versionMajor, int versionMinor) {
        super(String.format("Unsupported file version %d %d",
                versionMajor, versionMinor));
    }
}
