import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;
public class Task4 {
    static Stream<String> bad(Path p) throws IOException {
        return Files.lines(p);
    }
    public static void main(String[] args) throws IOException {
        Path p = Path.of("courses.txt");
        Files.writeString(p, "A\nB\nC\n");
        try (Stream<String> lines = Files.lines(p)) {
            lines.forEach(System.out::println);
        }
        Stream<String> leaked = bad(p);
        leaked.forEach(System.out::println);
    }
}