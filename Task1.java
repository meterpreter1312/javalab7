import java.io.IOException;
import java.nio.file.*;
public class Task1 {
    public static void main(String[] args) {
        Path path = Path.of("courses.txt");
        String data = "Java\nPython\nC++\n";
        try {
            Files.writeString(path, data);
            System.out.println("Записано");
        } catch (IOException e) {
            System.out.println("Ошибка записи: " + e.getMessage());
            return;
        }
        try {
            String read = Files.readString(path);
            System.out.println("Прочитано:");
            System.out.println(read);
        } catch (NoSuchFileException e) {
            System.out.println("Файл не найден: " + path);
        } catch (IOException e) {
            System.out.println("Ошибка чтения: " + e.getMessage());
        }
    }
}