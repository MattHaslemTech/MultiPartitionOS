package multiPartitionSystem;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;


public class runner {
	
	public static void main(String[] args) {
		
		//Frame frame = new Frame();
		 
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//frame.setVisible(true);
					
					// Show window by initializing God.
					God god = new God();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
				

	}

}
