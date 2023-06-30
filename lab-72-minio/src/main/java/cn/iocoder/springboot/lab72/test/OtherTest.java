package cn.iocoder.springboot.lab72.test;

/**
 * @author YS
 * @date 2023/6/30 9:23
 * @Description
 */
public class OtherTest {
    public static void main(String[] args) {
        /**
         * public Thread(Runnable target) {
         *         init(null, target, "Thread-" + nextThreadNum(), 0);
         *     }
         */


        /**
         * public abstract void run();
         * 无入参, 无出参
         */
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("run");
            }
        });
    }
}
