package ConsoleHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import EditInteractor.Calculator;
import FIleController.Opener;
import FIleController.Saver;
import Presentator.Printer;
import TableDAO.TableDAO;

public class PatternHandler {
    StringBuilder pathname = new StringBuilder();
    private final Pattern cellPattern = Pattern
            .compile("([Rr][0-9]+[Cc][0-9]+)");
    private final Pattern editPattern = Pattern
            .compile("([Ee][Dd][Ii][Tt])" +
                    "\\s?([Rr][0-9]+[Cc][0-9]+)" +
                    "\\s?=\\s?" +
                    "([0-9]+[.]?[0-9]*|[Rr][0-9]+[Cc][0-9]+|)" +
                    "\\s?([+\\-/*^])" +
                    "\\s?([0-9]+[.]?[0-9]*|[Rr][0-9]+[Cc][0-9]+)"
            );
    private final Pattern simpleEditPattern = Pattern
            .compile("([Ee][Dd][Ii][Tt]\\s?)" +
                    "([Rr][0-9]+[Cc][0-9]+)" +
                    "\\s*=\\s*" +
                    "([a-zA-Z0-9 ]+.?" +
                    "|[0-9]+[.]?[0-9]*|[Rr][0-9]+[Cc][0-9]+|" +
                    "[\"][a-zA-Z0-9 ]+.?[\"])");
    private final Pattern printPattern = Pattern
            .compile("([Pp][Rr][Ii][Nn][Tt])");
    private final Pattern openPattern = Pattern
            .compile("([Oo][Pp][Ee][Nn])" +
                    "\\s?" +
                    "([A-Za-z0-9:/\\\\. ]+)");
    private final Pattern closePattern = Pattern
            .compile("([Cc][Ll][Oo][Ss][Ee])");
    private final Pattern savePattern = Pattern
            .compile("([Ss]?[Aa]?[Vv][Ee])");
    private final Pattern saveAsPattern = Pattern
            .compile("([Ss][Aa][Vv][Ee][Aa][Ss])" +
                    "\\s?" +
                    "([A-Za-z0-9:/\\\\. ]+)");
    private final Pattern helpPattern = Pattern
            .compile("([Hh][Ee][Ll][Pp])");
    private final Pattern exitPattern = Pattern
            .compile("([Ee][Xx][Ii][Tt])");


    public void handle(boolean isOpen, String currentInput, TableDAO table) {
        Matcher editMatcher = editPattern
                .matcher(currentInput);
        Matcher simpleEditMatcher = simpleEditPattern
                .matcher(currentInput);
        if (editMatcher.find()) {
            if (isOpen) {
                table.putToTable(editMatcher.group(2),
                        new Calculator()
                                .doCalculations
                                        (
                                                table,
                                                editMatcher.group(3),
                                                editMatcher.group(5),
                                                editMatcher.group(4)
                                        )
                );
            } else {
                System.out.println("No opened file is present" +
                        " or your input is incorrect");
            }

        } else {
            if (simpleEditMatcher.find()) {
                if (isOpen) {
                    Matcher cellPatternMatcher = cellPattern
                            .matcher(simpleEditMatcher.group(3));
                    if (cellPatternMatcher.find()) {
                        table
                                .putToTable(simpleEditMatcher.group(2),
                                        table.getFromTable(simpleEditMatcher.group(3)));
                        new Printer().printer(table);
                    } else {
                        table
                                .putToTable(simpleEditMatcher.group(2),
                                        simpleEditMatcher.group(3));
                        new Printer().printer(table);
                    }

                } else {
                    System.out.println("No opened file is present" +
                            " or your input is incorrect");
                }
            }
        }

        Matcher openMatcher = openPattern.matcher(currentInput);
        if (openMatcher.find()) {
            try {
                if (!isOpen) {
                    pathname.setLength(0);
                    table.setTable
                            (new Opener()
                                    .openFile(openMatcher.group(2))
                            );
                    pathname.append(openMatcher.group(2));
                    isOpen = true;
                    System.out.println("Successfully opened file "
                            + pathname);
                    if (!table.getTable().isEmpty()) {
                        new Printer().printer(table);
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Incorrect input/ " +
                        "this file can't be opened");
            }
        }

        Matcher printMatcher = printPattern.matcher(currentInput);
        if (printMatcher.find()) {
            if (!table.getTable().isEmpty()) {
                if (isOpen) {
                    System.out.print(new Printer().printer(table));
                } else {
                    System.out.println("No opened file is present" +
                            " or your input is incorrect");
                }
            } else System.out.println("Table is empty");
        }

        Matcher closeMatcher = closePattern.matcher(currentInput);
        if (closeMatcher.find()) {
            if (isOpen) {
                table.clear();
                isOpen = false;
            } else {
                System.out.println("No opened file is present" +
                        " or your input is incorrect");
            }
        }

        Matcher saveMatcher = savePattern.matcher(currentInput);
        if (saveMatcher.find()) {
            if (isOpen) {
                new Saver().saveFile(table, pathname.toString());
            } else {
                System.out.println("No opened file is present" +
                        " or your input is incorrect");
            }
        }

        Matcher saveAsMatcher = saveAsPattern.matcher(currentInput);
        if (saveAsMatcher.find()) {
            if (isOpen) {
                new Saver().saveFile(table, saveAsMatcher.group(2));
            } else {
                System.out.println("No opened file is present" +
                        " or your input is incorrect");
            }
        }

        Matcher helpMatcher = helpPattern.matcher(currentInput);
        if (helpMatcher.find()) {
            System.out.println("The following commands are supported: ");
            System.out.println("open <file> : opens <file> ");
            System.out.println("close       : closes currently opened file ");
            System.out.println("save        : saves the currently open file ");
            System.out.println("saveas <fileDirectory>   saves the currently open file in <fileDirectory> ");
            System.out.println("help        : prints this information ");
            System.out.println("exit        : exits the program ");
        }
        Matcher exitMatcher = exitPattern.matcher(currentInput);
        if (exitMatcher.find()) {
            System.exit(0);
        }
    }
}
