import java.io.*;
public class Task3 {
    static class Resource implements Closeable {
        private final String name;
        Resource(String name) { this.name = name; System.out.println("open " + name); }
        public void close() { System.out.println("close " + name); }
        void work() { System.out.println("work " + name); }
    }
    public static void main(String[] args) {
        try (Resource r1 = new Resource("r1"); Resource r2 = new Resource("r2")) {
            r1.work();
            r2.work();
            if (true) throw new RuntimeException("boom");
        } catch (RuntimeException e) {
            System.out.println("caught: " + e.getMessage());
        }
    }
}