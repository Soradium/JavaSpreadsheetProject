package TableDAO;

import java.util.Map;

public interface TableDAO {
    void putToTable(String place, String value);
    String getFromTable(String place);
    Map<Integer, String> getTable();
    void setTable(Map<Integer, String> table);
    String getFromTableWithKey(int key);
    void clear();
}
