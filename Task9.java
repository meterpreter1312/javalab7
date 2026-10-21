import java.io.*;
import java.nio.file.*;
import java.util.zip.CRC32;
public class Task9 {
    static final int V1 = 1, V2 = 2;
    record CourseV1(String name) {}
    record CourseV2(String name, int hours) {}
    static CourseV2 migrate(CourseV1 c) { return new CourseV2(c.name(), 0); }
    static void writeV2(Path p, CourseV2[] cs) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (DataOutputStream body = new DataOutputStream(buf)) {
            for (CourseV2 c : cs) { body.writeUTF(c.name()); body.writeInt(c.hours()); }
        }
        byte[] payload = buf.toByteArray();
        CRC32 crc = new CRC32();
        crc.update(payload);
        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(p))) {
            out.writeInt(V2);
            out.writeInt(cs.length);
            out.writeLong(crc.getValue());
            out.write(payload);
        }
    }
    static CourseV2[] read(Path p) throws IOException {
        try (DataInputStream in = new DataInputStream(Files.newInputStream(p))) {
            int version = in.readInt();
            int n = in.readInt();
            if (version == V1) {
                CourseV2[] r = new CourseV2[n];
                for (int i = 0; i < n; i++) r[i] = migrate(new CourseV1(in.readUTF()));
                return r;
            }
            if (version == V2) {
                long expected = in.readLong();
                byte[] payload = in.readAllBytes();
                CRC32 crc = new CRC32();
                crc.update(payload);
                if (crc.getValue() != expected) throw new IOException("checksum mismatch");
                try (DataInputStream body = new DataInputStream(new ByteArrayInputStream(payload))) {
                    CourseV2[] r = new CourseV2[n];
                    for (int i = 0; i < n; i++) r[i] = new CourseV2(body.readUTF(), body.readInt());
                    return r;
                }
            }
            throw new IOException("unknown future version: " + version);
        } catch (EOFException e) {
            throw new IOException("truncated", e);
        }
    }
    public static void main(String[] args) throws IOException {
        Path p = Path.of("catalog_v2.bin");
        writeV2(p, new CourseV2[]{ new CourseV2("Java", 40) });
        for (CourseV2 c : read(p)) System.out.println(c);
    }
}