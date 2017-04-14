package used;
public class SensorNode {
     
	public double x=0,x_final=0;
	public double y=0,y_final=0;
	public double r=0;
	public double movement=0;  //store the largest possible movement
	
	/**
	 * 
	 * @param arg1  for x coordination
	 * @param arg2  for y coordination
	 * @param arg3  for radius
	 */
	
	public SensorNode(double arg1,double arg2,double arg3){
		x=arg1;x_final=arg1;
		y=arg2;y_final=arg2;
		r=arg3;
	}
	public void setInitalization(){
		x_final=x;
		y_final=y;
		movement=0;
	}
	public void setMovement(double arg3){// x is not sure
		//y_final=arg2;
		movement=arg3;
	}
	
	public void setMovement(){ // do not move
		movement=-1;
	}
	public void setXFinal(double arg1){
		x_final=arg1;
	}
	public void setYFinal(double arg1){
		y_final=arg1;
	}
}
