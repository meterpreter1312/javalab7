import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.UUID;
public class Task6 {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of(".").toAbsolutePath();
        Path target = dir.resolve("catalog.txt");
        Path tmp = dir.resolve("catalog.tmp." + UUID.randomUUID());
        try (var writer = Files.newBufferedWriter(tmp)) {
            writer.write("Java\nPython\nRust\n");
            writer.flush();
        }
        try {
            Files.move(tmp, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("atomic move ok");
        } catch (AtomicMoveNotSupportedException e) {
            System.out.println("atomic move not supported, fallback");
            Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}