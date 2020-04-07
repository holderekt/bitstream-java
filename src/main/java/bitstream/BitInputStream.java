package main.java.bitstream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 *  Stream for reading single bits from a file.
 *  The file is loaded byte by byte but the read function return each bit for each byte.
 *  The bytes are represented with BIG ENDIAN. The bits are read form the most significant to the less significant
 *  bit. It can create problems when used with a BufferedInputStream.
 */
public class BitInputStream extends InputStream {

    private final FileInputStream reader;
    private final String FILENAME;
    private final short BYTE_SIZE = 8;
    private byte currentBit = 8;
    private byte bufferByte = 0;

    public BitInputStream(String filename) throws FileNotFoundException {
        this.FILENAME = filename;
        this.reader = new FileInputStream(FILENAME);
    }

    @Override
    public int read() throws IOException {
        if(currentBit == BYTE_SIZE || currentBit == 0){
            bufferByte = (byte) reader.read();
            currentBit = 0;
        }
        return BitStreamUtils.getBit(bufferByte, currentBit++);
    }

    public byte readByte() throws IOException{
        if (currentBit == BitStreamUtils.BYTE_SIZE || currentBit == 0){
            currentBit = 0;
            return (byte) reader.read();
        }else{
            byte ret = 0;
            for(int index = 0; index < BitStreamUtils.BYTE_SIZE; index++){
                ret = BitStreamUtils.setBit(ret, index, this.read());
            }
            return ret;
        }
    }
}
