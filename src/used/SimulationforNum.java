package used;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class SimulationforNum {
    
	/*private WritableWorkbook book;
	private static WritableSheet sheet1;
	*//**
	 * @param args
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws IOException 
	 *//*
	public Simulation() throws IOException{
		this.book=Workbook.createWorkbook(new File("s1.xsl"));
		this.sheet1= this.book.createSheet("MinMaxDistance", 0);
	}*/
	public static void main(String[] args) throws RowsExceededException, WriteException, IOException {
		// TODO Auto-generated method stub
		WritableWorkbook book=Workbook.createWorkbook(new FileOutputStream("simulation_for_number_160_0.0001_1.xls"));
		WritableSheet sheet1= book.createSheet("MinMaxDistance", 0);
     //barrier length is the same,while number not for radius is the same
		int nodeNumber=50,index=0;
		double square_x=1000,barrier_length=1000,radius=10;
		double ac=0.0001;
		List<SensorNode> nodeList1 = new ArrayList<SensorNode>();
		List<SensorNode> nodeList2 = new ArrayList<SensorNode>();
		//uniform distribution
		
		while(nodeNumber<=200){
			System.out.println("ooooooo:"+nodeNumber);
			//exec 200 times
	     int times=200,time2=200;
	     double sum1=0,sum2=0;
		 while((times--)>0){
			int value1=0,value2=0;
			nodeList1.clear();
			nodeList2.clear();
			Random rand = new Random();
			for(int i=0;i<nodeNumber;i++){
					/*double temp_x = rand.nextInt((int) square_x);
					double temp_y = rand.nextInt((int) square_y);*/
			    double temp_x = rand.nextDouble()*square_x;
			    double temp_y = Math.abs(rand.nextGaussian()*10);
					double temp_r = radius;//for the same radius
					/*if(temp_r<9.5) temp_r=6+rand.nextDouble();
					else temp_r=90+rand.nextDouble();*/
					SensorNode node1 = new SensorNode(temp_x,temp_y,temp_r);
					SensorNode node2 = new SensorNode(temp_x,temp_y,temp_r);
					nodeList1.add(node1);
					nodeList2.add(node2);
			}
			OnLine2 algo1 = new OnLine2(nodeList1,barrier_length,radius);
			double m = algo1.getMinMaxDistance();
			//System.out.print("m: "+m);
//			UnLine2 algo2 = new UnLine2(barrier_length,radius,nodeList2,ac);
//		    double n= algo2.getMinMax();
		    //System.out.print(" n: "+n+" ");
		    //choose 10 nodes randomly to act as sensors running out of energy of working point
			//number 
		    for(int i=0;i<nodeList1.size();i++){
		    	SensorNode node1= nodeList1.get(i);
		    	if(!(node1.x==node1.x_final&&node1.y==node1.y_final)){
		    		value1++;
		    	}
		    	SensorNode node2= nodeList2.get(i);
		    	if(!(node2.x==node2.x_final&&node2.y==node2.y_final)){
		    		value2++;
		    	}
		    }

			/*OnLine2 algo3 = new OnLine2(nodeList1,barrier_length,radius);
		    value1=algo3.getMinMaxDistance();
		    System.out.print("value1: "+value1);
		    UnLine2 algo4 = new UnLine2(barrier_length,radius,nodeList2,ac);
		    //OnLine2 algo4 = new OnLine2(nodeList2,barrier_length,radius);
		    value2=algo4.getMinMax();*/
		    
		    sum1+=value1;
			sum2+=value2;
			//System.out.println("  value2: "+value2);
		 }
		 //write it
	     jxl.write.Number n1 = new  jxl.write.Number(0,index,nodeNumber);
		 sheet1.addCell(n1);
		 jxl.write.Number n2 = new  jxl.write.Number(1,index,barrier_length);
		 sheet1.addCell(n2);
		 jxl.write.Number n3 = new  jxl.write.Number(2,index,radius);
		 sheet1.addCell(n3);
		 jxl.write.Number n4 = new  jxl.write.Number(3,index,sum1/time2);
		 sheet1.addCell(n4);
		 jxl.write.Number n5 = new  jxl.write.Number(4,index,sum2/time2);
		 sheet1.addCell(n5);
		 nodeNumber=nodeNumber+10;
		 index++;
		}
		book.write();
		book.close();
	}
	
}
