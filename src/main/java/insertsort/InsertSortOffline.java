package insertsort;

import java.io.*;

/**
 * Created by liuxiyang
 */
public class InsertSortOffline {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        int totalLen = 10000;
        int[] arr = new int[totalLen];
        int i = 0;
        System.out.println("Start read data");
        // 读取所有行
        try {
            FileReader fileReader = new FileReader("src/main/resources/sortnumbers.txt");
            String str;
            BufferedReader reader = new BufferedReader(fileReader);
            while ((str = reader.readLine()) != null) {
                arr[i] = Integer.parseInt(str);
                i++;
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("Error reading ", e);
        }
        System.out.println("Start sort data");
        // 插入排序-离线版
        int j, key;
        for (i = 1; i < totalLen; i++) {
            key = arr[i];
            j = i - 1;
            while ((j >= 0) && (arr[j] > key)) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
        System.out.println("Start write data");
        // 输出排序结果
        String pathName = "src/main/java/insertsort/results-offline.txt";
        File file = new File(pathName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(pathName);
        for (i = 0; i < totalLen; i++) {
            fileWriter.write(arr[i] + "\n");
        }
        fileWriter.flush();
        fileWriter.close();
        System.out.println("Program finish");
        long endTime = System.currentTimeMillis();
        System.out.println("Program running time：" + (endTime - startTime) + "ms");
    }
}
