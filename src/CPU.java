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
    
    private void processInstruction(int instruction, int op){
    	switch(instruction){
    	
    	case 1: // Load the value into the AC
    		this.ac = op;
    		break;
    	case 2: // Load the value at the address into the AC
    		this.ac = this.read(op);
    		break;
    	case 3: // Store the value in the AC into the address
    		this.write(op, this.ac);
    		break;
    	case 4: // Add the value in X to the AC
    		this.ac+=this.x;
    		break;
    	case 5: // Add the value in Y to the AC
    		this.ac+=this.y;
    		break;
    	case 6: // Subtract the value in X from the AC
    		this.ac-=this.x;
    		break;
    	case 7: // Subtract the value in Y from the AC
    		this.ac-=this.y;
    		break;
    	case 8: // Get a random int from 1 to 100
    		this.ac = 1 + (int)(Math.random()*100);
    		break;
    	case 9: // Write AC to the screen
    		if(op == 1){
    			System.out.print(this.ac);
    		}else if(op == 2){
    			System.out.print((char) this.ac);
    		}
    		break;
    	case 10: // Copy the value in the AC to X
    		this.x = this.ac;
    		break;
    	case 11: // Copy the value in the AC to y
    		this.y = this.ac;
    		break;
    	case 12: // Copy the value in X to the AC
    		this.ac = this.x;
    		break;
    	case 13: // Copy the value in Y to the AC
    		this.ac = this.y;
    		break;
    	case 14: // Jump to the address
    		this.pc = op;
    		break;
    	case 15: // Jump to the address only if the value in the AC is zero
    		if(this.ac == 0)this.pc = op;
    		break;
    	case 16: // Jump to the address only if the value in the AC is not zero
    		if(this.ac != 0)this.pc = op;
    		break;
    	case 17: // Push return address onto stack, jump to the address
    		this.write(this.sp, this.pc);
    		this.sp++;
    		this.pc = op;
    		break;
    	case 18: // Pop the return address from the stack, jump to the address
    		this.sp--;
    		this.pc = this.read(sp);
    		break;
    	case 19: // Increment the value in X
    		this.x++;
    		break;
    	case 20: // Decrement the value in X
    		this.y--;
    		break;
    	case 21: //Load the value at (address+x) into the AC
    		this.ac = this.read(op+this.x);
    		break;
    	case 22: //Load the value at (address+y) into the AC
    		this.ac = this.read(op+this.y);
    		break;
    	case 23: //Push AC onto Stack
    		this.write(this.sp, this.ac);
    		this.sp++;
    		break;
    	case 24: //Pop from the stack into AC
    		this.sp--;
    		this.ac = this.read(sp);
    		break;
    	case 25: //Push return address, set system mode, set PC to int handler
    		break;
    	case 26: //Pop return address into PC, set user mode
    		break;
    	case 50: //End execution
    		System.exit(0);
    		break;
    	default:
    		this.error("Instruction Invalid");
    		break;
    		
    	}
    }
    
    private int read(int address){
    	return 0;
    }
    
    private void write(int address, int data){
    
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
        System.err.println(msg);
        System.exit(0);
    }
}
