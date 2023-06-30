package cn.iocoder.springboot.lab72.test;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author YS
 * @date 2023/6/30 9:38
 * @Description  函数测试
 */
public class FunctionTest {

    public static void main(String[] args) {

    }

}

class Test_Supplier {
    private static String test_Supplier(Supplier<String> suply) {
        return suply.get(); //供应者接口
    }

    private static Integer test_Supplier02(Supplier<Integer> suply) {
        return suply.get(); //供应者接口
    }
    public static void main(String[] args) {
        // 产生的数据作为 sout 作为输出
        System.out.println(test_Supplier(()->"产生数据"));


        System.out.println(test_Supplier02(() -> 1));

        System.out.println(String.valueOf(new Supplier<String>() {
            @Override
            public String get() {
                return "产生数据";
            }
        }));
    }
}


class use_Supplier_Max_Value {
    private static int getMax(Supplier<Integer> suply) {
        return suply.get(); //供应者接口
    }
    public static void main(String[] args) {
        Integer [] data=new Integer[] {6,5,4,3,2,1};
        int reslut=getMax(()->{
            int max=0;
            for (Integer datum : data) {
                max = Math.max(max, datum);
            }
            return max;
        });
        System.out.println(reslut);
    }
}


class test_consumer{
    public static void sout(Consumer<String> consumer){
        consumer.accept("hello");
    }

    public static void main(String[] args) {
        sout(System.out::println);
    }
}
