import java.io.*;
import java.util.regex.*;

public class URLExtractor {
    public static void main(String[] args) {
        try {
            String filePath = readFilePath("input.txt");
            if (filePath != null) {
                processFiles(filePath);
            } else {
                System.out.println("File path not found in input.txt.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFilePath(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("C:\\")) {
                reader.close();
                return line;
            }
        }
        reader.close();
        return null;
    }

    private static void processFiles(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
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
                } while ((line = reader.readLine()) != null && !line.equals("end")); // Updated condition

                writer.write(output.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
