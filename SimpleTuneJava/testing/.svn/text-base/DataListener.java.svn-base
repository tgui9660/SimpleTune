import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;


public class DataListener  implements SerialPortEventListener{
	
	private LinkedList<Byte> commands = new LinkedList<Byte>();
	private InputStream in = null;
	
	public DataListener(InputStream in){
		this.in = in;
		System.out.println("DataListener added.");
	}
	
	public void serialEvent(SerialPortEvent e) {
		System.out.println("Got Data, Event type :"+e.getEventType());
		System.out.println("Wanted data tpye :"+SerialPortEvent.DATA_AVAILABLE);
		
		int newData = 1;
		
		switch (e.getEventType()) {
		case SerialPortEvent.DATA_AVAILABLE:
			// Append new output to buffer
			while (newData != -1) {
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				try {
					
					//System.out.println("HI 1");
					
					int available = in.available();
					System.out.println("Available bytes :"+ available);
					
					byte[] bytesBack = new byte[available];
					
					in.read(bytesBack);
					System.out.println("Read bytes into array!");
					
					
					newData = in.read();
					//System.out.println("HI 2");
					
					for(int i = 0; i < available; i++){
						commands.add(bytesBack[i]);
					}
					
					
				} catch (IOException ex) {
					System.err.println(ex);
					return;
				}
				
			}
			
			
			System.out.println("---------------------");
			Iterator<Byte> iterator = commands.iterator();
			System.out.println("Printing out this many bytes :"+commands.size());
			while(iterator.hasNext()){
				Byte next = iterator.next();
				System.out.println("--->"+((int)next&0xFF)+" <> "+Integer.toHexString((int)next&0xFF));
			}
			//SSMQueryTest.getSPort().close();
			System.out.println("DONE");
			/*
			ByteBuffer bb = ByteBuffer.allocate(2);
			byte[] bytes = new byte[2];
			bytes[0] = commands.get(commands.size() - 2);
			bytes[1] = commands.get(commands.size() - 1);
			bb.put(bytes);
			
			System.out.println("First try:"+bb.getShort());
			
			bytes[1] = commands.get(commands.size() - 2);
			bytes[0] = commands.get(commands.size() - 1);
			bb.put(bytes);
			
			System.out.println("Second try:"+bb.getShort());
			*/
		}

	}
}
