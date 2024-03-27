import java.io.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        try {

            String inputFilePath = new File("input.txt").getAbsolutePath();
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equalsIgnoreCase("end")) {
                    continue;
                }

                int problemNumber = Integer.parseInt(line.trim());
                StringBuilder output = new StringBuilder(problemNumber + "\n");

                while (!(line = reader.readLine()).equalsIgnoreCase("end")) {
                    switch (problemNumber) {
                        case 1:
                            output.append(isValidEmail(line.trim())).append("\n");
                            break;
                        case 2:
                            output.append(validatePhoneNumber(line.trim())).append("\n");
                            break;
                        case 3:
                            output.append(validateDate(line.trim())).append("\n");
                            break;
                        case 4:
                            output.append(validateIPAddress(line.trim())).append("\n");
                            break;
                        case 5:
                            output.append(validateCPlusPlusVariable(line.trim())).append("\n");
                            break;
                        case 6:
                            output.append(validateString(line.trim())).append("\n");
                            break;
                        case 7:
                            output.append(findOddAandBOccurrences(line.trim())).append("\n");
                            break;
                        case 8:
                            output.append(extractAndWriteWords(line.trim())).append("\n");
                            break;
//                        case 9:
//                            try {
//                                String filePath = readFilePath(line.trim());
//                                if (filePath != null) {
//                                    System.out.println("File path found: " + filePath);
//                                    processFiles(filePath, "output.txt");
//                                } else {
//                                    System.out.println("File path not found in input.txt.");
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
                        case 10:
                            output.append(validateMathematicalExpression(line.trim(), reader)).append("\n");
                            break;
                    }
                }
                writer.write(output.toString().trim() + "\nx\n");
            }
            reader.close();
            writer.close();
            System.out.println("Check output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches() ? "valid email" : "invalid email";
    }

    private static String validatePhoneNumber(String phoneNumber) {
        String regex1 = "^\\d{10}$";
        String regex2 = "^\\d{3}[-.]?\\d{3}[-.]?\\d{4}$";
        String regex3 = "^\\(\\d{3}\\)[-]?\\d{3}[-.]?\\d{4}$";

        if (phoneNumber.matches(regex1) || phoneNumber.matches(regex2) || phoneNumber.matches(regex3)) {
            return "valid phone number";
        } else {
            return "invalid phone number";
        }
    }

    private static String validateDate(String date) {
        String regex = "^(\\d{4}/(0?[1-9]|1[0-2])/(0?[1-9]|[12][0-9]|3[01])|\\d{4}-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])|(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/\\d{4})$";
        if (date.matches(regex)) {
            return "valid date";
        } else {
            return "invalid date";
        }
    }

    private static String validateIPAddress(String ipAddress) {
        String regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        if (ipAddress.matches(regex)) {
            return "valid IP address";
        } else {
            return "invalid IP address";
        }
    }

    private static String validateCPlusPlusVariable(String variableName) {
        String regex = "^[a-zA-Z_][a-zA-Z0-9_]*$";
        if (variableName.matches(regex)) {
            return "valid C++ variable name";
        } else {
            return "invalid C++ variable name";
        }
    }

    private static String validateString(String str) {
        int consecutiveBsCount = countConsecutiveBs(str);
        if (consecutiveBsCount >= 3) {
            return "invalid string, has " + consecutiveBsCount + " consecutive b’s";
        } else {
            return "valid string";
        }
    }

    private static int countConsecutiveBs(String str) {
        int count = 0;
        int maxCount = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'b' || str.charAt(i) == 'B') {
                count++;
                maxCount = Math.max(maxCount, count);
            } else {
                count = 0;
            }
        }
        return maxCount;
    }

    public static String extractAndWriteWords(String input) {
        StringBuilder result = new StringBuilder();
        String regex = "\\b\\w{3}\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        int count = 0;
        StringBuilder matchedWords = new StringBuilder();
        while (matcher.find()) {
            count++;
            String matchedWord = matcher.group();
            matchedWords.append(matchedWord).append(" ");
        }

        if (count > 0) {
            result.append(input.trim()).append("\n");
            result.append("number of matched words: ").append(count).append("\n");
            matcher.reset();
            while (matcher.find()) {
                String matchedWord = matcher.group();
                int startIndex = matcher.start();
                int endIndex = matcher.end();
                result.append("matched word: ").append(matchedWord).append("\n");
                result.append("start index: ").append(startIndex).append(", end index: ").append(endIndex).append("");
            }
        } else {
            result.append(input.trim()).append("\n");
            result.append("No word matches\n");
        }

        return result.toString();
    }

    public static String validateMathematicalExpression(String expression, BufferedReader reader) throws IOException {
        String regex = "^([-+]?\\d*\\.?\\d*[a-zA-Z]*)([-+*/][-+]?\\d*\\.?\\d*[a-zA-Z]*)+=(?:([-+]?\\d*\\.?\\d*[a-zA-Z]*))?([-+]?\\d*\\.?\\d*[a-zA-Z]*)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expression);
        return matcher.matches() ? "valid mathematical expression" : "invalid mathematical expression";
    }

    public static String findOddAandBOccurrences(String input) {
        StringBuilder result = new StringBuilder();
        String[] lines = input.split("\n");

        for (String line : lines) {
            if (line.equals("end")) {
                break;
            }

            String regex = "\\b(?:a(?:[^a]*a[^a]*a)*[^a]*|b(?:[^b]*b[^b]*b)*[^b]*)\\b";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line);

            int count = 0;
            while (matcher.find()) {
                count++;
                String matchedSubstring = matcher.group();
                int startIndex = matcher.start();
                int endIndex = matcher.end();
                result.append("*").append(matchedSubstring).append("*").append("\n");
                result.append("number of matched substrings: ").append(count).append("\n");
                result.append("matched substring: ").append(matchedSubstring).append("\n");
                result.append("start index: ").append(startIndex).append(", end index: ").append(endIndex).append("\n");
            }
        }

        return result.toString();
    }

    protected static String readFilePath(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("C:\\")) {
                reader.close();
                System.out.println("File path detected: " + line);
                return line;
            }
        }
        reader.close();
        System.out.println("File path not found in input.txt for problem 9. Expected format: C:\\path\\to\\file.txt");
        return null;
    }

    public static void processFiles(String filePath, String outputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, true))) {
            String line;
            Pattern pattern = Pattern.compile("https?://\\S+");
            int problemNumber = 0;

            while ((line = reader.readLine()) != null) {
                if (line.equals("end")) {
                    writer.write("x\n");
                    continue;
                }

                problemNumber++;
                StringBuilder output = new StringBuilder("Problem ").append(problemNumber).append("\n");
                int urlCount = 0;
                int lineNumber = 1;

                do {
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        urlCount++;
                        output.append("Number of URLs: ").append(urlCount).append("\n");
                        output.append("URL: ").append(matcher.group()).append("\n");
                        output.append("Line: ").append(lineNumber).append("\n");
                        output.append("start index: ").append(matcher.start()).append(", end index: ").append(matcher.end() - 1).append("\n");
                    }
                    lineNumber++;
                } while ((line = reader.readLine()) != null && !line.equals("end"));

                writer.write(output.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}