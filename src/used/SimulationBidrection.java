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
 * for cases: 中间往两头开始覆盖
 * */

public class SimulationBidrection {
    
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
		long timestamp=System.currentTimeMillis();;
		WritableWorkbook book=Workbook.createWorkbook(new FileOutputStream("simulationBidirection_"+timestamp+".xls"));
		WritableSheet sheet1= book.createSheet("MinMaxDistance", 0);
     //barrier length is the same,while number not for radius is the same
		int nodeNumber=600,index=0;
		double square_x=1000,barrier_length=1000,radius=10;
		double square_y=100;
		double value1=-1,value2=-1,ac=0.0001;
		List<SensorNode> nodeList1 = new ArrayList<SensorNode>();
		List<SensorNode> nodeList2 = new ArrayList<SensorNode>();
		//uniform distribution
		
		while(nodeNumber<=600){
			System.out.println("ooooooo:"+nodeNumber);
			//exec 200 times
	     int times=1,time2=1;
	     double sum1=0,sum2=0;
		 while((times--)>0){
			nodeList1.clear();
			nodeList2.clear();
			Random rand = new Random();
			for(int i=0;i<nodeNumber;i++){
			    double temp_x = rand.nextDouble()*square_x;
			    double temp_y = rand.nextDouble()*square_y;
			    //double temp_y = Math.abs(rand.nextGaussian()*10);
				double temp_r = radius;//for the same radius
				SensorNode node1 = new SensorNode(temp_x,temp_y,temp_r);
				SensorNode node2 = new SensorNode(temp_x,temp_y,temp_r);
				nodeList1.add(node1);
				nodeList2.add(node2);
			}
			/*OnLine2 algo1 = new OnLine2(nodeList1,barrier_length,radius);
			double m = algo1.getMinMaxDistance();*/
			//System.out.print("m: "+m);
			UnLineBidirection2 algo2 = new UnLineBidirection2(barrier_length,square_y,radius,nodeList2,ac);
		    double n= algo2.getMinMax();
		    //System.out.print(" n: "+n+" ");
		    
		    //sum1+=m;
			sum2+=n;
			//System.out.println("  value2: "+value2);
			//paint
//	 		DrawImage di = new DrawImage("not on the line",n,nodeList2);
//	 		di.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
//	 		di.setSize(1200, 400);  
//	 		di.setLocationRelativeTo(null); 
//	 		di.setVisible(true);
//			System.out.println("nodeNumber:"+nodeNumber+" minmax:"+n);
//			for(int i=0;i<nodeList2.size();i++){
//				if(nodeList2.get(i).x_final-10<=0){
//					System.out.println("x:"+nodeList2.get(i).x+" y:"+nodeList2.get(i).y+" x_final:"+nodeList2.get(i).x_final+" y_final:"+nodeList2.get(i).y_final);
//				}
//			}
		 }
		 //write it
	     jxl.write.Number n1 = new  jxl.write.Number(0,index,nodeNumber);
		 sheet1.addCell(n1);
		 jxl.write.Number n2 = new  jxl.write.Number(1,index,barrier_length);
		 sheet1.addCell(n2);
		 jxl.write.Number n3 = new  jxl.write.Number(2,index,radius);
		 sheet1.addCell(n3);
//		 jxl.write.Number n4 = new  jxl.write.Number(3,index,sum1/time2);
//		 sheet1.addCell(n4);
		 jxl.write.Number n5 = new  jxl.write.Number(4,index,sum2/time2);
		 sheet1.addCell(n5);
		 nodeNumber=nodeNumber+10;
		 index++;
		}
		book.write();
		book.close();
	}
	
}
