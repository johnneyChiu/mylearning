package personal.xiaoq.base;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-09-23 14:28
 * @Version: V1.0.0
 */
public class Test {

    static void foo(int... x){
        for(int z:x) System.out.println(z);
        for(int i=0;i<x.length;i++) System.out.println(x[i]);
    }

    public static String output = "";

    public static void foo(int i){
        try{
            if(i==1){
                throw new Exception();
            }
            output += "1";
        }catch (Exception e){
            output += "2";
            return;
        }
        finally {
            output += "3";
        }
        output += "4";
    }

    public static void main(String[] args) {
        foo(0);
        foo(1);
        System.out.println(output);
    }


    double method(byte x,double y){
        return (short) x / y * 2;
    }

}
