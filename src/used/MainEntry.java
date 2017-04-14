package used;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;


public class MainEntry {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        //instance node set for experiments
		int nodeNumber=200;
		double square_x=1000;
		double square_y=100;
		List<SensorNode1> nodeList1 = new ArrayList<SensorNode1>();
		List<SensorNode1> nodeList2 = new ArrayList<SensorNode1>();
		//uniform distribution
		int time=200;
		double sum=0,dis2=0;
		//while(time-->0){
			nodeList1.clear();
			//nodeList2.clear();
			Random rand = new Random();
			for(int i=0;i<nodeNumber;i++){
				double temp_x = rand.nextDouble()*square_x;
				//double temp_y = rand.nextDouble()*square_y;
				double temp_y = Math.abs(rand.nextGaussian()*10);
				double temp_r = 10;//for the same radius
				SensorNode1 node1 = new SensorNode1(temp_x,temp_y,temp_r,false);
				//SensorNode1 node2 = new SensorNode1(temp_x,temp_y,temp_r);
				nodeList1.add(node1);
				//nodeList2.add(node2);
			}
			/*for(SensorNode1 c:nodeList1)
				System.out.println(c.x+","+c.y);*/
			
			double barrier_length=1000;
			double radius=10,dis=80,value1=-1,value2=-1,ac=0.001;
			//OnLine2 algo1 = new OnLine2(nodeList1,barrier_length,radius);
			//OnLine3 algo1 = new OnLine3(nodeList1,barrier_length,radius);
			//UnLineNotEqual algo1 = new UnLineNotEqual(barrier_length,nodeList1);
			//dis2=algo1.getMinMaxDistance();
			//System.out.println(algo1.feasibleDistance(dis,nodeList1));
			//dis2=algo1.getMinMaxDistance();
			//System.out.println(" value:  "+dis2);
			//sum+=dis2;
			//System.out.println("--------");
		//}
		//System.out.println(" average value:  "+sum/200);
		//value1=algo1.getMinMaxDistance();
		//System.out.println("Optimal value:"+value1);
		UnLine2 algo2 = new UnLine2(barrier_length,radius,nodeList1,ac);
	    value2=algo2.getMinMax();
		if(value2!=-1){
			//System.out.println("Optimal value:"+dis2);
			//algo1.getProssibleDistance();
			//System.out.println(algo1.feasibleDistance(dis,nodeList1));
			//paint
//			DrawImage di = new DrawImage("on the line unequal",value2,nodeList1);
//	     	di.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
//			di.setSize(1200, 400);  
//			di.setLocationRelativeTo(null); 
//			di.setResizable(true);
//			di.setVisible(true);
		}
		//UnLine2 algo2 = new UnLine2(barrier_length,radius,nodeList2);
		///value2=algo2.getMinMax();
		//System.out.println("Optimal value:"+value2);
		/*UnLineNotEqual algo2 = new UnLineNotEqual(barrier_length,nodeList2);
		value2=algo2.getMinMax();
		if(value2==-2)
			System.out.println("there does not exist any solution to form a barrier.");
		if(value2!=-2&&value2!=-1){
		System.out.print(algo2.getFeasibleSolution(dis,nodeList2));
			System.out.println("Optimal value:"+value2);
			//paint
			try{
				Thread.sleep(6000L);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			DrawImage di2 = new DrawImage("not on the line",dis+radius,nodeList2);
			di2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
			di2.setSize(1200, 400);  
			di2.setLocationRelativeTo(null); 
			di2.setVisible(true);
		}*/
	}

}
