package bitstream;
import java.io.IOException;

/**
  *  Implements the same functionality of a BitInputStream but using
  *  a preloaded data buffer. Extremely faster.
 */
public class BufferedBitInputStream extends BitInputStream{
    byte[] bufferedData = null;
    int currentByte = 0;

    public BufferedBitInputStream(String filename) throws IOException {
        super(filename);
        bufferedData = new byte[reader.available()];
        reader.read(bufferedData);
        reader.close();
    }

    @Override
    public int read() throws IOException {
        if(currentBit == BYTE_SIZE || currentBit == 0){
            bufferByte = bufferedData[currentByte++];
            currentBit = 0;
        }
        return BitStreamUtils.getBit(bufferByte, currentBit++);
    }

    @Override
    public byte readByte() throws IOException{
        if(available() >= BYTE_SIZE){
            if (currentBit == BitStreamUtils.BYTE_SIZE || currentBit == 0){
                currentBit = 0;
                return bufferedData[currentByte++];
            }else{
                byte ret = 0;
                for(int index = 0; index < BitStreamUtils.BYTE_SIZE; index++){
                    ret = BitStreamUtils.setBit(ret, index, this.read());
                }
                return ret;
            }
        }else{
            return 0;
        }
    }

    @Override
    public int available() throws IOException {
        return (bufferedData.length - currentByte)*8 + ((currentBit != 0) ? BYTE_SIZE - currentBit : 0);
    }
}
