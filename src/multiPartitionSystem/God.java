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

import java.util.stream.Collectors;
import java.util.stream.Stream;


public class God {
	public List<Memory> freeList = new ArrayList<Memory>(); 
	
	public List<Memory> busyList = new ArrayList<Memory>();
	
	public List<Process> readyProcesses = new ArrayList<Process>(); 
	
	public List<Memory> allList = new ArrayList<Memory>();
	
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
				tempMem = new Memory(null, 0, false);
			}
			else
			{
				// Set the next item to point to front item
				tempMem = new Memory(this.freeList.get(0), loc, false);
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
		displayAllList();
		
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
			JLabel Processes_Label = new JLabel("NO FREE SPACE AVAILABLE");
			if(mem == this.freeList.get(this.freeList.size() - 1) && this.freeList.get(this.freeList.size() - 1).size < procSize)
			{
				System.out.print("No Free Space");
				// Show Busy Memory List
				GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
				gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_8.gridx = 1;
				gbc_lblNewLabel_8.gridy = 8;
				this.frame.errorPanel.add(Processes_Label, gbc_lblNewLabel_8);
				return;
			}
			// Remove error if there's no proble
			else
			{
				this.frame.errorPanel.remove(Processes_Label);
			}
		}
		
		
		
		
		
		// remove process from ready list
		this.readyProcesses.remove(proc);
		
		// Change the free spots' location and size
		chosenFreeSpace.size -= procSize;
		//chosenFreeSpace.memoryLocation += procSize + 1;
		
		// Create new allocated memory at end of free list
		Memory tempMem = new Memory(chosenFreeSpace.next, chosenFreeSpace.memoryLocation, true);
		tempMem.size = procSize;
		tempMem.memoryLocation = chosenFreeSpace.memoryLocation + chosenFreeSpace.size + 1;
				
		this.busyList.add(0, tempMem);
		
		// display all lists
		this.displayBusyList();
		this.displayProcesses();
		this.displayFreeList();
		this.displayAllList();
		
		// display error
		this.displayError();
		
	}
	
	
	public void deallocateProcess(Memory mem)
	{
		// Create a temp list so we don't mess up original
		List<Memory> tempAllList = this.allList;
		List<Memory> tempBusyList = this.busyList; 
		
		// Reverse the list to put in the right order 
		Collections.reverse(tempAllList);
		Collections.reverse(tempBusyList);
				
		int indexOfProcessInAll = tempAllList.indexOf(mem);
		int indexOfProcessInBusy = tempBusyList.indexOf(mem);
		
		// Remove from busy
		this.busyList.remove(mem);

		// Remove from all
		this.allList.remove(mem);
		
		System.out.println("Deallocate all: " + indexOfProcessInAll);
		System.out.println("Deallocate busy: " + indexOfProcessInBusy);
		
		
		Memory prevMem = new Memory();
		Memory nextMem = new Memory();
		// Find previous list item
		if( indexOfProcessInAll > 0)
		{
			prevMem = tempAllList.get(indexOfProcessInAll - 1);
		}
		if( indexOfProcessInAll < tempAllList.size() - 1 )
		{
			nextMem = tempAllList.get(indexOfProcessInAll);
		}
		
		// Keep track if both allocated, prev allocated, next allocated, or both free
		String allocStatus = "";
		
		// If the previous is allocated
		if(prevMem.freeBit && nextMem.freeBit)
		{
			allocStatus = "both allocated";
		}
		if(!prevMem.freeBit && nextMem.freeBit)
		{
			allocStatus = "next allocated";
		}
		if(prevMem.freeBit && !nextMem.freeBit)
		{
			allocStatus = "prev allocated";
		}
		if(!prevMem.freeBit && !nextMem.freeBit)
		{
			allocStatus = "both free";
		}
		
		System.out.println(allocStatus);

		this.displayAllList();
		displayBusyList();
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
			layout.rowHeights = new int[]{20,20,20, 20};
			layout.columnWeights = new double[]{0,0,0};
			layout.rowWeights = new double[]{Double.MIN_VALUE};
			itemPanel.setLayout(layout);
			Color maroon  = new Color(252, 78, 3);
			itemPanel.setBackground(maroon);
			
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
			
			
			// Add 'deallocate' button
			JButton button = new JButton("Deallocate");
			GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
			gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 0);
			gbc_btnNewButton_2.gridy = 3;
			gbc_btnNewButton_2.gridx = 0;
			
			itemPanel.add(button, gbc_btnNewButton_2);
			
			God god = this;
			// Add event listener for button
			button.addActionListener(new ActionListener() {

			    @Override
			    public void actionPerformed(ActionEvent e) {
			    	
			    	// Remove allocated memory to hold process
			    	god.deallocateProcess(mem);
			    	
			    	
			    	god.displayBusyList();
			    }
			});
			
			count--;
			
		}
		
		
		this.frame.freeMemoryPanel.revalidate();
		this.frame.repaint();
	}
	
	
	public void displayAllList()
	{
		this.frame.memoryPanel.removeAll();
		

		
		// Combine free list and busy list
		this.allList = Stream.of(this.freeList, this.busyList)
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());
		
		
		
		// Keep track of where to put the process (x wise)
		int count = this.allList.size() - 1; 
		
		// Sort by memory location
		Collections.sort(this.allList, (o1, o2) -> o1.getMemoryLocation() - o2.getMemoryLocation());
		
		// Reverse the list to put in the right order 
		Collections.reverse(this.allList);
		
		// Put process in ready panel
		for( Memory mem : this.allList)
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
			
			
			// If it's allocated, make it marroon
			if(mem.getFreeBit())
			{
				Color maroon  = new Color(252, 78, 3);
				itemPanel.setBackground(maroon);
			}
			else
			{
				itemPanel.setBackground(Color.green);
			}
			
			
			this.frame.memoryPanel.add(itemPanel, gbc_panel);
			
			
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
		
		
		this.frame.memoryPanel.revalidate();
		this.frame.repaint();
	}
	
	
	public void displayError()
	{
		this.frame.errorPanel.removeAll();
		this.frame.errorPanel.revalidate();
		this.frame.repaint();
	}
	
}
