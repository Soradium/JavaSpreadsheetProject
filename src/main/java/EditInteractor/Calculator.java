package EditInteractor;

import TableDAO.TableDAO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    private final Pattern localPattern = Pattern.compile("[Rr]([0-9]+)[Cc]([0-9]+)");

    public String doCalculations(TableDAO table, String var1, String var2, String operationType) {
        float parsedFloatValue1 = getValue(var1, table);
        float parsedFloatValue2 = getValue(var2, table);
        int parsedValue1 = (int) parsedFloatValue1;
        int parsedValue2 = (int) parsedFloatValue2;
        boolean floatsPresent = parsedValue1 != parsedFloatValue1 || parsedValue2 != parsedFloatValue2;

        if (floatsPresent) {
            return performFloatCalculation(parsedFloatValue1, parsedFloatValue2, operationType);
        } else {
            return performIntegerCalculation(parsedValue1, parsedValue2, operationType);
        }
    }

    private float getValue(String var, TableDAO table) {
        Matcher matcher = localPattern.matcher(var);
        if (matcher.find()) {
            try {
                return Float.parseFloat(table.getFromTable(var));
            } catch (NumberFormatException e) {
                System.out.println("The value in place " + var + " is not parseable to numbers");
                return 0;
            }
        } else {
            try {
                return Float.parseFloat(var);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }

    private String performFloatCalculation(float value1, float value2, String operationType) {
        return switch (operationType) {
            case "*" -> Float.toString(value1 * value2);
            case "/" -> value2 != 0 ? Float.toString(value1 / value2) : "ERROR";
            case "+" -> Float.toString(value1 + value2);
            case "-" -> Float.toString(value1 - value2);
            case "^" -> Float.toString((float) Math.pow(value1, value2));
            default -> "ERROR";
        };
    }

    private String performIntegerCalculation(int value1, int value2, String operationType) {
        return switch (operationType) {
            case "*" -> Integer.toString(value1 * value2);
            case "/" -> value2 != 0 ? Integer.toString(value1 / value2) : "ERROR";
            case "+" -> Integer.toString(value1 + value2);
            case "-" -> Integer.toString(value1 - value2);
            case "^" -> Integer.toString((int) Math.pow(value1, value2));
            default -> "ERROR";
        };
    }
}

