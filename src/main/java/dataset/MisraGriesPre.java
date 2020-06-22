package dataset;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;

/**
 * Created by liuxiyang
 * 2020/6/22 9:56 AM
 */
public class MisraGriesPre {
    public static void main(String[] args) {
        try {
            String pathName = "src/main/resources/frequencychars.txt";
            File file = new File(pathName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(pathName);
            String[] candidateChars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "l", "L", "H"};
            int[] charsNum = new int[]{100, 200, 300, 50, 10, 80, 70, 50, 40, 20, 10, 300, 200};
            Hashtable<String, AtomicLong> countersMap = new Hashtable<String, AtomicLong>();
            List<String> charsSet = new ArrayList<String>();
            // 记录所有的字符类型和总数量
            int totalCount = 0;
            int totalClass = candidateChars.length;
            // 准备插入的字符和对应的数量
            for (int i = 0; i < candidateChars.length; ++i) {
                charsSet.add(candidateChars[i]);
                countersMap.put(candidateChars[i], new AtomicLong(charsNum[i]));
                System.out.println("char: " + candidateChars[i] + " count: " + charsNum[i]);
                totalCount += charsNum[i];
            }
            System.out.println("Total category: " + totalClass + " total chars num: " + totalCount);

            int charClass;
            String targetChar;
            AtomicLong counter;
            // 随机插入字符
            while (totalCount != 0) {
                charClass = (int) (Math.random() * totalClass);
                targetChar = charsSet.get(charClass);
                counter = countersMap.get(targetChar);
                fileWriter.write(targetChar + "\n");
                // 如果该字符已经输出完毕
                if (counter.decrementAndGet() == 0) {
                    countersMap.remove(targetChar);
                    charsSet.remove(charClass);
                    totalClass--;
                }
                totalCount--;
            }

            fileWriter.flush();
            fileWriter.close();
            System.out.println("Finish initial dataset for Misra-Gries");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
