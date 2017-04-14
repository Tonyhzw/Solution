package data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import used.OnLine2;
import used.SensorNode;
import used.SensorNode1;
import used.UnLine2;

public class SimulationThree {

	public static void main(String[] args) throws RowsExceededException, WriteException, FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		int nodeNumber = Config.nodeNumber;
		double square_y = Config.square_y;
		double square_x = Config.square_x;
		int radius = Config.temp_r;
		double barrier_length = Config.barrier_length;
		double ac =  Config.ac;
		List<SensorNode1> nodeList = new ArrayList<SensorNode1>();
		List<SensorNode> nodeList2 = new ArrayList<SensorNode>();
		List<SensorNode1> nodeList3 = new ArrayList<SensorNode1>();
		WritableWorkbook book=Workbook.createWorkbook(new FileOutputStream("simulation6.xls"));
		WritableSheet sheet1= book.createSheet("barrier", 2);
		//初始化数据
		Random rand = new Random();
		DecimalFormat df = new DecimalFormat("#.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		int index=0;
		while(nodeNumber<=100){
			nodeList.clear();
			nodeList2.clear();
			nodeList3.clear();
			for(int i=0;i<nodeNumber;i++){
			    double temp_x = Double.parseDouble(df.format(rand.nextDouble()*square_x));
			    double temp_y = Double.parseDouble(df.format(rand.nextDouble()*square_y));
			    nodeList.add(new SensorNode1(temp_x,temp_y,radius,false));
			    nodeList2.add(new SensorNode(temp_x,temp_y,radius));
			    nodeList3.add(new SensorNode1(temp_x,temp_y,radius,false));
			}
			 OnLine2 algo1 = new OnLine2(nodeList2,barrier_length,radius);
			 double value1=Double.parseDouble(df.format(algo1.getMinMaxDistance()));
			 UnLine2 algo2 = new UnLine2(barrier_length,radius,nodeList,ac);
			 double value2=Double.parseDouble(df.format(algo2.getMinMax()));
			 double value3=Double.parseDouble(df.format(SATest.getSA(square_y,nodeList3,nodeNumber,radius,barrier_length,ac)));
			 jxl.write.Number n1 = new  jxl.write.Number(0,index,nodeNumber);
			 sheet1.addCell(n1);
			 jxl.write.Number n2 = new  jxl.write.Number(1,index,value1);
			 sheet1.addCell(n2);
			 jxl.write.Number n3 = new  jxl.write.Number(2,index,value2);
			 sheet1.addCell(n3);
			 jxl.write.Number n4 = new  jxl.write.Number(3,index,value3);
			 sheet1.addCell(n4);
			 index++;
			 nodeNumber+=10;
		}
		 book.write();
		 book.close();
		 System.out.println("complete");
		}

}
