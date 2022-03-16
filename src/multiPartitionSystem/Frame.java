package multiPartitionSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Frame extends JFrame {

	// All Content
	public JPanel contentPane;
	
	// All Memory
	public JPanel memoryPanel = new JPanel();
	
	// Free Memory
	public JPanel freeMemoryPanel = new JPanel();
	
	// Busy Memory
	public JPanel busyMemoryPanel = new JPanel();
	
	// Process panel
	public JPanel processPanel = new JPanel();
	
	// Error message panel
	public JPanel errorPanel = new JPanel();
	
	// God
	public God god;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	/**
	 * Create the frame.
	 */
	public Frame(God god) {
		this.god = god;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel contentPane = new JPanel();
		this.contentPane = contentPane;
		
		// Set size and position of window
		setBounds(20, 20, 1200, 900);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		
		// Set the columns of the grid
		//gbl_contentPane.columnWidths = new int[]{20, 70, 70, 70, 70, 70, 111, 150, 30, 20, 0};
		gbl_contentPane.columnWidths = new int[]{900};
		
		
		// Set the rows of the grid
		//gbl_contentPane.rowHeights = new int[]{20, 100, 106, 80, 20, 351, 51, 0};
		gbl_contentPane.rowHeights = new int[]{20, 100, 20, 100, 20, 100, 20, 100, 50, 20};
		
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		
		// Show Free Memory List
		JLabel lblNewLabel_5 = new JLabel("Free Memory List");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 0;
		contentPane.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.ipady = 20;
		gbc_panel.ipadx = 20;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		gbc_panel.gridheight = 1;
		
		contentPane.add(this.freeMemoryPanel, gbc_panel);
		
		
		GridBagLayout gbl_ready_panel = new GridBagLayout();
		gbl_ready_panel.columnWidths = new int[]{0,70,70,70,70,70,70,70,0};
		gbl_ready_panel.rowHeights = new int[]{0};
		gbl_ready_panel.columnWeights = new double[]{0,0,0};
		gbl_ready_panel.rowWeights = new double[]{Double.MIN_VALUE};
		this.freeMemoryPanel.setLayout(gbl_ready_panel);
		this.freeMemoryPanel.setBackground(Color.cyan);
		
		
		
		// Show Busy Memory List
		JLabel Busy_Memory_Label = new JLabel("Busy Memory List");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 2;
		contentPane.add(Busy_Memory_Label, gbc_lblNewLabel_6);
		
		GridBagConstraints gbc_panel2 = new GridBagConstraints();
		gbc_panel2.ipady = 20;
		gbc_panel2.ipadx = 20;
		gbc_panel2.insets = new Insets(0, 0, 5, 5);
		gbc_panel2.fill = GridBagConstraints.BOTH;
		gbc_panel2.gridx = 0;
		gbc_panel2.gridy = 3;
		gbc_panel2.gridheight = 1;
		
		contentPane.add(this.busyMemoryPanel, gbc_panel2);
		GridBagLayout gbl_busy_panel = new GridBagLayout();
		gbl_busy_panel.columnWidths = new int[]{0,70,70,70,70,70,70,70,0};
		gbl_busy_panel.rowHeights = new int[]{0};
		gbl_busy_panel.columnWeights = new double[]{0,0,0};
		gbl_busy_panel.rowWeights = new double[]{Double.MIN_VALUE};
		this.busyMemoryPanel.setLayout(gbl_ready_panel);
		this.busyMemoryPanel.setBackground(Color.red);
		
		
		// Show Busy Memory List
		JLabel All_Memory_Label = new JLabel("All Memory List");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 0;
		gbc_lblNewLabel_7.gridy = 4;
		contentPane.add(All_Memory_Label, gbc_lblNewLabel_7);
		
		GridBagConstraints gbc_panel3 = new GridBagConstraints();
		gbc_panel3.ipady = 20;
		gbc_panel3.ipadx = 20;
		gbc_panel3.insets = new Insets(0, 0, 5, 5);
		gbc_panel3.fill = GridBagConstraints.BOTH;
		gbc_panel3.gridx = 0;
		gbc_panel3.gridy = 5;
		gbc_panel3.gridheight = 1;
		
		contentPane.add(this.memoryPanel, gbc_panel3);
		GridBagLayout gbl_all_panel = new GridBagLayout();
		gbl_all_panel.columnWidths = new int[]{0,70,70,70,70,70,70,0};
		gbl_all_panel.rowHeights = new int[]{0};
		gbl_all_panel.columnWeights = new double[]{0,0,0};
		gbl_all_panel.rowWeights = new double[]{Double.MIN_VALUE};
		this.memoryPanel.setLayout(gbl_all_panel);
		this.memoryPanel.setBackground(Color.blue);
		
		
		
		
		// Show Busy Memory List
		JLabel Processes_Label = new JLabel("Ready Processes");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 0;
		gbc_lblNewLabel_8.gridy = 6;
		contentPane.add(Processes_Label, gbc_lblNewLabel_8);
		
		GridBagConstraints gbc_panel4 = new GridBagConstraints();
		gbc_panel4.ipady = 20;
		gbc_panel4.ipadx = 20;
		gbc_panel4.insets = new Insets(0, 0, 5, 5);
		gbc_panel4.fill = GridBagConstraints.BOTH;
		gbc_panel4.gridx = 0;
		gbc_panel4.gridy = 7;
		gbc_panel4.gridheight = 1;
		
		contentPane.add(this.processPanel, gbc_panel4);
		GridBagLayout gbl_process_panel = new GridBagLayout();
		gbl_process_panel.columnWidths = new int[]{0,70,0};
		gbl_process_panel.rowHeights = new int[]{0};
		gbl_process_panel.columnWeights = new double[]{0,0,0};
		gbl_process_panel.rowWeights = new double[]{Double.MIN_VALUE};
		this.processPanel.setLayout(gbl_process_panel);
		this.processPanel.setBackground(Color.yellow);
		
		
		
		
		
		// New Process button
		JButton addProcessBtn = new JButton("New Process");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 8;
		contentPane.add(addProcessBtn, gbc_btnNewButton);
		
		// Add process on Click
		addProcessBtn.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        //your actions
		    	god.newProcess();
		    }
		});
		
		
		// Add spot for error message
		// Show Busy Memory List
		GridBagConstraints gbc_panel5 = new GridBagConstraints();
		gbc_panel5.ipady = 10;
		gbc_panel5.ipadx = 10;
		gbc_panel5.insets = new Insets(0, 0, 5, 5);
		gbc_panel5.fill = GridBagConstraints.BOTH;
		gbc_panel5.gridx = 0;
		gbc_panel5.gridy = 9;
		gbc_panel5.gridheight = 1;
		
		contentPane.add(this.errorPanel, gbc_panel5);
		GridBagLayout gbl_error_panel = new GridBagLayout();
		gbl_error_panel.columnWidths = new int[]{0,70,0};
		gbl_error_panel.rowHeights = new int[]{0};
		gbl_error_panel.columnWeights = new double[]{0,0,0};
		gbl_error_panel.rowWeights = new double[]{Double.MIN_VALUE};
		this.errorPanel.setLayout(gbl_error_panel);
		this.errorPanel.setBackground(Color.yellow);
		
	}

}
