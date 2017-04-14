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

/*
 * barrier 纵坐标可变的情形
 * */
public class Simulation3 {
    
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
		WritableWorkbook book=Workbook.createWorkbook(new FileOutputStream("dif.xls"));
		WritableSheet sheet1= book.createSheet("barrier+0.5", 2);
     //barrier length is the same,while number not for radius is the same
		int nodeNumber=50,index=0;
		double square_x=1000,barrier_length=1000,radius=10;
		double square_y=100;
		double value1=-1,value2=-1,value3=-1,ac=0.0001;
		double barrier_y=0;
		List<SensorNode1> nodeList1 = new ArrayList<SensorNode1>();
		List<SensorNode1> nodeList2 = new ArrayList<SensorNode1>();
		//uniform distribution
		int times=200,count=0;
		double[] value=new double[20];
		while((times-count)>0){
			//nodeList1.clear();
			barrier_y=0;
			nodeList2.clear();
			Random rand = new Random();
			for(int i=0;i<nodeNumber;i++){
					/*double temp_x = rand.nextInt((int) square_x);
					double temp_y = rand.nextInt((int) square_y);*/
			    double temp_x = rand.nextDouble()*square_x;
			    //double temp_y = Math.abs(rand.nextGaussian()*10);
			    double temp_y=rand.nextDouble()*square_y;
					double temp_r = radius;//for the same radius
					/*if(temp_r<9.5) temp_r=6+rand.nextDouble();
					else temp_r=90+rand.nextDouble();*/
					//SensorNode1 node1 = new SensorNode1(temp_x,temp_y,temp_r);
					SensorNode1 node2 = new SensorNode1(temp_x,temp_y,temp_r,false);
					//nodeList1.add(node1);
					nodeList2.add(node2);
			}
			int[] num= new int[16],finalNum=new int[16];
			index=0;
			double positive_y=0,negative_y=0,min_pos=Double.MAX_VALUE,min_neg=Double.MAX_VALUE;
			double minDis=Double.MAX_VALUE,minY=0;
			for(int i=0;i<nodeNumber;i++){
				 double y=nodeList2.get(i).y; 
				 num[(int) (y/10)]++;
			}
			while(barrier_y<square_y){
				positive_y=0;
				negative_y=0;
				min_pos=Double.MAX_VALUE;
				min_neg=Double.MAX_VALUE;
				boolean down=false;
				 System.out.println("ooooooo:"+count+" "+barrier_y);
			     double sum2=0;
			     List<SensorNode1> nodeList3 = new ArrayList<SensorNode1>();
			     for(int i=0;i<nodeNumber;i++){
			    	 double x=nodeList2.get(i).x;
			    	 double y=nodeList2.get(i).y;
			    	 if(y<barrier_y){
			    		    negative_y+=(barrier_y-y)*(barrier_y-y);
			    		    //if(barrier_y-y<min_pos) min_neg=barrier_y-y;
					    	double dif=barrier_y-y;
					    	y+=2*dif;
					    	down=true;
					 }else{
					        positive_y+=(y-barrier_y)*(y-barrier_y);
					        //if(y-barrier_y<min_pos) min_pos=y-barrier_y;
					 }
					 y-=barrier_y;
			    	 nodeList3.add(new SensorNode1(x,y,radius,down));
			     }
			 UnLine2 algo2 = new UnLine2(barrier_length,radius,nodeList3,ac);
			 value[index]+=algo2.getMinMax();
			 //value2=algo2.getMinMax();
			 UnLine2_1 algo3 = new UnLine2_1(barrier_length,radius,nodeList3,ac);
			 //value[index]+=algo2.getMinMax();
			 value3=algo3.getMinMax();
			 if(value3<minDis){
				 minDis=value3;
				 minY=barrier_y;
		
			 }
			 System.out.println(minY);
			 double offset=algo3.getOffset(barrier_y);
			 jxl.write.Number n1 = new  jxl.write.Number(0,index,index);
			 sheet1.addCell(n1);
			 jxl.write.Number n2 = new  jxl.write.Number(1,index,barrier_y);
			 sheet1.addCell(n2);
			 jxl.write.Number n3 = new  jxl.write.Number(2,index,radius);
			 sheet1.addCell(n3);
			 jxl.write.Number n4 = new  jxl.write.Number(3,index,value3);
			 sheet1.addCell(n4);
			 jxl.write.Number n5 = new  jxl.write.Number(4,index,offset);
			 sheet1.addCell(n5);
//			 jxl.write.Number n6 = new  jxl.write.Number(5,index,positive_y);
//			 sheet1.addCell(n6);
//			 jxl.write.Number n7 = new  jxl.write.Number(6,index,negative_y);
//			 sheet1.addCell(n7);
//			 jxl.write.Number n8 = new  jxl.write.Number(7,index,(positive_y-negative_y)/10000);
//			 sheet1.addCell(n8);
//			 jxl.write.Number n8 = new  jxl.write.Number(7,index,min_pos);
//			 sheet1.addCell(n8);
//			 jxl.write.Number n9 = new  jxl.write.Number(8,index,min_neg);
//			 sheet1.addCell(n9);
//			 jxl.write.Number n10 = new  jxl.write.Number(9,index,min_pos-min_neg);
//			 sheet1.addCell(n10);
			 //index++;
			 index++;
			 barrier_y+=0.5;
//			 DrawImage2 di = new DrawImage2("not on the line",minY,nodeList3);
//		 		di.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
//		 		di.setSize(1200, 400);  
//		 		di.setLocationRelativeTo(null); 
//		 		di.setVisible(true);
			}
			count++;
		}
		//index=0;
//		while(index<value.length){
//		 jxl.write.Number n1 = new  jxl.write.Number(0,index,index*5);
//		 sheet1.addCell(n1);
//		 jxl.write.Number n2 = new  jxl.write.Number(1,index,barrier_length);
//		 sheet1.addCell(n2);
//		 jxl.write.Number n3 = new  jxl.write.Number(2,index,radius);
//		 sheet1.addCell(n3);
//		 jxl.write.Number n4 = new  jxl.write.Number(3,index,value[index]);
//		 sheet1.addCell(n4);
//		 jxl.write.Number n5 = new  jxl.write.Number(3,index,value[index]);
//		 sheet1.addCell(n4);
//		 jxl.write.Number n4 = new  jxl.write.Number(3,index,value[index]);
//		 sheet1.addCell(n4);
//		 index++;
//		}
		book.write();
		book.close();
	}
	
}
