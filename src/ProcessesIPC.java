/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author iyo
 */
public class ProcessesIPC {

    private boolean debugMode = true;
    private File program;
    private Runtime rt;
    private Process mem;
    private OutputStream os;
    private PrintWriter pw;
    private CPU cpu;

    public static void main(String args[]) {
        try {
            ProcessesIPC bridge = new ProcessesIPC();
            int exitVal = bridge.run(args);
            System.out.println("Process exited: " + exitVal);
            System.exit(0);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public int run(String args[]) throws IOException, InterruptedException {

        validateArguments(args);

        rt = Runtime.getRuntime();

        mem = rt.exec("java Memory");

        os = mem.getOutputStream();

        pw = new PrintWriter(os);

        cpu = new CPU();

        initMemory();

        //read from mem
        pw.format("%d\n", 3);
        pw.flush();

        pw.format("%d\n", 8);
        pw.flush();

        //finished writing and reading
        pw.close();

        InputStream is = mem.getInputStream();
        int x = 0;
        while ((x = is.read()) != -1) {
            System.out.print((char) x);
        }

        mem.waitFor();

        return mem.exitValue();

    }

    protected void initMemory() throws FileNotFoundException {
        //initialize memory with the program
        Scanner scan = new Scanner(program);
        debug("READ PROGRAM");
        String ln;
        int i = 0;
        while (scan.hasNext()) {
            ln = scan.nextLine();
            //write to mem
            pw.format("%d %s\n", i, ln);
            pw.flush();
            debug("[" + i + "] - " + ln);
            i++;
        }
    }

    /**
     * Validate arguments passed to the program and
     * creates program object
     *
     * @param args
     */
    protected void validateArguments(String[] args) {

        if (debugMode) {
            debug("ARGUMENTS");
            for (String s : args) {
                debug("\t" + s);
            }
        }

        if (args.length < 1) {
            error("IO [path to program]");
        }

        program = new File(args[0]);
        if (!program.exists()) {
            error("Program does not exist.");
        }

        if (args.length == 2) {
            debugMode = args[1].equals("-debug");
        }
        debug("PROGRAM VALIDATED");
    }

    /**
     * Exits the system with an error message
     *
     * @param msg
     */
    protected void error(String msg) {
        System.out.println(msg);
        System.exit(-1);
    }

    protected void debug(String msg) {
        if (debugMode) {
            System.out.println(msg);
        }
    }
}