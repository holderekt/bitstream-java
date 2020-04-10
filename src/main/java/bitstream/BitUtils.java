package bitstream;

public class BitUtils {
    private BitUtils(){}
    public static void printByte(byte b){
        for(int i =0; i< BitStreamUtils.BYTE_SIZE; i++){
            System.out.print(BitStreamUtils.getBit(b, i));
        }
    }
}
