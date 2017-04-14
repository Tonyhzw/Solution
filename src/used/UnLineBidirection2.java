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
    * 两侧有栅栏的情形
    ***/
public class UnLineBidirection2 {
   private List<SensorNode> nodeList = new ArrayList<SensorNode>();
   private double barrier_length=0,barrierUp=0,barrierDown=0,width=0;
   private double radius=0;
   private double accuracy=0;
   public UnLineBidirection2(double length,double width,double radius,List<SensorNode> list,double ac){
	   this.barrier_length=length;
	   this.width=width;
	   this.radius=radius;
	   this.nodeList=list;
	   this.accuracy=ac;
	   this.barrierUp=width;	   
   }
   public double getDistance(SensorNode node1,SensorNode node2){
	   return Math.sqrt((node1.x-node2.x)*(node1.x-node2.x)-(node1.y-node2.y)*(node1.y-node2.y));
   }
   public double getMinMax(){
	   double optimalValue=binary_search(getDis1(this.barrier_length,this.width),this.nodeList);
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
//	   for(int i=0;i<list.size();i++){
//		   list.get(i).setXFinal(list.get(i).x);
//		   list.get(i).setYFinal(list.get(i).y);
//	   }
	   
	   double rightmostUp=0,rightmostDown=0;
	   List<SensorNode> criticalSetUp = new ArrayList<SensorNode>(),criticalSetDown = new ArrayList<SensorNode>();
	   SortNode sortNode=new SortNode();
	   sortNode.print("trying。。。");
	   while(rightmostUp<this.barrier_length||rightmostDown<this.barrier_length){
		  sortNode.clearList();
		  //sortNode.print("rightmostDown:"+rightmostDown+" rightmostUp:"+rightmostUp);
		  int lineIndex=rightmostUp>rightmostDown?1:2;//下1上2
		  List<SensorNode> criticalSet = (lineIndex==1)?criticalSetDown:criticalSetUp;
		  List<SensorNode> criticalSetOther = (lineIndex==1)?criticalSetUp:criticalSetDown;
		  double rightmost= (lineIndex==1)?rightmostDown:rightmostUp;
		  double barrier_y=(lineIndex==1)?this.barrierDown:this.barrierUp;
          //sortNode.print("choose:"+rightmostDown+" "+rightmostUp+" -->"+lineIndex);
		  double maxrightX=0;
		  int max_r=0;
		  for(int i=0;i<list.size();i++){
			  double temp_y=list.get(i).y;
			  if(temp_y<barrier_y) temp_y=barrier_y-temp_y;//barrierUp
			  if(!criticalSet.contains(list.get(i))&&dis+this.radius>=temp_y){
				  double max_right=Math.sqrt((dis+this.radius)*(dis+this.radius)-temp_y*temp_y);
				  double max_right_y=this.radius*temp_y/(dis+this.radius);
				  double max_right_x=Math.sqrt(dis*dis-(temp_y-max_right_y)*(temp_y-max_right_y));
				  if(list.get(i).x+max_right-2*Math.sqrt(this.radius*this.radius-max_right_y*max_right_y)<=rightmost&&list.get(i).x+max_right>=rightmost){
					  sortNode.addNode(list.get(i), list.get(i).x+max_right, list.get(i).x+max_right_x);
				  }
			  }
		  }
		  if(sortNode.size()!=0){
			  List<MyList> descList=sortNode.sortList(false);
			  //for(int i=0;i<descList.size();i++){
			  int i=0;
				  SensorNode node=descList.get(i).node;
				  max_r=criticalSetOther.indexOf(node);
				  sortNode.print("max_r:"+max_r);
				  if(max_r==-1){
					  max_r=i;
					  double final_y=node.y;
					  if(final_y<barrier_y) final_y=barrier_y-final_y;
					  double x=descList.get(max_r).rightmostX;
					  double y=this.radius*final_y/(dis+this.radius);
					  if(lineIndex==2) y=barrier_y-y;
					  node.setXFinal(x);
					  node.setYFinal(y);
					  sortNode.print("case 1-->x:"+node.x+" y:"+node.y+" x_final:"+x+" y_final:"+y+" barrier_y:"+barrier_y+" abs:"+(dis*dis-(maxrightX-node.x)*(maxrightX-node.x)));
					  criticalSet.add(node);
					  if(lineIndex==1) rightmostDown=descList.get(max_r).rightmost;
					  else rightmostUp=descList.get(max_r).rightmost;
					  sortNode.print("stretch in 1 -- lineIndex:"+lineIndex+" barrier_y:"+barrier_y+" rightmostDown:"+rightmostDown+" rightmostUp:"+rightmostUp);
					  break;
				  }else{
					  //collision
					  sortNode.print("Collision in 1");
					  List<SensorNode> criticalSetOther2 = criticalSetOther.subList(0, max_r+1);
					  int down=0,up=0;
					  double downDis=Double.MAX_VALUE,upDis=Double.MAX_VALUE;
					  for(int z=0;z<list.size();z++){
						  SensorNode temp=list.get(z);
						  if(!criticalSet.contains(list.get(z))&&(!criticalSetOther2.contains(list.get(z)))){
							  double tempDis=getDistance(temp,node);
							  if(node.y>temp.y){
								  if(upDis>tempDis){
									  upDis=tempDis;
									  up=z;
								  }
							  }else{
								  if(downDis>tempDis){
									  downDis=tempDis;
									  down=z;
								  }
							  }
						  }
					  }
					  if(upDis<downDis){
						  if(lineIndex==1){
							  /*
							   * 上边距回滚到之前的max_r那里 
							   * 1. 清空上边距的max_r之后的criticalSet  2.两边都加入新选接点 
							   */
							  
						  }else{
							  //
						  }
					  }else{
						  if(lineIndex==2){
							  //下边界回滚到之前的max_r那里
						  }
					  }
				  }
			  //}
		  }else{
			  sortNode.clearList();
			  
			  int min_zero_l=0;
			  for(int i=0;i<list.size();i++){
				  double temp_y=list.get(i).y;
				  if(temp_y<barrier_y) temp_y=barrier_y-temp_y;
				  double max_right=Math.sqrt((dis+this.radius)*(dis+this.radius)-temp_y*temp_y);
				  double max_right_y=this.radius*temp_y/(dis+this.radius);
				  if(!criticalSet.contains(list.get(i))&&dis>=temp_y){
					  double right_zero_x_1=Math.sqrt(dis*dis-temp_y*temp_y);
					  if(list.get(i).x+right_zero_x_1-this.radius<rightmost&&list.get(i).x+max_right-2*Math.sqrt(this.radius*this.radius-max_right_y*max_right_y)>rightmost){
						 sortNode.addNode(list.get(i), list.get(i).x+max_right-2*Math.sqrt(this.radius*this.radius-max_right_y*max_right_y), (double)i);
					  }
				  }
				  if(!criticalSet.contains(list.get(i))&&dis+this.radius>=temp_y&&dis<temp_y){
				      double y=temp_y-dis;
					  if(list.get(i).x-Math.sqrt(this.radius*this.radius-y*y)<=rightmost&&list.get(i).x+max_right-2*Math.sqrt(this.radius*this.radius-max_right_y*max_right_y)>rightmost){
						 sortNode.addNode(list.get(i), list.get(i).x+max_right-2*Math.sqrt(this.radius*this.radius-max_right_y*max_right_y), (double)i); 
					  } 
				  }
			 }
			 if(sortNode.size()!=0){
				 List<MyList> ascList=sortNode.sortList(true);
				 for(int i=0;i<ascList.size();i++){
					 SensorNode node=ascList.get(i).node;
					 min_zero_l=criticalSetOther.indexOf(node);
					 sortNode.print("min_zero_l:"+min_zero_l);
					 if(min_zero_l==-1){
						 min_zero_l=i;
						 double final_y=node.y;
						 if(final_y<barrier_y) final_y=barrier_y-final_y;
						 double temp1=rightmost-node.x;
						 double temp2=dis*dis-this.radius*this.radius-temp1*temp1-final_y*final_y;
						 //约掉
						 double temp=temp2*temp2*final_y*final_y-(temp2*temp2-4*temp1*temp1*this.radius*this.radius)*(final_y*final_y+temp1*temp1);
						 double y1=0,y2=0,y=-1;
						 if(temp<0) {
							 temp=-temp;
						     sortNode.print("sqrt error 2");
						 }
						 y1=(-Math.sqrt(temp)-final_y*temp2)/(2*(final_y*final_y+temp1*temp1));
						 y2=(Math.sqrt(temp)-final_y*temp2)/(2*(final_y*final_y+temp1*temp1));
						 //sortNode.print("result:"+y1+" "+y2);
//						 if(y1>=0&&Math.abs(node.x+Math.sqrt(dis*dis-(final_y-y1)*(final_y-y1))-Math.sqrt(this.radius*this.radius-y1*y1)-rightmost)<=0.001){
//						   y=y1;
//						 }
//						 if(y2>=0&&Math.abs(node.x+Math.sqrt(dis*dis-(final_y-y2)*(final_y-y2))-Math.sqrt(this.radius*this.radius-y2*y2)-rightmost)<=0.001){
//						   y=y2; 
//						 }
//						 if(y!=-1){
							 if(lineIndex==1) y=y2;
							 else y=barrier_y-y2;
							 //double x=node.x+Math.sqrt(dis*dis-(final_y-y2)*(final_y-y2));
							 double x=rightmost+Math.sqrt(this.radius*this.radius-y2*y2);
							 node.setXFinal(x);
							 node.setYFinal(y);
							 sortNode.print("case 2-->x:"+node.x+" y:"+node.y+" x_final:"+x+" y_final:"+y+" barrier_y:"+barrier_y+" abs:"+(this.radius*this.radius-y2*y2));
							 criticalSet.add(node);
							 if(lineIndex==1) rightmostDown=x+Math.sqrt(this.radius*this.radius-y2*y2);
							 else rightmostUp=x+Math.sqrt(this.radius*this.radius-y2*y2);
							 sortNode.print("stretch in 2 -- lineIndex:"+lineIndex+" barrier_y:"+barrier_y+" rightmostDown:"+rightmostDown+" rightmostUp:"+rightmostUp);
							 break;
//						 }else{
//							 sortNode.print("error in 2");
//						 }
						 //break;
					 }else{
						 //collison
						  sortNode.print("Collision in 2");
					 }
				 }
			 }else{
				 sortNode.clearList();
				 
				 double min=Double.MAX_VALUE;
				 int index=0;
				 for(int i=0;i<list.size();i++){
					 double temp_y=list.get(i).y;
					 if(temp_y<barrier_y) temp_y=barrier_y-temp_y;
					 if(!criticalSet.contains(list.get(i))&&dis>=temp_y){
						double zero=Math.sqrt(dis*dis-temp_y*temp_y);
						if(list.get(i).x-zero-this.radius<=rightmost&&list.get(i).x+zero-this.radius>=rightmost){
						    sortNode.addNode(list.get(i), list.get(i).x+zero, (double)i);  
						 }
					 }
				 }
				if(sortNode.size()!=0){
					 List<MyList> ascList=sortNode.sortList(true);
					 for(int i=0;i<ascList.size();i++){
						 SensorNode node= ascList.get(i).node;
						 index=criticalSetOther.indexOf(node);
						 sortNode.print("index:"+index);
						 if(index==-1){
							 index=i;
							 double x=rightmost+this.radius;
							 node.setXFinal(x);
							 node.setYFinal(barrier_y);
							 sortNode.print("case 3-->x:"+node.x+" y:"+node.y+" x_final:"+x+" y_final:"+barrier_y+" barrier_y:"+barrier_y);
							 criticalSet.add(node);
							 if(lineIndex==1) rightmostDown=x+this.radius;
							 else rightmostUp=x+this.radius;
							 sortNode.print("stretch in 3 -- lineIndex:"+lineIndex+" barrier_y:"+barrier_y+" rightmostDown:"+rightmostDown+" rightmostUp:"+rightmostUp);
							 break;
						 }else{
							 //collison
							  sortNode.print("Collision in 3");
						 }
					 }
					 
				}else{
					 sortNode.clearList();
					 
					 int index_l=0;
					 for(int i=0;i<list.size();i++){
						 double temp_y=list.get(i).y;
						 if(temp_y<barrier_y) temp_y=barrier_y-temp_y;
						 if(!criticalSet.contains(list.get(i))&&dis>=temp_y){
							double zero=Math.sqrt(dis*dis-temp_y*temp_y);
							double max_right=Math.sqrt((dis+this.radius)*(dis+this.radius)-temp_y*temp_y); 
							if(list.get(i).x-max_right<=rightmost&&list.get(i).x-zero-this.radius>rightmost){
							   sortNode.addNode(list.get(i), list.get(i).x-zero-this.radius, (double)i);
							}
						 }
						 if(!criticalSet.contains(list.get(i))&&dis+this.radius>=temp_y&&dis<temp_y){
							double max_right=Math.sqrt((dis+this.radius)*(dis+this.radius)-temp_y*temp_y);
							double y=temp_y-dis;
							if(list.get(i).x-max_right<=rightmost&&list.get(i).x-Math.sqrt(this.radius*this.radius-y*y)>rightmost){
								sortNode.addNode(list.get(i), list.get(i).x-Math.sqrt(this.radius*this.radius-y*y), (double)i);
							 }
						 }
					 }
					if(sortNode.size()!=0){
						 List<MyList> ascList=sortNode.sortList(true);
						 for(int i=0;i<ascList.size();i++){
							 SensorNode node= ascList.get(i).node;
							 index_l=criticalSetOther.indexOf(node);
							 sortNode.print("index_l:"+index_l);
							 if(index_l==-1){
								 index_l=i;
								 double final_y=node.y;
								 if(final_y<barrier_y) final_y=barrier_y-final_y;
								 double temp1=node.x-rightmost;
								 double temp2=this.radius*this.radius+temp1*temp1-dis*dis+final_y*final_y;
								 double temp=temp2*temp2*final_y*final_y-(temp2*temp2-4*temp1*temp1*this.radius*this.radius)*(final_y*final_y+temp1*temp1);
								 double y=-1,y1=0,y2=0;
								 y1=(final_y*temp2-Math.sqrt(temp))/(2*(final_y*final_y+temp1*temp1));
								 y2=(final_y*temp2+Math.sqrt(temp))/(2*(final_y*final_y+temp1*temp1));
//								 if(y1>=0&&Math.abs(node.x-Math.sqrt(dis*dis-(final_y-y1)*(final_y-y1))-Math.sqrt(this.radius*this.radius-y1*y1)-rightmost)<=0.001){
//									   y=y1;
//								 }
//								 if(y2>=0&&Math.abs(node.x-Math.sqrt(dis*dis-(final_y-y2)*(final_y-y2))-Math.sqrt(this.radius*this.radius-y2*y2)-rightmost)<=0.001){
//									   y=y2; 
//								 }
								 //if(y!=-1){
								 double x=0;
								 	 if(y1>=0){
								 		 if(lineIndex==1) y=y1;
								 		 else y=barrier_y-y1;
									     x=node.x-Math.sqrt(dis*dis-(final_y-y1)*(final_y-y1));
								 		 if(lineIndex==1) rightmostDown=x+Math.sqrt(this.radius*this.radius-y1*y1);
										 else rightmostUp=x+Math.sqrt(this.radius*this.radius-y1*y1);
								 	 }
								 	 else{
								 		 if(lineIndex==1) y=y2;
								 		 else y=barrier_y-y2;
								 		 x=node.x-Math.sqrt(dis*dis-(final_y-y2)*(final_y-y2));
								 		 if(lineIndex==1) rightmostDown=x+Math.sqrt(this.radius*this.radius-y2*y2);
										 else rightmostUp=x+Math.sqrt(this.radius*this.radius-y2*y2);
								 	 }
									 node.setXFinal(x);
									 node.setYFinal(y);
									 sortNode.print("case 4-->x:"+node.x+" y:"+node.y+" x_final:"+x+" y_final:"+y+" barrier_y:"+barrier_y);
									 criticalSet.add(node);
									
									 sortNode.print("stretch in 4 -- lineIndex:"+lineIndex+" barrier_y:"+barrier_y+" rightmostDown:"+rightmostDown+" rightmostUp:"+rightmostUp);
									 break;
//								 }else{
//									 sortNode.print("error in 4");
//								 }
								 //break;
							 }else{
								 //collision
								  sortNode.print("Collision in 4");
							 }
						 }
					}else{
						sortNode.print("failed");
						return false;
					}
				} 
			 }
		 }
	   }
	 //paint
//	 		DrawImage di = new DrawImage("not on the line",dis,list);
//	 		di.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
//	 		di.setSize(1200, 400);  
//	 		di.setLocationRelativeTo(null); 
//	 		di.setVisible(true);
	   //System.out.println("----------------------------------");
//	   System.out.println("************************************");
//	   sortNode.print("rightmostDown:"+rightmostDown+" rightmostUp:"+rightmostUp);
	   for(int i=0;i<list.size();i++){
			if(list.get(i).x_final-10<=0){
				System.out.println("inside x:"+list.get(i).x+" y:"+list.get(i).y+" x_final:"+list.get(i).x_final+" y_final:"+list.get(i).y_final);
			}
		}
	   System.out.println("----------------------------------");
//	   criticalSetUp.addAll(criticalSetDown);
//	   for(int i=0;i<criticalSetUp.size();i++){
//			if(criticalSetUp.get(i).x_final-10<=0){
//				System.out.println("inside2 x:"+criticalSetUp.get(i).x+" y:"+criticalSetUp.get(i).y+" x_final:"+criticalSetUp.get(i).x_final+" y_final:"+criticalSetUp.get(i).y_final);
//			}
//		}
//	   System.out.println("************************************");
	  return true;
   }
   //min :0     max: sqrt(w*w+l*l)
   private List<Double> getDis1(double length, double width ){
	   List<Double> dis1= new ArrayList<Double>();
	   dis1.add((double) 0);
	   dis1.add(Math.sqrt(length*length+width*width));
		/*System.out.println("dis1:");
		for(double s:dis1)
			System.out.println(s);*/
	   return dis1;
   }

   private double binary_search(List<Double> dis,List<SensorNode> list){
		double lowValue=0,highValue=dis.get(1),middleValue=0;
		while(highValue-lowValue>=this.accuracy){
			//System.out.println("lowValue:"+lowValue+" highValue:"+highValue);
			middleValue=(lowValue+highValue)/2;
			boolean result=getFeasibleSolution(middleValue,list);
			//System.out.println("shdjkdhskhfhjk");
			if(!result) lowValue=middleValue;
			else highValue=middleValue;
		}
		//for paint
		if(lowValue==middleValue) getFeasibleSolution(highValue,list);
		return highValue;
		
	}

}
