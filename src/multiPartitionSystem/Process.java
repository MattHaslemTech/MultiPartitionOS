package multiPartitionSystem;

import java.util.Random;

public class Process {
	
	int size; 
	
	Process(){
		// Init the random object
		Random r_obj = new Random();
		
		// Generate a random size from 100-500
		size = r_obj.nextInt(10) + 1;
	}
	
	public int getSize() {return this.size;}
}
