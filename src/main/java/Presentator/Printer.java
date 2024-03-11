package Presentator;

import TableDAO.TableDAO;
import TwoDimensionalCreator.TableTo2DArray;

import java.util.Arrays;

public class Printer {
    public String printer(TableDAO table) {
        StringBuilder outputString = new StringBuilder();
        String [][] buf = new TableTo2DArray().give2DArray(table);
        int [] longestWordInColumn = new int [buf[0].length];
        for(int i = 0; i < buf.length; i++){
            for(int j = 0; j < buf[0].length; j++) {
                if(buf[i][j] != null) {
                    int lengthOfString = (buf[i][j]).length();
                    if (lengthOfString > longestWordInColumn[j]) {
                        longestWordInColumn[j] = lengthOfString;
                    }
                }
            }
        }
        for(int i = 0; i < buf.length; i++) {
            for (int j = 0; j < buf[0].length; j++) {
                if(buf[i][j] != null) {
                    String currentValue = String.format("%-"
                                    + longestWordInColumn[j]
                                    + "s",
                            buf[i][j]);
                    outputString
                            .append(currentValue)
                            .append(" |");
                }
                else {
                    char[] whitespaces = new char[longestWordInColumn[j]];
                    Arrays.fill(whitespaces, ' ');
                    outputString
                            .append(whitespaces)
                            .append(" |");
                }
            }
            outputString.append('\n');
        }
        return outputString.toString();
    }
}
