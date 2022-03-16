package multiPartitionSystem;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


import java.util.Collections;


public class God {
	public List<Memory> freeList = new ArrayList<Memory>(); 
	
	public List<Memory> busyList = new ArrayList<Memory>();
	
	public List<Process> readyProcesses = new ArrayList<Process>(); 
	
	public Frame frame;
	
	
	God()
	{
		this.frame = new Frame(this);
		this.frame.setVisible(true);
		
		generateFreeList();
	}
	
	
	// Every time the 'new process' button is clicked, add a process to the 'Ready Processes' list/panel
	public void newProcess() {
		
		// reset panel every time
		this.frame.processPanel.removeAll();
		
		Process tempProcess = new Process(this.readyProcesses.size());
		this.readyProcesses.add(tempProcess);
		
		// Display the processes 
		displayProcesses();
		
	}
	
	
	// Set initial 5 free times 
	public void generateFreeList()
	{
		// the memory location for the free memory
		int loc = 0;
		Memory tempMem;
		
		for(int i = 0; i < 5; i++)
		{
			// If this is the first item
			if(this.freeList.size() == 0)
			{
				tempMem = new Memory(null, 0);
			}
			else
			{
				// Set the next item to point to front item
				tempMem = new Memory(this.freeList.get(0), loc);
			}
			
			// Toss it in the front of the list
			this.freeList.add(0, tempMem);
			
			// Update location
			loc += tempMem.size + 1;
		}
		
		// reverse the list because it's in the wrong order for some reason
		Collections.reverse(this.freeList);
		
		// Display the free list
		displayFreeList();
		
	}
	
	
	// Allocate memory when process is running
	public void allocateProcess(Process proc)
	{
		
		
		// Search through free memory to find first-fit
		int procSize = proc.getSize();
		
		int count = 0;
		// The free space we're going to allocate the process into
		Memory chosenFreeSpace = new Memory();
		for(Memory mem : this.freeList)
		{
			count++;
			if(mem.size > procSize)
			{
				chosenFreeSpace = mem;
				break;
			}
			
			// If we're on the last one and there  isn't any free space large enough, show error
			if(mem == this.freeList.get(this.freeList.size() - 1) && this.freeList.get(this.freeList.size() - 1).size < procSize)
			{
				System.out.print("No Free Space");
				return;
			}
		}
		
		// Create new allocated memory with the given mem location
		Memory tempMem = new Memory(chosenFreeSpace.next, chosenFreeSpace.memoryLocation);
		tempMem.size = procSize;
		tempMem.memoryLocation = chosenFreeSpace.memoryLocation;
		
		this.busyList.add(0, tempMem);
		
		// remove process from ready list
		this.readyProcesses.remove(proc);
		
		// Change the free spots' location and size
		chosenFreeSpace.size -= procSize;
		chosenFreeSpace.memoryLocation += procSize;
		
		// Update free lists memory locations
		
		
		// display all lists
		this.displayBusyList();
		this.displayProcesses();
		this.displayFreeList();
		
	}
	
	
	
	
	/*
	 * Displays
	 */
	public void displayProcesses()
	{
		this.frame.processPanel.removeAll();
		// Keep track of where to put the process (x wise)
		int count = 0; 
		JButton button;
		// Put process in ready panel
		for( Process process : this.readyProcesses)
		{
			button = new JButton("ID: " + process.getId() + " Size: " + process.getSize());
			GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
			gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 0);
			gbc_btnNewButton_2.anchor = GridBagConstraints.NORTHWEST;
			gbc_btnNewButton_2.gridy = 0;
			gbc_btnNewButton_2.gridx = count;
			
			this.frame.processPanel.add(button, gbc_btnNewButton_2);
			count++;
			
			God god = this;
			
			
			// Add event listener for button
			button.addActionListener(new ActionListener() {

			    @Override
			    public void actionPerformed(ActionEvent e) {
			    	
			    	// Create a new allocated memory to hold process
			    	god.allocateProcess(process);
			    	
			    	
			    	god.displayBusyList();
			    }
			});
		}
		
		
		
		
		this.frame.processPanel.revalidate();
		this.frame.repaint();
	}
	
	
	
	public void displayFreeList()
	{
		this.frame.freeMemoryPanel.removeAll();
		
		// Keep track of where to put the process (x wise)
		//int count = this.freeList.size() - 1; 
		int count = 0;
		
		// Put process in ready panel
		for( Memory mem : this.freeList)
		{
			
			// Add block to free memory list
			JPanel itemPanel = new JPanel();
			
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.ipady = 20;
			gbc_panel.ipadx = 20;
			gbc_panel.insets = new Insets(0, 0, 5, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = count;
			gbc_panel.gridy = 0;
			gbc_panel.gridheight = 1;
			
			GridBagLayout layout = new GridBagLayout();
			layout.columnWidths = new int[] {70};
			layout.rowHeights = new int[]{20,20,20};
			layout.columnWeights = new double[]{0,0,0};
			layout.rowWeights = new double[]{Double.MIN_VALUE};
			itemPanel.setLayout(layout);
			itemPanel.setBackground(Color.green);
			
			this.frame.freeMemoryPanel.add(itemPanel, gbc_panel);
			
			
			// Add allocated label to free memory block
			JLabel allocated_label = new JLabel("Alloc: " + mem.freeBit);
			GridBagConstraints gbc_1 = new GridBagConstraints();
			gbc_1.insets = new Insets(0, 0, 5, 5);
			gbc_1.gridx = 0;
			gbc_1.gridy = 0;
			itemPanel.add(allocated_label, gbc_1);
			
			// Add memory location label to free memory block
			JLabel mem_loc_label = new JLabel("Mem Loc: " + mem.memoryLocation);
			GridBagConstraints gbc_2 = new GridBagConstraints();
			gbc_2.insets = new Insets(0, 0, 5, 5);
			gbc_2.gridx = 0;
			gbc_2.gridy = 1;
			itemPanel.add(mem_loc_label, gbc_2);
			
			
			// Add memory location label to free memory block
			JLabel size_label = new JLabel("Size: " + mem.size);
			GridBagConstraints gbc_3 = new GridBagConstraints();
			gbc_3.insets = new Insets(0, 0, 5, 5);
			gbc_3.gridx = 0;
			gbc_3.gridy = 2;
			itemPanel.add(size_label, gbc_3);
			
			count++;
			
		}
		
		
		this.frame.freeMemoryPanel.revalidate();
		this.frame.repaint();
	}
	
	
	public void displayBusyList()
	{
		this.frame.busyMemoryPanel.removeAll();
		
		// Keep track of where to put the process (x wise)
		int count = this.freeList.size() - 1; 
		
		// Sort by memory location
		Collections.sort(this.busyList, (o1, o2) -> o1.getMemoryLocation() - o2.getMemoryLocation());
		
		// Reverse the list to put in the right order 
		Collections.reverse(this.busyList);
		
		// Put process in ready panel
		for( Memory mem : this.busyList)
		{
			
			// Add block to free memory list
			JPanel itemPanel = new JPanel();
			
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.ipady = 20;
			gbc_panel.ipadx = 20;
			gbc_panel.insets = new Insets(0, 0, 5, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = count;
			gbc_panel.gridy = 0;
			gbc_panel.gridheight = 1;
			
			GridBagLayout layout = new GridBagLayout();
			layout.columnWidths = new int[] {70};
			layout.rowHeights = new int[]{20,20,20};
			layout.columnWeights = new double[]{0,0,0};
			layout.rowWeights = new double[]{Double.MIN_VALUE};
			itemPanel.setLayout(layout);
			itemPanel.setBackground(Color.green);
			
			this.frame.busyMemoryPanel.add(itemPanel, gbc_panel);
			
			
			// Add allocated label to free memory block
			JLabel allocated_label = new JLabel("Alloc: " + mem.freeBit);
			GridBagConstraints gbc_1 = new GridBagConstraints();
			gbc_1.insets = new Insets(0, 0, 5, 5);
			gbc_1.gridx = 0;
			gbc_1.gridy = 0;
			itemPanel.add(allocated_label, gbc_1);
			
			// Add memory location label to free memory block
			JLabel mem_loc_label = new JLabel("Mem Loc: " + mem.memoryLocation);
			GridBagConstraints gbc_2 = new GridBagConstraints();
			gbc_2.insets = new Insets(0, 0, 5, 5);
			gbc_2.gridx = 0;
			gbc_2.gridy = 1;
			itemPanel.add(mem_loc_label, gbc_2);
			
			
			// Add memory location label to free memory block
			JLabel size_label = new JLabel("Size: " + mem.size);
			GridBagConstraints gbc_3 = new GridBagConstraints();
			gbc_3.insets = new Insets(0, 0, 5, 5);
			gbc_3.gridx = 0;
			gbc_3.gridy = 2;
			itemPanel.add(size_label, gbc_3);
			
			count--;
			
		}
		
		
		this.frame.freeMemoryPanel.revalidate();
		this.frame.repaint();
	}
	
	
	
	
}
