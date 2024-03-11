package TableStorage;

import TableDAO.TableDAO;
import java.util.Map;

public class Table implements TableDAO {
    private Map<Integer, String> table;
    private final int CONST_N = 2049;

    public void putToTable(String place, String value) {
        int[] ijArray = new TableMatcher()
                .match(place);

        if(ijArray != null) {
            table.put(ijArray[0] * CONST_N + ijArray[1], value);
        }
    }

    public String getFromTable(String place) {
        String storedValue;
        int[] ijArray = new TableMatcher()
                .match(place);

        if(ijArray != null) {
            storedValue = table.get
                    (ijArray[0] * CONST_N + ijArray[1]);
            return storedValue;
        }

        else return "ERROR";
    }

    public String getFromTableWithKey(int key) {
        return table.get(key);
    }

    @Override
    public void clear() {
        table.clear();
    }

    public Map<Integer, String> getTable() {
        return table;
    }

    public void setTable(Map<Integer, String> table) {
        this.table = table;
    }
}
