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

/**
 * with a draw here
 * @author ucas
 * 
 * just online
 *
 */
public class Simulation {
    
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
		WritableWorkbook book=Workbook.createWorkbook(new FileOutputStream("s10.xls"));
		WritableSheet sheet1= book.createSheet("MinMaxDistance", 0);
     //barrier length is the same,while number not for radius is the same
		int nodeNumber=60,index=0;
		double square_x=1000,barrier_length=1000,radius=10;
		double square_y=100;
		double value1=-1,value2=-1,value3=-1,value4=-1;
		List<SensorNode> nodeList1 = new ArrayList<SensorNode>();
		List<SensorNode> nodeList2 = new ArrayList<SensorNode>();
		//uniform distribution
		
		while(nodeNumber<=140){
			System.out.println("ooooooo:"+nodeNumber);
			//exec 200 times
	     int times=200,time2=200;
	     double sum1=0,sum2=0,sum3=0,sum4=0;
		 while((times--)>0){
			nodeList1.clear();
			nodeList2.clear();
			Random rand = new Random();
			for(int i=0;i<nodeNumber;i++){
					//double temp_x = rand.nextInt((int) square_x+1)/1.0;
					//double temp_y = rand.nextInt((int) square_y+1)/1.0;
				    double temp_x = rand.nextDouble()*square_x;
				   // double temp_y = rand.nextDouble()*square_y;
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
		    value1=algo1.getMinMaxDistance();
			//System.out.print("  value1: "+value1);
			/*if(value1!=-1){
				System.out.println("Optimal value:"+value1);
				//algo1.getProssibleDistance();
				//System.out.println(algo1.feasibleDistance(dis,nodeList1));
				//paint
				DrawImage di = new DrawImage("on the line",value1,nodeList1);
				di.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
				di.setSize(1200, 400);  
				di.setLocationRelativeTo(null); 
				di.setVisible(true);
			}*/
			//UnLine4 algo2 = new UnLine4(barrier_length,radius,nodeList2);
			//value2=algo2.getMinMax();
			OnLine3 algo2 = new OnLine3(nodeList1,barrier_length,radius);
		    value2=algo2.getMinMaxDistance();
			sum1+=value1;
			sum2+=value2;
			//System.out.println("  value2: "+value2);
			//random choose some nodes to become invalid
			/*for(int j=0;j<10;j++){
				Random rand2 = new Random();
				int index2= rand2.nextInt(nodeList1.size());
				nodeList1.remove(index2);
				nodeList2.remove(index2);
			}*/
			//OnLine2 algo3 = new OnLine2(nodeList1,barrier_length,radius);
		   // value3=algo3.getMinMaxDistance();
			//System.out.print("  value3: "+value3);
			//UnLine4 algo4 = new UnLine4(barrier_length,radius,nodeList2);
			//value4=algo4.getMinMax();
			//sum3+=value3;
			//sum4+=value4;
			//System.out.println("  value4: "+value4);
			/*System.out.println("  value2: "+value2);
			if(value2!=-1){
				System.out.println("Optimal value:"+value2);
				//algo1.getProssibleDistance();
				//System.out.println(algo1.feasibleDistance(dis,nodeList1));
				//paint
				DrawImage di = new DrawImage("on the line",value2,nodeList2);
				di.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
				di.setSize(1200, 400);  
				di.setLocationRelativeTo(null); 
				di.setVisible(true);
			}*/
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
		 jxl.write.Number n6 = new  jxl.write.Number(5,index,sum3/time2);
		 sheet1.addCell(n6);
		 jxl.write.Number n7 = new  jxl.write.Number(6,index,sum4/time2);
		 sheet1.addCell(n7);
		 nodeNumber=nodeNumber+10;
	     index++;
		}
		book.write();
		book.close();
	}
	
}
