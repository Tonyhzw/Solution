package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import used.SensorNode1;
import used.UnLine2;

/**
 * 实现模拟退火算法
 */
public class SATest {
    public static final int T = 100;// 初始化温度
    public static final double Tmin = 1e-5;// 温度的下界
    public static final int k = 100;// 迭代的次数
    public static final double delta = 0.98;// 温度的下降率
    
	
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
    public static double getX(double square_y) {
        //[0,square_y]
    	return Math.random()*square_y;
    }
    
    /**
     * 求得函数的值
     * 
     * @param x目标函数中的一个参数
     * @param y目标函数中的另一个参数
     * @return函数值
     */
    public static double getFuncResult(double barrier_y,List<SensorNode1> nodeList2,double nodeNumber,double radius,double barrier_length,double ac) {
    		//System.out.println("in");
    	    List<SensorNode1> nodeList3 = new ArrayList<SensorNode1>();
    		boolean down=false;
		    for(int i=0;i<nodeNumber;i++){
		    	 double x1=nodeList2.get(i).x;
		    	 double y1=nodeList2.get(i).y;
		    	 if(y1<barrier_y){
		    		    //negative_y+=(barrier_y-y1)*(barrier_y-y1);
				    	double dif=barrier_y-y1;
				    	y1+=2*dif;
				    	down=true;
				 }else{
				        //positive_y+=(y-barrier_y)*(y-barrier_y);
				 }
				 y1-=barrier_y;
		    	 nodeList3.add(new SensorNode1(x1,y1,radius,down));
		    }
		  UnLine2 algo3 = new UnLine2(barrier_length,radius,nodeList3,ac);
		  DecimalFormat df = new DecimalFormat("#.00");
		  df.setRoundingMode(RoundingMode.HALF_UP);
		  double value = Double.parseDouble(df.format(algo3.getMinMax()));
  		  //System.out.println("prepare to out。。。");
		  return value;
    }

    /**
     * 模拟退火算法的过程
     * @param y目标函数中的一个参数
     * @return最优解
     */
    public static double getSA(double square_y,List<SensorNode1> nodeList2,double nodeNumber,double radius,double barrier_length,double ac) {
        //double result = Double.MAX_VALUE;// 初始化最终的结果
        double t = T;
        double x[] = new double[k];
        Map<Double,Double> map =  new HashMap<Double,Double>();
        double result = getFuncResult(0,nodeList2,nodeNumber,radius,barrier_length,ac);
        System.out.println("0:  "+result);
        map.put((double) 0,result);
        double min=result,minY=0;
        x[0] = 0;
        // 初始化初始解
        for (int i = 1; i < k; i++) {
            x[i] = getX(square_y);
        }
        //System.out.println("初始化成功！");
        // 迭代的过程
        while (t > Tmin) {
            //System.out.println("当前温度为："+t);
            for (int i = 0; i < k; i++) {
                // 计算此时的函数结果
                //System.out.print("第"+i+"次迭代..");
                double funTmp=0;
                if(!map.containsKey(x[i])){
                	funTmp = getFuncResult(x[i],nodeList2,nodeNumber,radius,barrier_length,ac);
                    map.put(x[i], funTmp);
                }else{
                	funTmp = map.get(x[i]);
                }
                //System.out.println("  当前最小值为:"+funTmp);
                // 在邻域内产生新的解
                double x_new = x[i] + (Math.random() * 2 - 1) * t;
                // 判断新的x不能超出界
                if (x_new >= 0 && x_new <= square_y) {
                    double funTmp_new =0;
                    if(!map.containsKey(x_new)){
                    	funTmp_new = getFuncResult(x_new,nodeList2,nodeNumber,radius,barrier_length,ac); 
                    	map.put(x_new, funTmp_new);
                    }else{
                    	funTmp_new =  map.get(x_new);
                    }
                    min=Math.min(min, Math.min(funTmp, funTmp_new));
                    //minY
                    if (funTmp_new - funTmp < 0) {
                        // 替换
                        x[i] = x_new;
                    } else {
                        // 以概率替换
                        double p = 1 / (1 + Math
                                .exp(-(funTmp_new - funTmp) / T));
                        if (Math.random() < p) {
                            x[i] = x_new;
                        }
                    }
                }else{
                	min = Math.min(min, funTmp);
                }
            }
            t = t * delta;
        }
//        for (int i = 0; i < k; i++) {
//            result = Math.min(result, map.get(x[i]));
//        }
        return min;
    }

    public static void main(String args[]) {
    	double square_y = Config.square_y;
        int nodeNumber = Config.nodeNumber;
        int radius = Config.temp_r;
        double barrier_length = Config.barrier_length;
        double ac =  Config.ac;
  	    List<SensorNode1> nodeList2 = readTxtToNode(nodeNumber);
        System.out.println("最优解为：" + getSA(square_y,nodeList2,nodeNumber,radius,barrier_length,ac));
        UnLine2 algo2 = new UnLine2(barrier_length,radius,nodeList2,ac);
	    System.out.println("最优解为:"+algo2.getMinMax());
    }

}
