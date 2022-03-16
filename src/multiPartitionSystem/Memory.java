package multiPartitionSystem;

import java.util.Comparator;
import java.util.Random;

public class Memory {
	public boolean freeBit;
	public int size;
	public Memory next;
	public int memoryLocation;
	
	Memory(){
		
	}
	
	Memory(Memory next, int memoryLocation, boolean allocated)
	{
		freeBit = false;
		
		// Init the random object
		Random r_obj = new Random();
		
		// Generate a random size from 10-100
		size = r_obj.nextInt(100-50) + 50;
		
		// Point to the next one
		this.next = next;
		
		// Set allocated
		this.freeBit = allocated;
		
		// Set current Memory Location
		this.memoryLocation = memoryLocation;
	}
	
	public int getSize() {return this.size;}
	public int getMemoryLocation() {return this.memoryLocation;}
	public boolean getFreeBit() {return this.freeBit;}

	
	
	
	
}




