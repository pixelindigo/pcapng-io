package me.pixelindigo.pcapng;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

class BinaryInputStream extends FilterInputStream {

    enum Endianness {LITTLE_ENDIAN, BIG_ENDIAN}

    private Endianness endianness = Endianness.BIG_ENDIAN;

    /**
     * An input stream that handles endianness of the stream.
     * By default big endian stream is assumed. To change this, please, use {@link #setEndianness(Endianness)}.
     *
     * @param in the underlying input stream, or <code>null</code> if
     *           this instance is to be created without an underlying stream.
     */
    BinaryInputStream(InputStream in) {
        super(in);
    }

    /**
     * @return the next four bytes of the input stream, interpreted as a signed 32-bit integer
     * @throws IOException if an I/O error occurs
     */
    public int readInt() throws IOException {
        int b1 = readByte();
        int b2 = readByte();
        int b3 = readByte();
        int b4 = readByte();

        if (endianness == Endianness.BIG_ENDIAN) {
            return bytesToInteger(b1, b2, b3, b4);
        } else {
            return bytesToInteger(b4, b3, b2, b1);
        }
    }
    /**
     * @return the next four bytes of the input stream, interpreted as an unsigned 32-bit integer
     * @throws IOException if an I/O error occurs
     */
    public long readUnsignedInt() throws IOException {
        int b1 = readByte();
        int b2 = readByte();
        int b3 = readByte();
        int b4 = readByte();

        if (endianness == Endianness.BIG_ENDIAN) {
            return bytesToUnsignedInteger(b1, b2, b3, b4);
        } else {
            return bytesToUnsignedInteger(b4, b3, b2, b1);
        }
    }

    /**
     * @return the next two bytes of the input stream, interpreted as an unsigned 16-bit integer
     * @throws IOException if an I/O error occurs
     */
    public int readUnsignedShort() throws IOException {
        int b1 = readByte();
        int b2 = readByte();

        if (endianness == Endianness.BIG_ENDIAN) {
            return bytesToUnsignedShort(b1, b2);
        } else {
            return bytesToUnsignedShort(b2, b1);
        }
    }

    /**
     * Sets the endianness of the input stream
     * @param endianness
     */
    public void setEndianness(Endianness endianness) {
        this.endianness = endianness;
    }

    /**
     * Reads a byte from the input stream checking that the end of file (EOF) has not been
     * encountered.
     *
     * @return byte read from input
     * @throws IOException if an error is encountered while reading
     * @throws EOFException if the end of file (EOF) is encountered.
     */
    private int readByte() throws IOException {
        int value = in.read();

        if (value == -1) {
            throw new EOFException();
        }

        return value;
    }

    private int bytesToInteger(int b1, int b2, int b3, int b4) {
        return (b1 << 24) | (b2 << 16) | (b3 << 8) | b4;
    }

    private long bytesToUnsignedInteger(int b1, int b2, int b3, int b4) {
        long signedInteger = bytesToInteger(b1, b2, b3, b4);
        return signedInteger & 0xFFFFFFFFL;
    }

    private int bytesToUnsignedShort(int b1, int b2) {
        return (b1 << 8) | b2;
    }
}
