package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;


public class CreateData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * */
		Config config = new Config();
		int nodeNumber = config.nodeNumber;
		double square_x = config.square_x;
		double square_y = config.square_y;
		Random rand = new Random();
		String str="";
		DecimalFormat df = new DecimalFormat("#.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		//double[] y= new double[nodeNumber]; 
		for(int i=0;i<nodeNumber;i++){
		    double temp_x = rand.nextDouble()*square_x;
		    double temp_y=rand.nextDouble()*square_y;
		   // y[i]=Double.parseDouble(df.format(temp_y));
	   		str+=df.format(temp_x)+","+df.format(temp_y)+"\n";		
		}
//		for(int i=0;i<nodeNumber;i++){
//			for(int j=i+1;j<nodeNumber;j++){
//				str+="0,"+df.format((y[i]+y[j])/2);
//				if(!((i==nodeNumber-2)&&(j==nodeNumber-1))) str+="\n";
//			}
//		}
		System.out.println(str);
		File directory = new File("src/data/data.txt"); 
		String path=directory.getAbsolutePath();
		//System.out.println(path);
        try {
        	File f = new File(path);  
            if (f.exists()) {  
                System.out.println("文件存在");  
            } else {  
                System.out.println("文件不存在");  
                f.createNewFile();// 不存在则创建  
            }  
   
            BufferedWriter output = new BufferedWriter(new FileWriter(f));  
            output.write(str);  
            output.close();  
			System.out.print("finished");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
