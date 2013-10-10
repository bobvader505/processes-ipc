/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author iyo
 */
public class ProcessesIPC {

    private Runtime runtime;
    private Process memProcess;
    final private BufferedWriter out;
    final private BufferedReader in;
    final private BufferedReader err;
    private CPU cpu;
    private boolean debugMode = false;

    public static void main(String args[]) {
        try {
            ProcessesIPC bridge = new ProcessesIPC(args);

            int exitVal = bridge.run();
            System.out.println("Process exited: " + exitVal);
            System.exit(0);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public ProcessesIPC(String args[]) throws IOException {
        validateArguments(args);

        runtime = Runtime.getRuntime();

        //initialize memory
        memProcess = runtime.exec("java Memory " + args[0]);
        debug("READ PROGRAM");

        cpu = new CPU();

        out = new BufferedWriter(new OutputStreamWriter(memProcess.getOutputStream()));

        in = new BufferedReader(new InputStreamReader(memProcess.getInputStream()));

        err = new BufferedReader(new InputStreamReader(memProcess.getErrorStream()));
    }

    public int run() throws IOException, InterruptedException {

        //read & write from mem
        read(2);
        read(3);
        write(2, 79);
        read(2);

        //finished writing and reading

        out.close();
        in.close();
        err.close();

        memProcess.waitFor();

        return memProcess.exitValue();

    }

    /**
     * Validate arguments passed to the program and creates program object
     *
     * @param args
     */
    private void validateArguments(String[] args) {

        if (args.length < 1) {
            error("IO [path to program]");
        }

        if (args.length > 1) {
            debugMode = args[args.length - 1].equals("-debug");
        }

        debug("PROGRAM VALIDATED");
    }

    /**
     * read value from memory
     *
     * @param msg
     * @return int
     */
    protected int read(int address) throws IOException {
        out.write(String.format("%d\n", address));
        out.flush();
        String value = in.readLine();
        if (memProcess.getErrorStream().available() > 0) {
            error(err.readLine());
        }
        debug("READ " + address, value);
        return Integer.parseInt(value);

    }

    /**
     * write value to memory at given address
     *
     * @param address
     * @param value
     * @throws IOException
     */
    protected void write(int address, int value) throws IOException {
        out.write(String.format("%d %d\n", address, value));
        out.flush();
        if (memProcess.getErrorStream().available() > 0) {
            error(err.readLine());
        }
        debug("WRITE " + address, value);
    }

    /**
     * Exits the system with an error message
     *
     * @param msg
     */
    protected void error(String msg) {
        System.err.println(msg);
        System.exit(-1);
    }

    private void debug(Object msg) {
        if (debugMode) {
            System.out.println(msg);
        }
    }

    protected void debug(Object key, Object msg) {
        if (debugMode) {
            System.out.printf("[%s] %s\n", key, msg);
        }

    }
}