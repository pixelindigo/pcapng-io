package me.pixelindigo.pcapng;

import java.io.IOException;
import java.io.InputStream;

import static me.pixelindigo.pcapng.BinaryInputStream.Endianness.*;

public class PcapReader {

    final static int MAGIC_NUMBER_MICRO_BE = 0xa1b2c3d4;
    final static int MAGIC_NUMBER_NANO_BE = 0xa1b23c4d;
    final static int MAGIC_NUMBER_MICRO_LE = 0xd4c3b2a1;
    final static int MAGIC_NUMBER_NANO_LE = 0x4d3cb2a1;

    final static int VERSION_MAJOR = 2;
    final static int VERSION_MINOR = 4;

    private BinaryInputStream inputStream;

    private int timeCorrection;
    private long significantFigures;
    private long snapshotLength;
    private int datalinkType;

    public PcapReader(InputStream inputStream) throws IOException, MalformedFileException, UnsupportedVersionException{
        this.inputStream = new BinaryInputStream(inputStream);
        processHeader();
    }

    private void processHeader() throws IOException, MalformedFileException, UnsupportedVersionException {
        // Magic number
        int magicNumber = inputStream.readInt();
        if (magicNumber == MAGIC_NUMBER_MICRO_LE ||
                magicNumber == MAGIC_NUMBER_NANO_LE) {
            inputStream.setEndianness(LITTLE_ENDIAN);
        }

        if (magicNumber == MAGIC_NUMBER_NANO_BE ||
                magicNumber == MAGIC_NUMBER_NANO_LE) {
            // TODO Handle timestamp resolution
        } else if (magicNumber == MAGIC_NUMBER_MICRO_BE ||
                magicNumber == MAGIC_NUMBER_MICRO_LE) {
            // TODO Handle timestamp resolution
        } else {
            throw new MalformedFileException("Malformed file. Magic number doesn't match");
        }

        // Version
        int versionMajor = inputStream.readUnsignedShort();
        int versionMinor = inputStream.readUnsignedShort();
        if (!(versionMajor == VERSION_MAJOR && versionMinor == VERSION_MINOR)) {
            throw new UnsupportedVersionException(versionMajor, versionMinor);
        }

        // Timezone correction
        timeCorrection = inputStream.readInt();

        // Timestamps accuracy
        significantFigures = inputStream.readUnsignedInt();

        snapshotLength = inputStream.readUnsignedInt();

        // TODO Add static fields corresponding to datalink types
        datalinkType = inputStream.readInt();
    }

    public PcapPacketHeader nextPacketHeader(PcapPacketHeader header) throws IOException{
        if (header == null) {
            header = new PcapPacketHeader();
        }
        header.setTimestampSeconds(inputStream.readUnsignedInt());
        header.setTimestampUnits(inputStream.readUnsignedInt());
        header.setCapturedLength(inputStream.readUnsignedInt());
        header.setOriginalLength(inputStream.readUnsignedInt());

        inputStream.skip(header.getCapturedLength());

        return header;
    }

    public void close() throws IOException {
        inputStream.close();
    }
}
