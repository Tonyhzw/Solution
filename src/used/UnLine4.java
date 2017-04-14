package used;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import javax.swing.JFrame;
/**
 * first judge if is in critical set then do something
 * and without output notes.
 * and the solution for set 2 and 4 judge whether greater than zero
 ***/

public class UnLine4 {
   private List<SensorNode> nodeList = new ArrayList<SensorNode>();
   private double barrier_length=0;
   private double radius=0;
   public UnLine4(double length,double radius,List<SensorNode> list){
	   this.barrier_length=length;
	   this.radius=radius;
	   this.nodeList=list;
   }
   
   public double getMinMax(){
	   double optimalValue=binary_search(getDis1(this.nodeList),this.nodeList);
	   return optimalValue;
   }
   
   public boolean getFeasibleSolution(double dis,List<SensorNode> list){
	   //update the sensorNode status with dis
	   /*for(int i=0;i<list.size();i++){
		   double temp_y=list.get(i).y;
		   if(dis>=temp_y)
			   list.get(i).setMovement(0, Math.sqrt(dis*dis-temp_y*temp_y));
		   else
			   list.get(i).setMovement();//do not move
	   }*/
	   //for paint
	   for(int i=0;i<list.size();i++){
		   list.get(i).setXFinal(list.get(i).x);
		   list.get(i).setYFinal(list.get(i).y);
	   }
	   double rightmost=0;
	   List<SensorNode> set1 = new ArrayList<SensorNode>();
	   List<SensorNode> criticalSet = new ArrayList<SensorNode>();
	   List<SensorNode> set2 = new ArrayList<SensorNode>();
	   List<SensorNode> set3 = new ArrayList<SensorNode>();
	   List<SensorNode> set4 = new ArrayList<SensorNode>();
	  // List<SensorNode> set5 = new ArrayList<SensorNode>();
	   while(rightmost<this.barrier_length){
		  set1.clear();
		  set2.clear();
		  set3.clear();
		  set4.clear();
		  double maxright=0,maxrightX=0;
		  int max_r=0;
		  for(int i=0;i<list.size();i++){
			  double temp_y=list.get(i).y;
			  if(!criticalSet.contains(list.get(i))&&dis+this.radius>=temp_y){
				  double max_right=Math.sqrt((dis+this.radius)*(dis+this.radius)-temp_y*temp_y);
				  double max_right_y=this.radius*temp_y/(dis+this.radius);
				  double max_right_x=Math.sqrt(dis*dis-(temp_y-max_right_y)*(temp_y-max_right_y));
				  //double right_zero_x_1=Math.sqrt(dis*dis-temp_y*temp_y);
				 // double right_zero_y_2=2*this.radius*(this.radius+Math.sqrt(dis*dis-temp_y*temp_y))*temp_y/(Math.hypot(dis, this.radius)+2*this.radius*Math.sqrt(dis*dis-temp_y*temp_y));
				/*  double t1=list.get(i).x+max_right-Math.sqrt(this.radius*this.radius-max_right_y*max_right_y);
				  double t2=list.get(i).x+max_right;
				  System.out.println("set1:["+t1+","+t2+"],while r="+rightmost+" ("+list.get(i).x+","+list.get(i).y+")"+" dis: "+dis);*/
				  if(list.get(i).x+max_right-2*Math.sqrt(this.radius*this.radius-max_right_y*max_right_y)<=rightmost&&list.get(i).x+max_right>=rightmost){
					  //if(!criticalSet.contains(list.get(i))){
					      set1.add(list.get(i));
						  if(maxright<list.get(i).x+max_right){
							  maxright=list.get(i).x+max_right;
							  maxrightX=list.get(i).x+max_right_x;
							  max_r=i;
						  }
					  //}
				  }
			  }
			  //flag=true two results, others false
			  //boolean flag=2*temp_y*temp_y<(dis*dis+this.radius*this.radius+2*this.radius*Math.sqrt(dis*dis-temp_y*temp_y))?true:false;
		  }
		  if(set1.size()!=0){
			  list.get(max_r).setXFinal(maxrightX);
			  list.get(max_r).setYFinal(list.get(max_r).y-Math.sqrt(dis*dis-(maxrightX-list.get(max_r).x)*(maxrightX-list.get(max_r).x)));
			  criticalSet.add(list.get(max_r));
			  rightmost=maxright;
			 /* double n=maxrightX-Math.sqrt(this.radius*this.radius-list.get(max_r).y_final*list.get(max_r).y_final);
			  System.out.println("set1:"+rightmost+" x-r:"+n);*/
		  }else{
			  double minleft=Double.MAX_VALUE,minleftX=0;
			  int min_zero_l=0;
			  for(int i=0;i<list.size();i++){
				  double temp_y=list.get(i).y;
				  double max_right=Math.sqrt((dis+this.radius)*(dis+this.radius)-temp_y*temp_y);
				  double max_right_y=this.radius*temp_y/(dis+this.radius);
				  //double max_right_x=Math.sqrt(dis*dis-(temp_y-max_right_y)*(temp_y-max_right_y));
				  if(!criticalSet.contains(list.get(i))&&dis>=list.get(i).y){
					  double right_zero_x_1=Math.sqrt(dis*dis-temp_y*temp_y);
					 /* double t1=list.get(i).x+right_zero_x_1-this.radius;
					  double t2=list.get(i).x+max_right-Math.sqrt(this.radius*this.radius-max_right_y*max_right_y);
					  System.out.println("set2_1:["+t1+","+t2+"],while r="+rightmost);*/
					  if(list.get(i).x+right_zero_x_1-this.radius<rightmost&&list.get(i).x+max_right-2*Math.sqrt(this.radius*this.radius-max_right_y*max_right_y)>rightmost){
						 /*double t1=list.get(i).x+right_zero_x_1-this.radius;
						  double t2=list.get(i).x+max_right-Math.sqrt(this.radius*this.radius-max_right_y*max_right_y);
						  System.out.println("set2_1:["+t1+","+t2+"],while r="+rightmost+" ("+list.get(i).x+","+list.get(i).y+")"+" dis: "+dis);*/
						  //if(!criticalSet.contains(list.get(i))){
						      set2.add(list.get(i));
							  if(minleft>list.get(i).x+max_right){
								  minleft=list.get(i).x+max_right;
								  //minleftX=max_right_x;
								  min_zero_l=i;
							  }
						  //}
					  }
				  }
				  if(!criticalSet.contains(list.get(i))&&dis+this.radius>=list.get(i).y&&dis<list.get(i).y){
					      double y=list.get(i).y-dis;
					      //double right_zero_x_1=Math.sqrt(dis*dis-temp_y*temp_y);
						/*  double t1=list.get(i).x-Math.sqrt(this.radius*this.radius-y*y);
						  double t2=list.get(i).x+max_right-2*Math.sqrt(this.radius*this.radius-max_right_y*max_right_y);
						  System.out.println("set2_2:["+t1+","+t2+"],while r="+rightmost);*/
						  if(list.get(i).x-Math.sqrt(this.radius*this.radius-y*y)<=rightmost&&list.get(i).x+max_right-2*Math.sqrt(this.radius*this.radius-max_right_y*max_right_y)>rightmost){
							  /*double t1=list.get(i).x-Math.sqrt(this.radius*this.radius-y*y);
							  double t2=list.get(i).x+max_right-2*Math.sqrt(this.radius*this.radius-max_right_y*max_right_y);
							  System.out.println("set2_2:["+t1+","+t2+"],while r="+rightmost+":("+list.get(i).x+","+list.get(i).y+")"+" dis: "+dis);*/
							  //if(!criticalSet.contains(list.get(i))){
								  //System.out.println("in");
							      set2.add(list.get(i));
								  if(minleft>list.get(i).x+max_right){
									  minleft=list.get(i).x+max_right;
									  //minleftX=max_right_x;
									  min_zero_l=i;
								  }
							  //}
						  } 
				  }
			 }
			 if(set2.size()!=0){
				 /* double temp1=list.get(min_zero_l).x-rightmost;
				  double temp2=this.radius*this.radius-temp1*temp1-dis*dis+list.get(min_zero_l).y*list.get(min_zero_l).y;
				  double y=(list.get(min_zero_l).y*(temp2-2*temp1*temp1)-Math.sqrt(list.get(min_zero_l).y*(temp2-2*temp1*temp1)*list.get(min_zero_l).y*(temp2-2*temp1*temp1)+(list.get(min_zero_l).y*list.get(min_zero_l).y+temp1*temp1)*(temp2*temp2-4*temp1*temp1*(dis*dis-list.get(min_zero_l).y*list.get(min_zero_l).y))))/(2*(list.get(min_zero_l).y*list.get(min_zero_l).y+temp1*temp1));
				  double x=list.get(min_zero_l).x+Math.sqrt(dis*dis-(list.get(min_zero_l).y-y)*(list.get(min_zero_l).y-y));
				  list.get(min_zero_l).setXFinal(x);
				  criticalSet.add(list.get(min_zero_l));
				  rightmost=x+Math.sqrt(this.radius*this.radius-y*y);*/
				 double temp1=rightmost-list.get(min_zero_l).x;
				 //double temp2=this.radius*this.radius-temp1*temp1-dis*dis+list.get(min_zero_l).y*list.get(min_zero_l).y;
				 double temp2=dis*dis-this.radius*this.radius-temp1*temp1-list.get(min_zero_l).y*list.get(min_zero_l).y;
				 double temp=temp2*temp2*list.get(min_zero_l).y*list.get(min_zero_l).y-(temp2*temp2-4*temp1*temp1*this.radius*this.radius)*(list.get(min_zero_l).y*list.get(min_zero_l).y+temp1*temp1);
				 double y1=0,y2=0,y=-1;
				 //if(temp>=0)
				 //System.out.println("temp2 :"+temp);
				  if(temp<0) temp=-temp;
				   y1=(-Math.sqrt(temp)-list.get(min_zero_l).y*temp2)/(2*(list.get(min_zero_l).y*list.get(min_zero_l).y+temp1*temp1));
				   y2=(Math.sqrt(temp)-list.get(min_zero_l).y*temp2)/(2*(list.get(min_zero_l).y*list.get(min_zero_l).y+temp1*temp1));
				 if(y1>=0&&Math.abs(list.get(min_zero_l).x+Math.sqrt(dis*dis-(list.get(min_zero_l).y-y1)*(list.get(min_zero_l).y-y1))-Math.sqrt(this.radius*this.radius-y1*y1)-rightmost)<=0.001){
				   y=y1;
				 }
				 if(y2>=0&&Math.abs(list.get(min_zero_l).x+Math.sqrt(dis*dis-(list.get(min_zero_l).y-y2)*(list.get(min_zero_l).y-y2))-Math.sqrt(this.radius*this.radius-y2*y2)-rightmost)<=0.001){
				   y=y2; 
				 }
				 if(y!=-1){
					 double x=list.get(min_zero_l).x+Math.sqrt(dis*dis-(list.get(min_zero_l).y-y)*(list.get(min_zero_l).y-y));
					 list.get(min_zero_l).setXFinal(x);
					 list.get(min_zero_l).setYFinal(y);
					 criticalSet.add(list.get(min_zero_l));
					 rightmost=x+Math.sqrt(this.radius*this.radius-y*y);
					/* double n=x-Math.sqrt(this.radius*this.radius-y*y);
					 System.out.println("set2:"+rightmost+" x-r:"+n+" y:"+y);*/
				 }
			 }else{
				 double min=Double.MAX_VALUE;
				 int index=0;
				 //int number=0;
				 for(int i=0;i<list.size();i++){
					 if(!criticalSet.contains(list.get(i))&&dis>=list.get(i).y){
						 //number++;
						 double zero=Math.sqrt(dis*dis-list.get(i).y*list.get(i).y);
						 double max_right=Math.sqrt((dis+this.radius)*(dis+this.radius)-list.get(i).y*list.get(i).y);
						 //
						/* double t1=list.get(i).x-zero-this.radius;
						 double t2=list.get(i).x+zero-this.radius;
						 System.out.println("set3:["+t1+","+t2+"],while r="+rightmost+" ("+list.get(i).x+","+list.get(i).y+")"+" dis: "+dis);*/
						 if(list.get(i).x-zero-this.radius<=rightmost&&list.get(i).x+zero-this.radius>=rightmost){
							// if(!criticalSet.contains(list.get(i))){
							      set3.add(list.get(i));
								  if(min>list.get(i).x+max_right){
									  min=list.get(i).x+max_right;
									  //minleftX=max_right_x;
									  index=i;
								  }
							 // } 
						 }
					 }
				 }
				// System.out.println("number-----:"+number);
				if(set3.size()!=0){
					 double x=rightmost+this.radius;
					 list.get(index).setXFinal(x);
					 list.get(index).setYFinal(0);
					 criticalSet.add(list.get(index));
					 rightmost=x+this.radius;
				}else{
					 double min_l=Double.MAX_VALUE;
					 int index_l=0;
					 for(int i=0;i<list.size();i++){
						 if(!criticalSet.contains(list.get(i))&&dis>=list.get(i).y){
							 double zero=Math.sqrt(dis*dis-list.get(i).y*list.get(i).y);
							 double temp_y=list.get(i).y;
							 double max_right=Math.sqrt((dis+this.radius)*(dis+this.radius)-temp_y*temp_y); 
							/* double t1=list.get(i).x-max_right;
							 double t2=list.get(i).x-zero;
							 System.out.println("set4_1:["+t1+","+t2+"],while r="+rightmost+":("+list.get(i).x+","+list.get(i).y+")"+" dis"+dis);*/
							 if(list.get(i).x-max_right<=rightmost&&list.get(i).x-zero-this.radius>rightmost){
								 /*double t1=list.get(i).x-max_right;
								 double t2=list.get(i).x-zero;
								 System.out.println("set4_1:["+t1+","+t2+"],while r="+rightmost+":("+list.get(i).x+","+list.get(i).y+")"+" dis: "+dis);*/
								 //if(!criticalSet.contains(list.get(i))){
								      set4.add(list.get(i));
									  if(min_l>list.get(i).x+max_right){
										  min_l=list.get(i).x+max_right;
										  //minleftX=max_right_x;
										  index_l=i;
									  }
								 // } 
							 }
						 }
						 if(!criticalSet.contains(list.get(i))&&dis+this.radius>=list.get(i).y&&dis<list.get(i).y){
							 //double zero=Math.sqrt(dis*dis-list.get(i).y*list.get(i).y);
							 double temp_y=list.get(i).y;
							 double max_right=Math.sqrt((dis+this.radius)*(dis+this.radius)-temp_y*temp_y);
							 double y=list.get(i).y-dis;
							 /*double t1=list.get(i).x-max_right;
							 double t2=list.get(i).x;
							 System.out.println("set4_2:["+t1+","+t2+"],while r="+rightmost+":("+list.get(i).x+","+list.get(i).y+")"+" dis"+dis);*/
							 if(list.get(i).x-max_right<=rightmost&&list.get(i).x-Math.sqrt(this.radius*this.radius-y*y)>rightmost){
								/*double t1=list.get(i).x-max_right;
								 double t2=list.get(i).x;
								 System.out.println("set4_2:["+t1+","+t2+"],while r="+rightmost+":("+list.get(i).x+","+list.get(i).y+")"+" dis: "+dis);*/
								 //if(!criticalSet.contains(list.get(i))){
								      set4.add(list.get(i));
									  if(min_l>list.get(i).x+max_right){
										  min_l=list.get(i).x+max_right;
										  //minleftX=max_right_x;
										  index_l=i;
									  }
								 // } 
							 }
						 }
					 }
					if(set4.size()!=0){
						 double temp1=list.get(index_l).x-rightmost;
						 //double temp2=this.radius*this.radius-temp1*temp1-dis*dis+list.get(min_zero_l).y*list.get(min_zero_l).y;
						 double temp2=this.radius*this.radius+temp1*temp1-dis*dis+list.get(index_l).y*list.get(index_l).y;
						 double temp=temp2*temp2*list.get(index_l).y*list.get(index_l).y-(temp2*temp2-4*temp1*temp1*this.radius*this.radius)*(list.get(index_l).y*list.get(index_l).y+temp1*temp1);
						 double y=-1,y1=0,y2=0;
						 //if(temp>=0)
						 //System.out.println("temp4 :"+temp);
						   y1=(list.get(index_l).y*temp2-Math.sqrt(temp))/(2*(list.get(index_l).y*list.get(index_l).y+temp1*temp1));
						   y2=(list.get(index_l).y*temp2+Math.sqrt(temp))/(2*(list.get(index_l).y*list.get(index_l).y+temp1*temp1));
						 if(y1>=0&&Math.abs(list.get(index_l).x-Math.sqrt(dis*dis-(list.get(index_l).y-y1)*(list.get(index_l).y-y1))-Math.sqrt(this.radius*this.radius-y1*y1)-rightmost)<=0.001){
							   y=y1;
						}
						 if(y2>=0&&Math.abs(list.get(index_l).x-Math.sqrt(dis*dis-(list.get(index_l).y-y2)*(list.get(index_l).y-y2))-Math.sqrt(this.radius*this.radius-y2*y2)-rightmost)<=0.001){
							   y=y2; 
						}
						 if(y!=-1){
							 double x=list.get(index_l).x-Math.sqrt(dis*dis-(list.get(index_l).y-y)*(list.get(index_l).y-y));
							 list.get(index_l).setXFinal(x);
							 list.get(index_l).setYFinal(y);
							 criticalSet.add(list.get(index_l));
							 rightmost=x+Math.sqrt(this.radius*this.radius-y*y);
							 /*double n=x+Math.sqrt(this.radius*this.radius-y*y);
							 System.out.println("set4:"+rightmost+" x-r:"+n);*/
						 }
					}else{
						return false;
					}
				} 
			 }
		 }
	  }
	/* //paint
	 		DrawImage di = new DrawImage("not on the line",list);
	 		di.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	 		di.setSize(1200, 400);  
	 		di.setLocationRelativeTo(null); 
	 		di.setVisible(true);*/
	   //System.out.println("----------------------------------");
	  return true;
   }

   private List<Double> getDis1(List<SensorNode> list){
	   List<Double> dis1= new ArrayList<Double>();
	   //sort y
	   /*Collections.sort(list,new Comparator<SensorNode>(){
		public int compare(SensorNode o1, SensorNode o2) {
			// TODO Auto-generated method stub
			return new Double(o1.y).compareTo(new Double(o2.y));
		}  
	   });*/
	   dis1.add((double) 0);
	   //dis1.add(list.get(0).y-this.radius);
	   //dis1.add(list.get(list.size()-1).y-this.radius);
	   //others 
	  /* int m=(int) Math.ceil(this.barrier_length/(2*this.radius));
	   int leastNumber=list.size()/m*m;
	   double distance =1;
	   List<SensorNode> nodeSet = new ArrayList<SensorNode>();
		for(int j=0;j<leastNumber;j++) //1--m is 0--(m-1)
			nodeSet.add(list.get(j));
		while(!getFeasibleSolution(distance,nodeSet))
			distance++;*/
	   double max=0;
	   for(int i=0;i<list.size();i++){
		   double temp1=Math.hypot(list.get(i).x,list.get(i).y);
		   double temp2=Math.hypot(list.get(i).x-this.barrier_length,list.get(i).y);
		   if(temp1>=temp2&&max<temp1) max=temp1;
		   if(temp2>temp1&&max<temp2) max=temp2;
	   }
		dis1.add(max);
		/*System.out.println("dis1:");
		for(double s:dis1)
			System.out.println(s);*/
	   return dis1;
   }

   private double binary_search(List<Double> dis,List<SensorNode> list){
		//List<Double> resultArray= new ArrayList<Double>();
		/*Collections.sort(dis,new Comparator<Double>(){
           public int compare(Double arg0, Double arg1) {
               return new Double(arg0).compareTo(new Double(arg1));
           }
       });*/
		//sort with orders
		/*int low=0,high=dis.size()-1;
		while(low!=high-1){
			//System.out.println("low:"+low+" high:"+high);
			int middle=(low+high)/2;
			boolean result=getFeasibleSolution(dis.get(middle),list);
			//System.out.println("---result:"+result);
			if(!result) low=middle;
			else high=middle;
		}*/
		//sort with real numbers
		/*double lowValue=dis.get(low),highValue=dis.get(high),middleValue=0.;
		while(highValue-lowValue>=0.001){
			//System.out.println("lowValue:"+lowValue+" highValue:"+highValue);
			middleValue=(lowValue+highValue)/2;
			boolean result=getFeasibleSolution(middleValue,list);
			if(!result) lowValue=middleValue;
			else highValue=middleValue;
		}*/
		double lowValue=0,highValue=dis.get(1),middleValue=0.;
		while(highValue-lowValue>=0.001){
			//System.out.println("lowValue:"+lowValue+" highValue:"+highValue);
			middleValue=(lowValue+highValue)/2;
			boolean result=getFeasibleSolution(middleValue,list);
			//System.out.println("shdjkdhskhfhjk");
			if(!result) lowValue=middleValue;
			else highValue=middleValue;
		}
		//if(lowValue==middleValue) System.out.println("final:"+getFeasibleSolution(highValue,list));
		return highValue;
		
	}

}
