import java.io.*;
import java.nio.file.*;
public class Task5 {
    static final int MAGIC = 0xCAFE;
    static final int VERSION = 1;
    record Course(String name, int hours) {}
    static void write(Path p, Course[] courses) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(p)))) {
            out.writeInt(MAGIC);
            out.writeInt(VERSION);
            out.writeInt(courses.length);
            for (Course c : courses) {
                out.writeUTF(c.name());
                out.writeInt(c.hours());
            }
        }
    }
    static Course[] read(Path p) throws IOException {
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(Files.newInputStream(p)))) {
            if (in.readInt() != MAGIC) throw new IOException("bad magic");
            int version = in.readInt();
            if (version != VERSION) throw new IOException("unknown version: " + version);
            int n = in.readInt();
            Course[] result = new Course[n];
            for (int i = 0; i < n; i++) {
                result[i] = new Course(in.readUTF(), in.readInt());
            }
            if (in.read() != -1) throw new IOException("trailing data");
            return result;
        } catch (EOFException e) {
            throw new IOException("truncated file", e);
        }
    }
    public static void main(String[] args) throws IOException {
        Path p = Path.of("courses.bin");
        write(p, new Course[]{ new Course("Java", 40), new Course("SQL", 20) });
        for (Course c : read(p)) System.out.println(c);
    }
}