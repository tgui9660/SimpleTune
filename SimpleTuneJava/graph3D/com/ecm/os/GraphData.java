package com.ecm.os;

import java.io.*;

//Reads in the data file into a 2 dimension array
public class GraphData {
    
    private Double data[][];
    private String title = "Test Title";
    private int numGrids = 100;
    private double xmax = 50.0;
    private double ymax = 50.0;
    private double zmax = 50.0;
    private String xLabel = "X Label";
    private String zLabel = "Z Label";
    private String yLabel = "Y Label";
 
    // Calculated
    private int numPts;
    private int rows;
          
    public GraphData(){
    	try {
            readFile( "/Users/emorgan/Desktop/ST/SimpleTuneJava/graph3D/plot3D.dat" );
  	} catch( FileNotFoundException e){
            System.out.println("File not found -> "+"/Users/emorgan/Desktop/ST/SimpleTuneJava/graph3D/plot3D.dat");
  	}
    	
    }
    
    public GraphData( String filename ){
	try {
          readFile( filename );
	} catch( FileNotFoundException e){
          System.out.println("File not found -> "+filename);
	}
     }
    
    public String getTitle(){
        return title;
    }
    public double get_Z_Max(){
        return zmax;
    }
    public String get_Z_Label(){
        return zLabel;
    }
    public double get_X_Max(){
        return xmax;
    }
    public double get_Y_Max(){
        return ymax;
    }
    public String get_Y_Label(){
        return yLabel;
    }
    public String get_X_Label(){
        return xLabel;
    }
     public int getRows(){
        return rows;
    }
    
    public Double[][] getData(){
        return data;
    }
       
    
    /**Reads in plot file */
    private void readFile( String filename ) throws FileNotFoundException {
        int r=0,c=0;
	FileIn file = new FileIn( filename);
         try {
          title = file.readLine();               	//graph title 
          System.out.println("title = "+title);
          numGrids = file.readInt();            	//num grids on one side
          System.out.println("Numgrids :"+numGrids);
          numPts = numGrids*numGrids;
          rows = numGrids;
          System.out.println(numGrids);        
          xmax = file.readDouble();            	 	//xmax
          System.out.println(xmax);
          file.readString();                   		//comment
          
          xLabel = strip(file.readLine());     		//xlabel
          System.out.println(xLabel);
          ymax = file.readDouble();             	//ymax
          System.out.println(ymax);
          
          file.readString();                    	//comment
          
          yLabel = strip(file.readLine());           	//ylabel
          System.out.println(yLabel);
          zmax = file.readDouble();             	//zmax
          System.out.println(zmax);
          file.readString();                    	//comment
          
          zLabel = strip(file.readLine());           	//zlabel
          System.out.println(zLabel);
          
          data = new Double[rows][rows];
	  
	  //read in data points
          for(int i=0; i<numPts; i++){
             data[r][c] = file.readDouble();
             if( data[r][c] < 0 ) data[r][c] = 0.0;
             if( c < rows-1 ) c++;
             else {
                r++;
                c=0;
             }
          }
          
        } catch(Exception e){
          System.out.println("Invalid file format.");
          return;
        }
        
    }

    private String strip( String str ){
	    int idx = str.lastIndexOf(" ");
	    int idx1 = str.indexOf("//");
	    int id = Math.min(idx,idx1);
	    return str.substring(0,id);
    }
}


