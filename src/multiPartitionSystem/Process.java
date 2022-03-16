package multiPartitionSystem;

import java.util.Random;

public class Process {
	
	int size; 
	int mem_loc; 
	int id;
	
	Process(int id){
		this.id = id;
		// Init the random object
		Random r_obj = new Random();
		
		// Generate a random size from 10-100
		size = r_obj.nextInt(100-10) + 10;
	}
	
	public int getSize() {return this.size;}
	public int getId() {return this.id;}
}
