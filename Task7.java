import java.io.IOException;
import java.nio.file.*;
import java.util.*;
public class Task7 {
    sealed interface ImportResult permits Success, Failure {}
    record Success(List<String> courses) implements ImportResult {}
    record Failure(List<String> errors) implements ImportResult {}
    static ImportResult importCatalog(Path p) {
        List<String> courses = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        int lineNo = 0;
        try {
            for (String line : Files.readAllLines(p)) {
                lineNo++;
                String s = line.trim();
                if (s.isEmpty()) continue;
                if (s.length() < 3) errors.add("line " + lineNo + ": too short");
                else courses.add(s);
            }
        } catch (IOException e) {
            errors.add("io: " + e.getMessage());
        }
        if (!errors.isEmpty()) return new Failure(errors);
        return new Success(courses);
    }
    public static void main(String[] args) throws IOException {
        Path p = Path.of("import.txt");
        Files.writeString(p, "Java\nPy\n\nRust\nC\n");
        ImportResult r = importCatalog(p);
        switch (r) {
            case Success s -> System.out.println("OK: " + s.courses());
            case Failure f -> System.out.println("FAIL: " + f.errors());
        }
    }
}