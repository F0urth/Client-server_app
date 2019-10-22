import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public
class TestMain {

    public static void main(String[] args) {
        String s = "Hello World";
        byte[] b = {'e', 'x', 'a', 'm', 'p', 'l', 'e'};

        try {
            // create a new file with an ObjectOutputStream
            FileOutputStream out = new FileOutputStream("test.txt");
            ObjectOutputStream oout = new ObjectOutputStream(out);

            // write something in the file
            oout.writeObject(s);
            oout.flush();
            oout.writeObject(b);
            oout.flush();

            // create an ObjectInputStream for the file we created before
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("test.txt"));

            System.out.println(ois.readInt());
            // read and print an object and cast it as string
            System.out.println(ois.readObject());
            System.out.println(ois.readInt());
            // read and print an object and cast it as string
            byte[] read = (byte[]) ois.readObject();
            System.out.println(ois.readInt());
            String s2 = new String(read);
            System.out.println(s2);
            System.out.println(ois.readObject());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
