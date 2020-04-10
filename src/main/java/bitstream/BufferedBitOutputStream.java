package bitstream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  Implements the same functionality of a BitOutputStream but using
 *  a preloaded data buffer. Extremely faster.
 */
public class BufferedBitOutputStream extends BitOutputStream {
    byte[] data;
    ArrayList<Byte> dataBuffer = new ArrayList<>();

    public BufferedBitOutputStream(String filename) throws FileNotFoundException {
        super(filename);
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
        dataBuffer.add(bufferByte);
        reset();
    }


    @Override
    public void close() throws IOException {
        if(currentBit != 0){
            flushByte();
        }

        data = new byte[dataBuffer.size()];
        for(int index =0; index < dataBuffer.size(); index++)
            data[index] = dataBuffer.get(index);
        writer.write(data);
        writer.close();
        super.close();
    }
}
