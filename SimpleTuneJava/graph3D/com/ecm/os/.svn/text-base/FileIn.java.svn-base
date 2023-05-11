package com.ecm.os;
import java.io.*;
import java.util.StringTokenizer;

//Data file reader
public class FileIn
{
    private BufferedReader br;
    private FileReader fr;
    private StringTokenizer st;
    
    public FileIn(String fn) throws FileNotFoundException
    {
        st = new StringTokenizer(" ");
        try {
          br = new BufferedReader( new FileReader(fn) );
        } catch (NullPointerException e) {
          e.printStackTrace();
        }
    }

    //read a string from the file
    public String readString() throws EOFException
    {
       try {
	 if( !st.hasMoreTokens() ) st = new StringTokenizer(br.readLine());
	 return st.nextToken();
       } catch(IOException e){
         e.printStackTrace();
       }
       return null;
    }

    //read whole record
    public String readLine() throws EOFException
    {
        String line=null;
        try {
          line = br.readLine();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return line;
    }
    
    //read int from file
    public int readInt(){
 	try {
          if( !st.hasMoreTokens() ) st = new StringTokenizer(br.readLine());
	  return Integer.parseInt(st.nextToken());
	} catch(IOException e){
          e.printStackTrace();
	}
	return -9999;
    }
    
    //read double from file
    public double readDouble(){
 	try {
          if( !st.hasMoreTokens() ) st = new StringTokenizer(br.readLine());
	  return Double.parseDouble(st.nextToken());
	} catch(IOException e){
          e.printStackTrace();
	}
	return Double.NaN;
    }
    
    //close file
    public void close(){
	try {
	  br.close();
	} catch(IOException e){
          e.printStackTrace();
	}
    }
}