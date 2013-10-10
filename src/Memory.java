/**
 * It will consist of 2000 integer entries, 0-999 for the program, 1000-1999 for the interrupt handler.
 * The program cannot access addresses above 999 (exits with error message).
 * Memory will initialize itself by reading a file for the program and one for the interrupt handler (if used).
 * Each line in the file will hold one integer instruction or operand, optionally followed by a comment.
 * It will support two operations:
 *     read(address) returns the value at the address
 *     write(address, data) writes the data to the address
 * @author Blessing Osakue
 */

import java.util.Scanner;

public class Memory {
    
    private int[] data;

   // public static void main(String args[]) {
   //     Memory mem = new Memory();
       
   //     Scanner sc = new Scanner(System.in);
   //     int address = 0;
   //     int data = 0;
   //     boolean isWrite = false;
   //     if (sc.hasNextInt()) {
   //         address = sc.nextInt();
   //     }
   //     else {
   //         System.out.println("Expected Integer");
   //         System.exit(-1);
   //     }
       
   //     if (sc.hasNextInt()) {
   //         isWrite = true;
   //         data = sc.nextInt();
   //     }
       
   //     if (isWrite){
   //         mem.write(address, data);
   //     }
   //     else{
   //          System.out.println(mem.read(address));
   //     }
   // }

    public Memory() {
        this.data = new int[2000];
    }

    public int read(int address) {
        return this.data[address];
    }

    public void write(int address, int data) {
        this.data[address] = data;
    }
}
