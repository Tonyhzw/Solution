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


public class SimulationforOffset {
    
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
		WritableWorkbook book=Workbook.createWorkbook(new FileOutputStream("simulation_for_offset_3_100.xls"));
		WritableSheet sheet1= book.createSheet("MinMaxDistance", 0);
     //barrier length is the same,while number not for radius is the same
		int nodeNumber=100,index=0;
		double square_x=1000,barrier_length=1000,radius=10;
		double square_y=100;
		double value1=-1,value2=-1,value3=-1,value4=-1,ac=0.0001,offset=1;
		//List<SensorNode> nodeList1 = new ArrayList<SensorNode>();
		List<SensorNode> nodeList2 = new ArrayList<SensorNode>();
		//uniform distribution
	while(offset<=100){
		
			//System.out.println("ooooooo:"+nodeNumber);
			//exec 200 times
	     int times=200,time2=200;
	     double sum=0;
		 while((times--)>0){
			//nodeList1.clear();
			nodeList2.clear();
			Random rand = new Random();
			for(int i=0;i<nodeNumber;i++){
				    double temp_x = rand.nextDouble()*square_x;
				    double temp_y = Math.abs(rand.nextGaussian()*offset);
					double temp_r = radius;//for the same radius
					SensorNode node2 = new SensorNode(temp_x,temp_y,temp_r);
					nodeList2.add(node2);
			}
			ac=0.0001;
			//for(int z=0;z<10;z++){
//				UnLine2 algo2 = new UnLine2(barrier_length,radius,nodeList2,ac);
//				value2=algo2.getMinMax();
//				sum+=value2;
		    //} 
		}
		 //for(int index=0;index<10;index++){
		 jxl.write.Number n1 = new  jxl.write.Number(0,index,nodeNumber);
		 sheet1.addCell(n1);
		 jxl.write.Number n2 = new  jxl.write.Number(1,index,barrier_length);
		 sheet1.addCell(n2);
		 jxl.write.Number n3 = new  jxl.write.Number(2,index,offset);
		 sheet1.addCell(n3);
		 jxl.write.Number n4 = new  jxl.write.Number(3,index,sum/time2);
		 sheet1.addCell(n4);
		 //}
		 index++;
		 offset=offset*2;
	}
	book.write();
	book.close();
  }
}
