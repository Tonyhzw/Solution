package used;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class DrawImage extends JFrame{
	public DrawImage(String name,double dis,List<SensorNode> list){
		setTitle(name);
		getContentPane().add(new DrawIa(dis,list));
	}
}

class DrawIa extends JPanel{
	private List<SensorNode> list;
	private double dis;
	public DrawIa(double dis,List<SensorNode> list){
		this.list=list;
		this.dis=dis;
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D d2;     
	    d2=(Graphics2D)g;
	    //old
	    //System.out.println(list.size());
	    d2.setColor(Color.RED);
		for(int i=0;i<list.size();i++){
			Arc2D  s= new Arc2D.Double(list.get(i).x+60-list.get(i).r,list.get(i).y+100-list.get(i).r,2*list.get(i).r,2*list.get(i).r,0,360,Arc2D.CHORD);
			d2.draw(s);
		}
		//barrier
		d2.setColor(Color.BLACK);
		Line2D line = new Line2D.Double(60,100,1060,100);
		d2.draw(line);
		line = new Line2D.Double(60,200,1060,200);
		d2.draw(line);
		//dis
		d2.setColor(Color.GRAY);
		Line2D line2 = new Line2D.Double(60,this.dis+100,1060,this.dis+100);
		d2.draw(line2);
		//new
		d2.setColor(Color.BLUE);
		for(int i=0;i<list.size();i++){
			if(list.get(i).x_final!=list.get(i).x&&list.get(i).y_final!=list.get(i).y){
			    Arc2D  s= new Arc2D.Double(list.get(i).x_final+60-list.get(i).r,list.get(i).y_final+100-list.get(i).r,2*list.get(i).r,2*list.get(i).r,0,360,Arc2D.CHORD);
			    d2.draw(s);
			    //draw move trace
			    Line2D line3 = new Line2D.Double(list.get(i).x+60,list.get(i).y+100,list.get(i).x_final+60,list.get(i).y_final+100);
				d2.draw(line3);
			}
		}
		//not move
		d2.setColor(Color.GREEN);
		for(int i=0;i<list.size();i++){
			if(list.get(i).x_final==list.get(i).x&&list.get(i).y_final==list.get(i).y){
				Arc2D  s= new Arc2D.Double(list.get(i).x_final+60-list.get(i).r,list.get(i).y_final+100-list.get(i).r,2*list.get(i).r,2*list.get(i).r,0,360,Arc2D.CHORD);
				d2.draw(s);
			}
	    }
	}
}