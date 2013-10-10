public class CPU {
    private int pc = 0;
    private int sp = 0;
    private int ir = 0;
    private int ac = 0;
    private int x = 0;
    private int y = 0;
    
    private boolean userMode = true;
    final int PROGRAM_MEM_BOUNDARY = 1000;

    public static void main(String args[]) {
    
    }
    
    /**
     * Determines if a given address is accessible
     * @param address 
     */
    private void isValidAddress(int address) {
        if (this.userMode) {
            if (address < 0 || address >= PROGRAM_MEM_BOUNDARY) {
                this.error("Invalid memory address: " + address);
            }
        }
    }
    
    /**
     * Exits the system with an error message
     * @param msg 
     */
    private void error(String msg) {
        System.out.println(msg);
        System.exit(0);
    }
}
