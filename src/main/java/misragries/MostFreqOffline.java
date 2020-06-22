package misragries;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by liuxiyang
 * 统计所有元素的出现次数
 */
public class MostFreqOffline {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        System.out.println("Start read data");
        // 读取所有行
        Hashtable<String, AtomicLong> countersMap = new Hashtable<String, AtomicLong>();
        try {
            FileReader fileReader = new FileReader("src/main/resources/frequencychars.txt");
            String str;
            BufferedReader reader = new BufferedReader(fileReader);
            System.out.println("Searching most frequency elements");
            while ((str = reader.readLine()) != null) {
                AtomicLong counter = countersMap.get(str.trim());
                if (counter != null) {
                    counter.incrementAndGet();
                } else {
                    countersMap.put(str, new AtomicLong(1));
                }
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("Error reading ", e);
        }

        System.out.println("ALL frequency elements:");
        for (Map.Entry<String, AtomicLong> entry : countersMap.entrySet()) {
            System.out.println("element: " + entry.getKey() + "\tcounts:" + entry.getValue());
        }
        long endTime = System.nanoTime();
        System.out.println("Program running time： " + (endTime - startTime) / 1000 + "us");
    }
}
