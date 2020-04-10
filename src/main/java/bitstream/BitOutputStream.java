package bitstream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *  Stream for writing single bits in a file.
 *  The class works writing single bits inside a byte and then flushing the byte inside the specified file.
 *  The bytes are represented with BIG ENDIAN. When writing a single byte all the bits will be stored from the
 *  most significant bit to the lowest significant bit.
 *  It can create problems when used with a BufferedOutputStream.
 */
public class BitOutputStream extends OutputStream{

    protected final FileOutputStream writer;
    protected final String FILENAME;
    protected final short BYTE_SIZE  = 8;
    protected byte currentBit = 0;
    protected byte bufferByte = 0;


    public BitOutputStream(String filename) throws FileNotFoundException {
        this.FILENAME = filename;
        writer = new FileOutputStream(FILENAME);
    }


    @Override
    public void write(int i) throws IOException {
        if(i > 0)
            bufferByte = BitStreamUtils.setBit(bufferByte, currentBit, i);

        if(currentBit == BitStreamUtils.BYTE_SIZE - 1){
            flushByte();
        }else{
            currentBit++;
        }
    }


    @Override
    public void write(byte[] i) throws IOException {
        for(byte b : i){
            this.write(b);
        }
    }


    public void write(byte i) throws IOException {
        if(currentBit == 0){
            bufferByte = i;
            flushByte();
        }else{
            for(int index = 0; index < BYTE_SIZE; index++){
                write(BitStreamUtils.getBit(i, index));
            }
        }
    }


    private void flushByte() throws IOException {
        writer.write(bufferByte);
        reset();
    }

    public int getPadding(){
        return padding;
    }


    @Override
    public void close() throws IOException {
        if(currentBit != 0){
            padding = BYTE_SIZE - currentBit;
           flushByte();
        }
        writer.close();
        super.close();
    }


    void reset(){
        bufferByte = 0;
        currentBit = 0;
    }
}
