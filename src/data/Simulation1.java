package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import used.SensorNode1;
import used.UnLine2;

public class Simulation1 {
	public static List<SensorNode1> readTxtToNode(int number){
		File directory = new File("src/data/data.txt"); 
		String path=directory.getAbsolutePath();
		List<SensorNode1> nodeList2 = new ArrayList<SensorNode1>();
		try {  
            File f = new File(path);  
            if (f.exists()) {  
                System.out.print("文件存在");  
            } else {  
                System.out.print("文件不存在");  
            }  
            BufferedReader input = new BufferedReader(new FileReader(f));  
            String str="";
            int temp_r=10;
            while ((number>0)&&((str = input.readLine()) != null)) {  
                System.out.println(str);
                String[] s1= str.split(",");
                SensorNode1 node2 = new SensorNode1(Double.parseDouble(s1[0]),Double.parseDouble(s1[1]),temp_r,false);
				//nodeList1.add(node1);
				nodeList2.add(node2);
				number--;
            }  
            input.close();
        } catch (Exception e) {  
            e.printStackTrace();  
  
        }
		return nodeList2;
    }  
	public static void main(String[] args) throws FileNotFoundException, IOException, RowsExceededException, WriteException{
		Config config = new Config();
		int nodeNumber = config.nodeNumber;
		double square_y = config.square_y;
		int radius = config.temp_r;
		double barrier_length = config.barrier_length;
		double ac =  config.ac;
		List<SensorNode1> nodeList2 = readTxtToNode(nodeNumber);
		double min_pos=0,min_neg=0,positive_y=0,negative_y=0,minDis=Double.MAX_VALUE,minY=0;
		int index=0;
		WritableWorkbook book=Workbook.createWorkbook(new FileOutputStream("new_branch_5_1.xls"));
		WritableSheet sheet1= book.createSheet("barrier+0.5", 2);
		double barrier_y=0,end=square_y;
		DecimalFormat df = new DecimalFormat("#.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		while(barrier_y<end){
			min_pos=Double.MAX_VALUE;
			min_neg=Double.MAX_VALUE;
			positive_y=0;
			negative_y=0;
			boolean down=false;
		    double sum2=0;
		    List<SensorNode1> nodeList3 = new ArrayList<SensorNode1>();
		    for(int i=0;i<nodeNumber;i++){
		    	 double x=nodeList2.get(i).x;
		    	 double y=nodeList2.get(i).y;
		    	 if(y<barrier_y){
		    		    negative_y+=(barrier_y-y)*(barrier_y-y);
				    	double dif=barrier_y-y;
				    	y+=2*dif;
				    	down=true;
				 }else{
				        positive_y+=(y-barrier_y)*(y-barrier_y);
				 }
				 y-=barrier_y;
		    	 nodeList3.add(new SensorNode1(x,y,radius,down));
		    }
		 UnLine2 algo3 = new UnLine2(barrier_length,radius,nodeList3,ac);
		 double value3=Double.parseDouble(df.format(algo3.getMinMax()));
		 if(value3<minDis){
			 minDis=value3;
			 minY=barrier_y;
		 }
		 //System.out.println(minY);
		 //double offset=algo3.getOffset(barrier_y);
		 jxl.write.Number n1 = new  jxl.write.Number(0,index,index);
		 sheet1.addCell(n1);
		 jxl.write.Number n2 = new  jxl.write.Number(1,index,barrier_y);
		 sheet1.addCell(n2);
		 jxl.write.Number n3 = new  jxl.write.Number(2,index,value3);
		 sheet1.addCell(n3);
		 index++;
		 barrier_y+=0.01;
		}
	book.write();
	book.close();
	System.out.println("minY:"+minY);
	}
}
