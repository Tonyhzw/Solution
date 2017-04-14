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
 * ʵ��ģ���˻��㷨
 */
public class SATest {
    public static final int T = 100;// ��ʼ���¶�
    public static final double Tmin = 1e-5;// �¶ȵ��½�
    public static final int k = 100;// �����Ĵ���
    public static final double delta = 0.98;// �¶ȵ��½���
    
	
	public static List<SensorNode1> readTxtToNode(int number){
		File directory = new File("src/data/data.txt"); 
		String path=directory.getAbsolutePath();
		List<SensorNode1> nodeList2 = new ArrayList<SensorNode1>();
		try {  
            File f = new File(path);  
            if (f.exists()) {  
                System.out.print("�ļ�����");  
            } else {  
                System.out.print("�ļ�������");  
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
     * ��ú�����ֵ
     * 
     * @param xĿ�꺯���е�һ������
     * @param yĿ�꺯���е���һ������
     * @return����ֵ
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
  		  //System.out.println("prepare to out������");
		  return value;
    }

    /**
     * ģ���˻��㷨�Ĺ���
     * @param yĿ�꺯���е�һ������
     * @return���Ž�
     */
    public static double getSA(double square_y,List<SensorNode1> nodeList2,double nodeNumber,double radius,double barrier_length,double ac) {
        //double result = Double.MAX_VALUE;// ��ʼ�����յĽ��
        double t = T;
        double x[] = new double[k];
        Map<Double,Double> map =  new HashMap<Double,Double>();
        double result = getFuncResult(0,nodeList2,nodeNumber,radius,barrier_length,ac);
        System.out.println("0:  "+result);
        map.put((double) 0,result);
        double min=result,minY=0;
        x[0] = 0;
        // ��ʼ����ʼ��
        for (int i = 1; i < k; i++) {
            x[i] = getX(square_y);
        }
        //System.out.println("��ʼ���ɹ���");
        // �����Ĺ���
        while (t > Tmin) {
            //System.out.println("��ǰ�¶�Ϊ��"+t);
            for (int i = 0; i < k; i++) {
                // �����ʱ�ĺ������
                //System.out.print("��"+i+"�ε���..");
                double funTmp=0;
                if(!map.containsKey(x[i])){
                	funTmp = getFuncResult(x[i],nodeList2,nodeNumber,radius,barrier_length,ac);
                    map.put(x[i], funTmp);
                }else{
                	funTmp = map.get(x[i]);
                }
                //System.out.println("  ��ǰ��СֵΪ:"+funTmp);
                // �������ڲ����µĽ�
                double x_new = x[i] + (Math.random() * 2 - 1) * t;
                // �ж��µ�x���ܳ�����
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
                        // �滻
                        x[i] = x_new;
                    } else {
                        // �Ը����滻
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
        System.out.println("���Ž�Ϊ��" + getSA(square_y,nodeList2,nodeNumber,radius,barrier_length,ac));
        UnLine2 algo2 = new UnLine2(barrier_length,radius,nodeList2,ac);
	    System.out.println("���Ž�Ϊ:"+algo2.getMinMax());
    }

}
