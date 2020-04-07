package main.java.bitstream;

final class BitStreamUtils {
    static byte BYTE_SIZE = 8;
    private BitStreamUtils(){}

    static final int getBit(byte b, int pos){
        return ((b & (1 << (BYTE_SIZE - pos - 1)) & 0xFF) > 0 ? 1 : 0);
    }

    static final byte setBit(byte b, int pos, int bit){
        byte ret = (byte) (b| (bit << (BYTE_SIZE - pos - 1)));
        return ret;
    }
}
