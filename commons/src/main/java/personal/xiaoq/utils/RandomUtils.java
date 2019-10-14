package personal.xiaoq.utils;

import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-01-11 18:13
 * @Version: V1.0.0
 */
public class RandomUtils {
    private static Random r = new Random();

    /**
     * @param min
     * @param max
     * @return Random number
     */

    public static IntStream getRandomInts(int min, int max) {
        return r.ints(min, (max + 1));
    }


    /**
     * @param min
     * @param max
     * @return Random number
     */

    public static int getRandomIntInRange(int min, int max) {
        return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
    }

    /**
     * @param min
     * @param max
     * @return Random number
     */

    public static long getRandomLongInRange(long min, long max) {
        return r.longs(min, (max + 1)).limit(1).findFirst().getAsLong();
    }





    /**
     * @param min
     * @param max
     * @return Random number
     */

    public static double getRandomDbouleInRange(double min, double max) {
        return r.doubles(min, (max + 1)).limit(1).findFirst().getAsDouble();
    }

    /**
     * 生成排除{@code exclude} 在内的随机数
     *
     * @param min
     * @param max
     * @param exclude
     * @return Random number
     */
    public static int getRandomIntInRangeWithExclude(int min, int max, Set<Integer> exclude) {
        if (min == max) {
            throw new IllegalArgumentException("min and max can not equal");
        }
        return r.ints(min, (max + 1)).filter((r) -> !exclude.contains(r)).limit(1).findFirst().getAsInt();
    }

    /**
     * @param min
     * @param max
     * @return Random number string
     */
    public static String getRandomStringInRange(int min, int max) {
        return String.valueOf(r.ints(min, (max + 1)).limit(1).findFirst().getAsInt());
    }

    public static void main(String[] args) {
        IntStream.range(1,10).map(i->getRandomIntInRange(1,3))
                .forEach(System.out::println);
        /*IntStream.range(1,100).asDoubleStream()
                .map(i->getRandomDbouleInRange(-200D,300D)).forEach(System.out::println);*/
    }


}
