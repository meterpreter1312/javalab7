import java.io.*;
import java.nio.charset.StandardCharsets;
public class Task8 {
    public static void main(String[] args) throws IOException {
        int maxErrors = 10;
        long threshold = 5;
        long lines = 0, chars = 0;
        int errors = 0;
        boolean cancelled = false;
        try (BufferedReader r = new BufferedReader(new InputStreamReader(
                new FileInputStream("big.txt"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = r.readLine()) != null) {
                lines++;
                chars += line.length();
                if (line.isBlank() || line.length() < 3) {
                    if (errors < maxErrors) System.out.println("error at line " + lines + ": " + line);
                    errors++;
                }
                if (errors >= threshold) { cancelled = true; break; }
            }
        }
        System.out.println("lines=" + lines + " chars=" + chars + " errors=" + errors + " cancelled=" + cancelled);
    }
}