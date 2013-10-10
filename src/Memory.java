
/**
 * It will consist of 2000 integer entries, 0-999 for the program, 1000-1999 for
 * the interrupt handler. The program cannot access addresses above 999 (exits
 * with error message). Memory will initialize itself by reading a file for the
 * program and one for the interrupt handler (if used). Each line in the file
 * will hold one integer instruction or operand, optionally followed by a
 * comment. It will support two operations: read(address) returns the value at
 * the address write(address, data) writes the data to the address
 *
 * @author Blessing Osakue
 */
import java.util.Scanner;
import java.util.regex.Pattern;

public class Memory {

    private static int[] data = new int[2000];

    public static void main(String args[]) {
        Memory mem = new Memory();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String str = sc.nextLine();
//            System.out.println("LN - " + str);
            int address;
            if (str.matches("(\\d+) (\\d+).*")) {
                Scanner strSc = new Scanner(str);
                address = Integer.valueOf(strSc.next());
                String dataIn = strSc.next();
                mem.write(address, dataIn);
//                System.out.println("WRITE[" + address + "] " + dataIn);
            }else{
                address = Integer.valueOf(str);
                System.out.println("READ[" + address + "] " + mem.read(address));
            }
        }

    }

    public int read(int address) {
        return data[address];
    }

    public void write(int address, String dataIn) {
        dataIn = dataIn.replaceFirst(".*?(\\d+).*", "$1");
        data[address] = Integer.valueOf(dataIn);
    }
}
