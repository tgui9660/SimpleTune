
public class TestBitMasking {
	static final int flagAllOff = 0;  //         000...00000000 (empty mask)
	static final int flagbit1 = 1;    // 2^^0    000...00000001
	static final int flagbit2 = 2;    // 2^^1    000...00000010
	static final int flagbit3 = 4;    // 2^^2    000...00000100
	static final int flagbit4 = 8;    // 2^^3    000...00001000
	static final int flagbit5 = 16;   // 2^^4    000...00010000
	static final int flagbit6 = 32;   // 2^^5    000...00100000
	static final int flagbit7 = 64;   // 2^^6    000...01000000
	static final int flagbit8 = 128;  // 2^^7    000...10000000

	public static void main(String[] args){
		byte testValue = (byte)0x53;
		
		int maskValue = testValue & flagbit5;
		if(maskValue > 0){
			System.out.println("Hi 1");
		}
		

		maskValue = testValue & flagbit4;
		if(maskValue > 0){
			System.out.println("Hi 2");
		}
	}
}
