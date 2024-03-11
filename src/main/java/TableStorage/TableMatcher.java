package TableStorage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableMatcher {
    private final int CONST_MAX_ROWS = 131072;
    private final int CONST_MAX_COLUMNS = 2048;
    private final Pattern localPattern= Pattern.
            compile("[Rr]([0-9]+)[Cc]([0-9]+)");

    public int[] match(String place) {
        Matcher matcher = localPattern.matcher(place);
        try {
            if (matcher.find() &&
                    Integer.parseInt(matcher.group(1)) <= CONST_MAX_ROWS &&
                    Integer.parseInt(matcher.group(2)) <= CONST_MAX_COLUMNS) {
                int i = Integer.parseInt(matcher.group(1));
                int j = Integer.parseInt(matcher.group(2));
                return new int[]{i, j};
            }
        } catch (MatchException matchException) {
            matchException.printStackTrace();
        }
        return new int[]{};
    }
}
