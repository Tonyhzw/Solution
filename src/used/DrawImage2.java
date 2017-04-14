package used;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class DrawImage2 extends JFrame{
	public DrawImage2(String name,double dis,List<SensorNode1> list){
		setTitle(name);
		getContentPane().add(new DrawIa2(dis,list));
	}
	
}

class DrawIa2 extends JPanel{
	private List<SensorNode1> list;
	private double dis;
	public DrawIa2(double dis,List<SensorNode1> list){
		this.list=list;
		this.dis=dis;
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D d2;     
	    d2=(Graphics2D)g;
	    //old
	    System.out.println(list.size());
	    d2.setColor(Color.RED);
		for(int i=0;i<list.size();i++){
			Arc2D  s= new Arc2D.Double(list.get(i).x+60-list.get(i).r,list.get(i).y-list.get(i).r,2*list.get(i).r,2*list.get(i).r,0,360,Arc2D.CHORD);
			d2.draw(s);
		}
		//barrier
		d2.setColor(Color.BLACK);
		for(int i=0;i<=800;i+=100){
			Line2D line = new Line2D.Double(60,i,1060,i);
			d2.draw(line);
		}
//		d2.setColor(Color.YELLOW);
//		Line2D line = new Line2D.Double(60,,1060,100);
//		d2.draw(line);
		
		d2.setColor(Color.GREEN);
		Line2D line2 = new Line2D.Double(60,dis,1060,dis);
		d2.draw(line2);
	}
}