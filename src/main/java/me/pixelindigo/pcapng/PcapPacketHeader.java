package me.pixelindigo.pcapng;

public class PcapPacketHeader {

    private long timestampSeconds;
    private long timestampUnits;
    private long capturedLength;
    private long originalLength;

    PcapPacketHeader() {}

    PcapPacketHeader(long timestampSeconds, long timestampUnits,
                     long capturedLength, long originalLength) {
        this.timestampSeconds = timestampSeconds;
        this.timestampUnits = timestampUnits;
        this.capturedLength = capturedLength;
        this.originalLength = originalLength;
    }

    public long getTimestampSeconds() {
        return timestampSeconds;
    }

    public void setTimestampSeconds(long timestampSeconds) {
        this.timestampSeconds = timestampSeconds;
    }

    public long getTimestampUnits() {
        return timestampUnits;
    }

    public void setTimestampUnits(long timestampUnits) {
        this.timestampUnits = timestampUnits;
    }

    public long getCapturedLength() {
        return capturedLength;
    }

    public void setCapturedLength(long capturedLength) {
        this.capturedLength = capturedLength;
    }

    public long getOriginalLength() {
        return originalLength;
    }

    public void setOriginalLength(long originalLength) {
        this.originalLength = originalLength;
    }
}
