import java.nio.ByteBuffer;


public class TestHEXParsing {
	public static void main(String[] args){
		byte firstByte = (byte)0xC;
		byte secondByte = (byte)0x83;
		
		ByteBuffer bb = ByteBuffer.allocate(2);
		byte[] bytes = new byte[2];
		bytes[0] = firstByte;
		bytes[1] = secondByte;
		bb.put(bytes);
		
		short short1 = bb.getShort(0);
		
		System.out.println("First try:"+short1/4);
		
		
		bb.clear();
		
		bytes[1] = firstByte;
		bytes[0] = secondByte;
		bb.put(bytes);
		
		
		short1 = bb.getShort(0);
		
		System.out.println("Second try:"+short1/4);
	}
}
