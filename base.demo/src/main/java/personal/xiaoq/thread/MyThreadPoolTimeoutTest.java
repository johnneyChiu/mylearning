package personal.xiaoq.thread;

import ch.qos.logback.core.util.ExecutorServiceUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import personal.xiaoq.utils.RandomUtils;

import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-09-19 19:19
 * @Version: V1.0.0
 */
public class MyThreadPoolTimeoutTest {


    public static void main(String[] args) throws InterruptedException {

        final ExecutorService executorService=new ThreadPoolExecutor(4, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        IntStream.range(1,10).forEach(i->{
            Callable<String> callable = new SelectRunable(i);
            Future<String> stringFuture = executorService.submit(callable);
            try {
                String re = stringFuture.get(5000, TimeUnit.MILLISECONDS);
                System.out.println("任务成功"+re);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                stringFuture.cancel(true);
                System.out.println("取消任务");
                e.printStackTrace();
            }

        });
        while (true) {
            System.out.println("thread pool counts:"+((ThreadPoolExecutor) executorService).getTaskCount() );
            //((ThreadPoolExecutor) executorService).remove(callable);
            Thread.sleep(10000L);
        }

        //test();
    }


/*
    public static void test(){
        Random random = new Random();
        ExecutorService threadPool = Executors.newFixedThreadPool(4);

        Future<String> future= threadPool.submit(()->{
            //Thread.sleep(random.nextInt(2000));
            for(;;){
                int s = RandomUtils.getRandomIntInRange(1, 1000);
                System.out.println(s);
                if(s==100) break;
            }
            return "test";
        });

        try {
            String re = future.get(2000, TimeUnit.MILLISECONDS);
            System.out.println("任务成功"+re);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            future.cancel(true);
            System.out.println("取消任务");
        }finally {
            threadPool.shutdown();
        }

    }*/

}



@Data
@AllArgsConstructor
class SelectRunable implements Callable<String>{

    public int anInt;

    public String parse(String str) throws InterruptedException {
        long timeout = RandomUtils.getRandomLongInRange(1, 483050);

        //mojo parse
        //Thread.sleep(timeout);
        System.out.println("thread-" + anInt + " is running " +" and timeout "+timeout);
        long j = System.currentTimeMillis();
        for(long i=0;i<timeout;i++){
            for(int k=0;k<timeout;k++){

            }
        }

        System.out.println("thread-" + anInt + "-runned-" + (System.currentTimeMillis() - j));
        return String.format("%s_%d", str, anInt);

    }

    @Override
    public String call() throws Exception {
        String str = "select * ";
        return parse(str);
    }
}
