package used;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;
/*
 * ×îÖÕ³ÌÐò
 * */

public class OnLine2 {
    //for algorithm for the paper
	private List<SensorNode> nodeList = new ArrayList<SensorNode>();
	//private List<SensorNode> mobileList = new ArrayList<SensorNode>();//store feasible node
	private double barrier_length=0; //barrier from[0,0]--[barrier_length,0]
	private double optimalMovement=0;
	private int nodeNumber=0;
	private double radius=0;
	
	public OnLine2(List<SensorNode> list,double length,double r){
		this.nodeList = list;
		this.barrier_length = length;
		this.nodeNumber=this.nodeList.size();
		this.radius=r;
		/*for(SensorNode c:list)
			System.out.println("("+c.x+","+c.y+") ");*/
	}
	
	//the output results
	public double getMinMaxDistance(){
		List<Double> dis1= getDistance1(this.nodeList);
		//sort then use binary search to get a possible distance
		List<Double> result1= binary_search(dis1,this.nodeList);
		if(result1.size()==1){
			//System.out.print("  result1: "+result1.get(0));
			return result1.get(0);
		}
		else{
		//System.out.println("first:"+result1.get(0)+" "+result1.get(1));
		   List<Double> dis2=getDistance2(result1.get(0),result1.get(1),this.nodeList);
		   List<Double> result2=binary_search(dis2,this.nodeList);
		   if(result2.size()==1) {
			   //System.out.print("  result2_1:"+result2.get(0));
			   return result2.get(0);
			   }
			else{
				List<List<Integer>> array_A=new ArrayList<List<Integer>>();
				//System.out.println("Second:"+result2.get(0)+" "+result2.get(1));
				//computing the sensors,0 and n+1 are specail ,others are needed to minus one 1 ~ n
				double dis_temp=(result2.get(0)+result2.get(1))/2;
				//update every sensor with the movement of the dis_temp
				for(int i=0;i<this.nodeList.size();i++){
					this.nodeList.get(i).setInitalization();
					if(dis_temp>=this.nodeList.get(i).y){
						double movement=Math.sqrt(dis_temp*dis_temp-this.nodeList.get(i).y*this.nodeList.get(i).y);
						this.nodeList.get(i).setMovement(movement);
					}else{
						this.nodeList.get(i).movement=0;//do not move
					}
				}
				for(int i=0;i<this.nodeList.size()+2;i++){
					List<Integer> A=new ArrayList<Integer>();
					for(int z=0;z<this.nodeList.size()+2;z++)
						A.add(0);
					for(int j=i;j<this.nodeList.size()+2;j++){
						int num=0;
						//for(int z=0;z<this.nodeList.size();z++){
						//	double x_r=this.nodeList.get(z).x+this.nodeList.get(z).movement;
						//	double x_l=this.nodeList.get(z).x-this.nodeList.get(z).movement;
							if(i==0){//left endpoint is 0
								if(j==0){
									num=0;
									for(int z=0;z<this.nodeList.size();z++){
								       double x_r=this.nodeList.get(z).x+this.nodeList.get(z).movement;
									   double x_l=this.nodeList.get(z).x-this.nodeList.get(z).movement;
									   if(x_r>=0&&x_l<=0)
										   num++;
									}
									A.set(0, num);
								}else if(j==this.nodeList.size()+1){
									num=0;
									for(int z=0;z<this.nodeList.size();z++){
									       double x_r=this.nodeList.get(z).x+this.nodeList.get(z).movement;
										   double x_l=this.nodeList.get(z).x-this.nodeList.get(z).movement;
										   if(x_r>=0&&x_l<=this.barrier_length)
												num++;
										}
									A.set(this.nodeList.size()+1,num);
								}else{
									num=0;
									for(int z=0;z<this.nodeList.size();z++){
									       double x_r=this.nodeList.get(z).x+this.nodeList.get(z).movement;
										   double x_l=this.nodeList.get(z).x-this.nodeList.get(z).movement;
										   if(x_r>=0&&x_l<=this.nodeList.get(j-1).x+this.nodeList.get(j-1).movement)
												num++;   
								    }
									A.set(j,num);
								}
								//array_A.add(A);
							}
							else if(i!=this.nodeList.size()+1){ //right endpoint is L
								if(j==0){
									num=0;
									for(int z=0;z<this.nodeList.size();z++){
								       double x_r=this.nodeList.get(z).x+this.nodeList.get(z).movement;
									   double x_l=this.nodeList.get(z).x-this.nodeList.get(z).movement;
									   if(x_r>=this.nodeList.get(i-1).x-this.nodeList.get(i-1).movement&&x_l<=0)
										   num++;
									}
									 A.set(0, num);
								}else if(j==this.nodeList.size()+1){
									num=0;
									for(int z=0;z<this.nodeList.size();z++){
									       double x_r=this.nodeList.get(z).x+this.nodeList.get(z).movement;
										   double x_l=this.nodeList.get(z).x-this.nodeList.get(z).movement;
										   if(x_r>=this.nodeList.get(i-1).x-this.nodeList.get(i-1).movement&&x_l<=this.barrier_length)
												num++;
										}
									A.set(this.nodeList.size()+1,num);
								}else{
									num=0;
									for(int z=0;z<this.nodeList.size();z++){
									       double x_r=this.nodeList.get(z).x+this.nodeList.get(z).movement;
										   double x_l=this.nodeList.get(z).x-this.nodeList.get(z).movement;
										   if(x_r>=this.nodeList.get(i-1).x-this.nodeList.get(i-1).movement&&x_l<=this.nodeList.get(j-1).x+this.nodeList.get(j-1).movement)
												num++;
								    }
									A.set(j,num);
								}
								//array_A.add(A);
							}else{
								if(j==0){
									num=0;
									for(int z=0;z<this.nodeList.size();z++){
								       double x_r=this.nodeList.get(z).x+this.nodeList.get(z).movement;
									   double x_l=this.nodeList.get(z).x-this.nodeList.get(z).movement;
									   if(x_r>=this.barrier_length&&x_l<=0)
										   num++;
									}
									A.set(0, num);
								}else if(j==this.nodeList.size()+1){
									num=0;
									for(int z=0;z<this.nodeList.size();z++){
									       double x_r=this.nodeList.get(z).x+this.nodeList.get(z).movement;
										   double x_l=this.nodeList.get(z).x-this.nodeList.get(z).movement;
										   if(x_r>=this.barrier_length&&x_l<=this.barrier_length)
												num++;
										}
									A.set(this.nodeList.size()+1,num);
								}else{
									num=0;
									for(int z=0;z<this.nodeList.size();z++){
									       double x_r=this.nodeList.get(z).x+this.nodeList.get(z).movement;
										   double x_l=this.nodeList.get(z).x-this.nodeList.get(z).movement;
										   if(x_r>=this.barrier_length&&x_l<=this.nodeList.get(j-1).x+this.nodeList.get(j-1).movement)
												num++;
								    }
									 A.set(j,num);
								}
							}
						//}
					}
					/*System.out.println("A:***");
					for(int a: A)
						System.out.print(a+" ");
					System.out.println("*****");*/
					array_A.add(A);
				}
				//System.out.println("array_A_size:"+array_A.size()+" "+array_A.get(0).size()+" "+this.nodeList.size());
				for(int i=0;i<this.nodeList.size()+2;i++){
				   List<Integer> intList = array_A.get(i);
				   for(int j=0;j<i;j++){
					  //System.out.println(i+" "+j+" before:"+intList.get(j)+" "+array_A.get(j).get(i));
					  intList.set(j,array_A.get(j).get(i));
					  //System.out.println(i+" "+j+" after:"+intList.get(j)+" "+array_A.get(j).get(i));
				   }
				   
				   array_A.set(i, intList);
				}
				//System.out.println("array_A_after:"+array_A.get(0).get(0));
				//System.out.println("After:----");
				/*for(int a:array_A.get(1))
					System.out.print(a+" ");*/
				/*for(List<Integer> c: array_A){
					for(int a: c)
						System.out.print(a+" ");
					System.out.println();
				}*/
			    //System.out.println("---");
				List<Double> dis3=getDistance3(result2.get(0),result2.get(1),array_A,this.nodeList);
				/*System.out.println("dis3:---");
				for(double x:dis3)
				   System.out.print(x+"*");*/
				//System.out.println("---");
				if(dis3.size()!=0){
					List<Double> result3=binary_search(dis3,this.nodeList);
					   if(result3.size()==1){
						   //System.out.print("  result3_1:"+result3.get(0));
						   return result3.get(0);
					   }
					   else {
						   //System.out.print("  result3_2:"+result3.get(1));
						   feasibleDistance(result3.get(1),this.nodeList);
						   return result3.get(1);
					   }
				}else{
					//System.out.print("  result2_2:"+result2.get(1));
					return result2.get(1);
				}
			}
		}
		
	}
	 
	private List<Double> binary_search(List<Double> dis,List<SensorNode> list){
		List<Double> resultArray= new ArrayList<Double>();
		Collections.sort(dis,new Comparator<Double>(){
            public int compare(Double arg0, Double arg1) {
                return new Double(arg0).compareTo(new Double(arg1));
            }
        });
		int low=0,high=dis.size()-1;
		while(low!=high-1){
			int middle=(low+high)/2;
			int result=deterMinDis(dis.get(middle),list);
			if(result==0) {
				resultArray.add(dis.get(middle));
				break;
			}
			if(result==1)  high=middle;
			if(result==-1) low=middle;
		}
		if(low==high-1)  Collections.addAll(resultArray, dis.get(low),dis.get(high));
		return resultArray;
	}

	//algorithm for the feasibility distance
	public boolean feasibleDistance(double dis,List<SensorNode> list){
		int NF = 0;
		double rightmost=0;
		List<SensorNode> criticalSet = new ArrayList<SensorNode>();
		for(SensorNode c: list){
		   c.setInitalization();
		}
		/*List<SensorNode> nodeList = new ArrayList<SensorNode>();
		for(SensorNode c: node){
			  SensorNode sensor = new SensorNode(c.x,c.y,c.r);
			  nodeList.add(sensor);
		}*/
		List<SensorNode> set1 = new ArrayList<SensorNode>();
		List<SensorNode> set2 = new ArrayList<SensorNode>();
		//double[] d= new double[this.nodeNumber]; //store the largest movement
		double maxright=0,minright=Double.MAX_VALUE;
		int maxr=0,minr=0;
		//System.out.println("dis:"+dis);
		//int number=0;
		for(int j=0;j<list.size();j++){
			double y=list.get(j).y;
			if(y>dis) list.get(j).setMovement();
			else{
				//d[j]=Math.sqrt(dis*dis-y*y);
				list.get(j).setMovement(Math.sqrt(dis*dis-y*y));
				//number++;
				//System.out.print(" d:"+d[j]);
			}
		}
		//System.out.println("number-----:"+number);
		while(rightmost<this.barrier_length){
			//Initialize
			set1.clear();
			set2.clear();
			maxright=0;
			minright=Double.MAX_VALUE;
			// set1 for x+d-r<=rightmost<=x+d+r
			for(int j=0;j<list.size();j++){
				double x = list.get(j).x;
				//double r = this.nodeList.get(j).r;
				 double largestMovement = list.get(j).movement;
				/* double t1=x+largestMovement+this.radius;
				 double t2=x+largestMovement-this.radius;
				 System.out.println("set3:["+t1+","+t2+"],while r="+rightmost);*/
				if(largestMovement!=-1&&x+largestMovement+this.radius>rightmost&&x+largestMovement-this.radius<rightmost){
					if(!criticalSet.contains(list.get(j))){	
					   set1.add(list.get(j));
						if(maxright<x+largestMovement+this.radius){
							maxright=x+largestMovement+this.radius;
							maxr=j;
						}
						//System.out.println("maxr:"+maxr+ ++this.nodeList.get(maxr).x+" "+this.nodeList.get(maxr).y);
					}
				}
			}
			//delete the one which have already choose
			/*for(SensorNode c:criticalSet)
				if(set1.contains(c))
					set1.remove(c);*/
			if(set1.size()!=0){
				//select the largest  x+d+r when d[maxr] for maxright largest
				double x=list.get(maxr).x+list.get(maxr).movement;
				list.get(maxr).setXFinal(x);
				list.get(maxr).setYFinal(0);
				//double r=this.nodeList.get(maxr).r;
				//System.out.println("maxr:"+list.get(maxr).x+" "+list.get(maxr).y+" after:"+list.get(maxr).x_final+" "+list.get(maxr).y_final+" movement:"+list.get(maxr).movement);	
				//SensorNode node = new SensorNode(x,0,this.radius);
				criticalSet.add(list.get(maxr));
				//nodeList.remove(maxr);
				rightmost=x+this.radius;
				//System.out.println("rightmost  set1:"+rightmost+" x:"+x);
			}else{
				//set2 for x-d-r<=rightmost<=x+d-r
				for(int j=0;j<list.size();j++){
					double x = list.get(j).x;
					double largestMovement = list.get(j).movement;
					//double r = this.nodeList.get(j).r; 
					/* double t1=x+largestMovement-this.radius;
					 double t2=x-largestMovement-this.radius;
					 System.out.println("set3:["+t1+","+t2+"],while r="+rightmost);*/
					if(largestMovement!=-1&&x+largestMovement-this.radius >=rightmost&&x-largestMovement-this.radius <=rightmost){
						if(!criticalSet.contains(list.get(j))){
							set2.add(list.get(j));
							if(minright>x+largestMovement+this.radius ){
								minright=x+largestMovement+this.radius;
								minr=j;
								//System.out.println("minr:"+minr+" "+this.nodeList.get(minr).x+" "+this.nodeList.get(minr).y);
							}
					    }
					}
				}
				//delete the one which have already choose
			/*	for(SensorNode c:criticalSet)
					if(set2.contains(c))
						set2.remove(c);*/
				if(set2.size()!=0){
					//double r=this.nodeList.get(minr).r;
					double x=rightmost+this.radius;
					list.get(minr).setXFinal(x);
					list.get(minr).setYFinal(0);
					//SensorNode node = new SensorNode(x,0,this.radius);
					criticalSet.add(list.get(minr));
					//System.out.println("minr:"+list.get(minr).x+" "+list.get(minr).y+" after:"+list.get(maxr).x_final+" "+list.get(maxr).y_final+" movement:"+list.get(maxr).movement);
					//nodeList.remove(minr);
					rightmost=x+this.radius;
					//System.out.println("rightmost   set2:"+rightmost+" x:"+x);
				}else{
					NF=1;
					break;
				}
			}
		}	
		//System.out.println("**********");
		if(NF == 1) return false;
		else return true;
	}
	
	private List<Double> getDistance1(List<SensorNode> list){
		List<Double> disList= new ArrayList<Double>();
		int m=(int) Math.ceil(this.barrier_length/(2*this.radius));
		//sort by y-coordinates increasing, add the largest n-m+1
		Collections.sort(list,new Comparator<SensorNode>(){
            public int compare(SensorNode arg0, SensorNode arg1) {
                return new Double(arg0.y).compareTo(new Double(arg1.y));
            }
        });
		/*for(SensorNode c:list)
			System.out.println("("+c.x+","+c.y+") ");*/
		
		for(int j=m-1;j<list.size();j++){ //start from 1
			//System.out.println("distance: "+this.nodeList.get(j).y);
			disList.add(list.get(j).y);
		}
		/*for(int j=0;j<this.nodeList.size();j++)
			System.out.print(this.nodeList.get(j).y+" ");*/
		//choose the smallest set 1-m to cover, get the maximum distance
		double distance=list.get(list.size()-1).y+1;
		List<SensorNode> nodeSet = new ArrayList<SensorNode>();
		for(int j=0;j<m;j++) //1--m is 0--(m-1)
			nodeSet.add(list.get(j));
		while(!feasibleDistance(distance,nodeSet))
			distance++;
		disList.add(distance);
		//System.out.println("distance: "+distance);
		/*System.out.println("isCheck!");
		for(SensorNode c : this.nodeList)
		  System.out.println(c.x_final+" "+c.y_final);*/
		return disList;
	}
	
	//@return -1 for < , 0 for =, 1 for >=
   public int deterMinDis(double dis,List<SensorNode> list){
	   boolean result=feasibleDistance(dis,list);
	   if(result){
		   //smallest left endpoint of the moving range of the sensor is at x=r
		   double minl=Double.MAX_VALUE,maxr=0;
		   int indexl=0,indexr=0;
		   for(int j=0;j<list.size();j++){
		     if(minl>list.get(j).x-list.get(j).movement){
		    	 minl=list.get(j).x-list.get(j).movement;
		    	 indexl=j;
		     }
		     if(maxr<list.get(j).x+list.get(j).movement){
		    	 maxr=list.get(j).x+list.get(j).movement;
		    	 indexr=j;
		     }
		   }
		   if(list.get(indexl).x_final==this.radius) return 0;
		   else{
			   if(list.get(indexr).x_final==this.barrier_length-this.radius) return 0;
			   else{
				   for(int j=1;j<list.size();j++){
					   if(list.get(j).y_final==0&&list.get(j).x-list.get(j).movement==list.get(j).x_final){
						   int i=0;
						   for(i=j-1;i>=0;i--){
							   if(list.get(i).y_final==0) break;
						   }
						   if(list.get(j).x_final==list.get(i).x_final+2*this.radius&&list.get(j).x_final<=this.barrier_length-this.radius){
							   boolean flag=true;
							   for(int z=j+1;z<list.size();z++){
								  if(list.get(z).y_final==0){
									  if(list.get(j).x<list.get(z).x&&list.get(j).x-list.get(j).movement>list.get(z).x-list.get(z).movement){
										  flag=false;
										  break;
									  }
								  }
							  }
							  if(flag) return 0;
						   }
					   }
				   }
			   }
		   }
		   return 1;
	   }else{
		   return -1;
	   }
   }
   
   private List<Double> getDistance2(double arg1,double arg2,List<SensorNode> list){
	   List<Double> dis2= new ArrayList<Double>();    
	   Collections.addAll(dis2, arg1,arg2);
	   for(int i=0;i<list.size();i++){
		for(int j=i+1;j<list.size();j++){
			//exist critical movement
		    double x1=list.get(i).x;
		    double y1=list.get(i).y;
		    double x2=list.get(j).x;
		    double y2=list.get(j).y;
		    //double k1=Math.hypot(y1,((x2-x1)*(x2-x1)-(y1*y1-y2*y2))/(2*(x2-x1)));
		    //double k2=Math.hypot(y1, ((y1*y1-y2*y2)-(x1-x2)*(x1-x2))/(2*(x1-x2)));
		    //double k3=Math.hypot(y1,((x2-x1)*(x2-x1)-(y1*y1-y2*y2))/(2*(x1-x2)));
		    //double k4=Math.hypot(y1, ((y1*y1-y2*y2)-(x1-x2)*(x1-x2))/(2*(x2-x1)));
		    //if(k1>arg1&&k1<arg2) dis2.add(k1);
		    //if(k2>arg1&&k2<arg2) dis2.add(k2);
		    //if(k3>arg1&&k3<arg2) dis2.add(k3);
		    //if(k4>arg1&&k4<arg2) dis2.add(k4);
		    //lemma8 exists two kinds of distance
		    double k=((x2-x1)*(x2-x1)-(y1*y1-y2*y2))/(2*(x2-x1));
		    if(k>=0&&Math.hypot(y1, k)>arg1&&Math.hypot(y1, k)<arg2) dis2.add(Math.hypot(y1, k));
		    if(k<0&&Math.hypot(y1,-k)>arg1&&Math.hypot(y1,-k)<arg2) dis2.add(Math.hypot(y1,-k));
		}   
	   }
	   return dis2;
   }
   
   private List<Double> getDistance3(double arg1,double arg2,List<List<Integer>> arg3,List<SensorNode> list){
	   List<Double> dis3= new ArrayList<Double>();
	   dis3.add(arg1);
	   dis3.add(arg2);
	   int number=list.size()+2;//0~(m+1)
	   //System.out.println("ssssd1");
	   for(int i=0;i<list.size();i++){
		   //if(list.get(i).y_final==0){
		       //int index=i*number+list.size()+1;
			   //for(int j=0;j<=index;j++){
				   //int k=arg3.get(j/number).get(j%number);
		           int k1=arg3.get(i).get(list.size()+1);
				   for(int m=0;m<=k1;m++){
					   double temp=this.barrier_length-(2*m+1)*this.radius-list.get(i).x;
					   if(temp>0){
						   double feaDis=Math.hypot(list.get(i).y,temp);
						   if(feaDis>=arg1&&feaDis<=arg2)
							   dis3.add(feaDis);
					   }	  
				   }
			   //}
			  // System.out.println("ssssd2");
			   //for(int j=0;j<=i;j++){
				   int k2=arg3.get(0).get(i);
				   for(int m=0;m<=k2;m++){
					   double temp=list.get(i).x-(2*m+1)*this.radius;
					   /*if(temp>0&&Math.hypot(list.get(i).y,temp)>=arg1&&Math.hypot(list.get(i).y,temp)<=arg2) 
						   dis3.add(Math.hypot(list.get(i).y, temp));*/
					   if(temp>0){
						   double feaDis=Math.hypot(list.get(i).y,temp);
						   if(feaDis>=arg1&&feaDis<=arg2)
							   dis3.add(feaDis);
					   }
				   }
			   //}
			   
		   //}	   
	   }
	  // System.out.println("ssssd3");
	   for(int i=0;i<list.size()-1;i++)
		   for(int j=i+1;j<list.size();j++){
			   //int index=i*number+j;
			  // for(int z=0;z<=index;z++){
				   //int k=arg3.get(z/number).get(z%number);
				   int k3=arg3.get(i).get(j);
				   for(int m=0;m<=k3;m++){
					   double temp1=list.get(j).x-list.get(i).x-(2*m+1)*this.radius;
					   double temp=(list.get(j).y*list.get(j).y-list.get(i).y*list.get(i).y-temp1*temp1)/(2*temp1);
					   /*if(temp>0&&Math.hypot(list.get(i).y,temp)>=arg1&&Math.hypot(list.get(i).y,temp)<=arg2)
						   dis3.add(Math.hypot(list.get(j).y, temp));
					   if(temp<0&&Math.hypot(list.get(i).y,-temp)>=arg1&&Math.hypot(list.get(i).y,-temp)<=arg2)
						   dis3.add(Math.hypot(list.get(j).y, -temp));*/
					   if(temp>=0){
						   double feaDis=Math.hypot(list.get(j).y,temp);
						   if(feaDis>=arg1&&feaDis<=arg2)
							   dis3.add(feaDis); 
					   }else{
						   double feaDis=Math.hypot(list.get(j).y,-temp);
						   if(feaDis>=arg1&&feaDis<=arg2)
							   dis3.add(feaDis);
					   }
				   }
			   //}
		   }
	  // System.out.println("ssssd4");
	   return dis3;
   }
}
