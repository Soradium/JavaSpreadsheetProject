package FIleController;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Opener {
    public Map<Integer, String> openFile(String pathname) {
        final Map<Integer, String> table = new HashMap<>();
        int max_rows = 131072;
        int max_columns = 2048;
        int n = 2049;

        try (FileReader reader = new FileReader(pathname)) {
            int rows = 0;
            Scanner parser = new Scanner(reader);

            while(parser.hasNextLine()) {
                String temp = parser.nextLine();
                String [] valueArray = temp
                        .split("([,])");
                for(int a = 0; a < valueArray.length; a++) {
                    if(rows < max_rows &&
                            valueArray.length < max_columns) {
                        table.put(rows * n + a, valueArray[a]);
                    }
                }
                rows++;
            }

            reader.close();
            parser.close();
        } catch (IOException ioExceptionNoFile) {
            try {
                System.out.println("Given file not found, new file is created");
                FileWriter writer = new FileWriter(pathname);
                writer.close();
            } catch (IOException ioExceptionCantSave) {
                ioExceptionCantSave.printStackTrace();
            }
        }
        return table;
    }
}
