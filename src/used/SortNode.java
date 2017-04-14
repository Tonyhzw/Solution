package used;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class SortNode {
    private List<MyList> list=new ArrayList<MyList>();
	public void addNode(SensorNode node,double rightmost,double rightmostX){
	   list.add(new MyList(node,rightmost,rightmostX));
   }
	public void clearList(){
	   list.clear();
	}
	public List<MyList> sortList(boolean asc){
		if(asc){
			//increase
			Collections.sort(list,new Comparator<MyList>(){
	           public int compare(MyList arg0, MyList arg1) {
	               return new Double(arg0.rightmost).compareTo(new Double(arg1.rightmost));
	           }
		    });
		}else{
			Collections.sort(list,new Comparator<MyList>(){
	           public int compare(MyList arg0, MyList arg1) {
	               return new Double(arg1.rightmost).compareTo(new Double(arg0.rightmost));
	           }
			});
		}
		return list;
	}
	public int size(){
		return list.size();
	}
	public void print(String str){
		System.out.println(str);
	}
}
class MyList{
	public SensorNode node;
	public double rightmost;
	public double rightmostX;
	public MyList(SensorNode node2, double rightmost2, double rightmostX2) {
		// TODO Auto-generated constructor stub
		this.node=node2;
		this.rightmost=rightmost2;
		this.rightmostX=rightmostX2;
	}
}
