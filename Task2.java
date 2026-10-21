import java.io.*;
public class Task2 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("in.txt")); BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt"))) {
            int lines = 0;
            long chars = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                lines++;
                chars += line.length();
                writer.write(line.toUpperCase());
                writer.newLine();
            }
            System.out.println("Строк: " + lines + ", символов: " + chars);
        }
    }
}