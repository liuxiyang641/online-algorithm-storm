package dataset;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by liuxiyang
 */
public class InsertSortPre {
    public static void main(String[] args) {
        try {
            int numCount = 10000;
            String pathName = "src/main/resources/sortnumbers.txt";
            File file = new File(pathName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(pathName);
            while (numCount != 0) {
                int data = (int) (Math.random() * 10000);
                fileWriter.write(data + "\n");
                numCount--;
            }
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Finish initial dataset for insert sort");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
