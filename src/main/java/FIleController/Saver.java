package FIleController;

import TwoDimensionalCreator.TableTo2DArray;
import TableDAO.TableDAO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Saver {

    public void saveFile(TableDAO table, String pathname) {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(pathname))) {
            String[][] a = new TableTo2DArray().give2DArray(table);
            for(int i = 0; i < a.length; i++) {
                for (int j = 0; j < a[0].length; j++) {
                    if (a[i][j] != null) {
                        writer.write(a[i][j]+",");
                    }
                    else {
                        writer.write(" ,");
                    }
                }
                writer.newLine();
            }
        } catch (IOException unableToSaveFile) {
            unableToSaveFile.printStackTrace();
        }
    }
}
