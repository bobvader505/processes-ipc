/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author iyo
 */
public class ProcessesIPC {

    public static void main(String args[]) {
        try {
            File program = validateArguments(args);
            
            Runtime rt = Runtime.getRuntime();
          
            Scanner scan = new Scanner(program);
            
            while(scan.hasNextInt()){
                System.out.println(scan.nextInt());
            }
            
           System.exit(0);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Validate arguments passed to the program
     *
     * @param args
     * @return File program file
     */
    public static File validateArguments(String[] args) {
//        for (String s : args) {
//            System.out.println("arg -> " + s);
//        }
        if (args.length < 1) {
            error("IO [path to program]");
        }

        File file = new File(args[0]);
        if (!file.exists()) {
            error("Program does not exist.");
        }
        return file;
    }

    /**
     * Exits the system with an error message
     *
     * @param msg
     */
    public static void error(String msg) {
        System.out.println(msg);
        System.exit(0);
    }
}