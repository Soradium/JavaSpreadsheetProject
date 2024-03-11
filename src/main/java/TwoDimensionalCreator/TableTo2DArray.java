package TwoDimensionalCreator;

import TableDAO.TableDAO;

import java.util.Set;

public class TableTo2DArray {
    int max_rows = 131072;
    int max_columns = 2048;
    int n = 2049;
    Set<Integer> keySet;

    public int[] findMaxRowCol(TableDAO table) {
        int[] maxValues_ij = {0,0};

        keySet = table
                .getTable()
                .keySet();

        for(Integer a : keySet) {
            if (a / n >= maxValues_ij[0]) {
                maxValues_ij[0] = (a / n) + 1;
            }
            if (a % n >= maxValues_ij[1]) {
                maxValues_ij[1] = (a % n ) + 1;
            }
            if(a < n) {
                maxValues_ij[1] = a + 1;
            }
        }
        return maxValues_ij;
    }

    public String[][] give2DArray(TableDAO table) {

        int[] ij = findMaxRowCol(table);
        String[][] representation_2D = new String [ij[0]][ij[1]];

        for(Integer a : keySet) {
            if(a < n) {
                representation_2D[0][a] = table
                        .getFromTableWithKey(a);
            }
            else if (a == n) {
                representation_2D[1][0] = table
                        .getFromTableWithKey(a);
            }
            else {
                representation_2D[(a / n)][(a % n)] = table
                        .getFromTableWithKey(a);
            }
        }
        return representation_2D;
    }
}
