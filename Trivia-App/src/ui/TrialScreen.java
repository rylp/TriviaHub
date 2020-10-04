package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class TrialScreen {

	private JFrame frame;
	private JTextField username;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrialScreen window = new TrialScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TrialScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.RED);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblNewLabel = new JLabel("username");
		lblNewLabel.setBounds(101, 35, 47, 14);
		frame.getContentPane().add(lblNewLabel);
		
		username = new JTextField();
		username.setBounds(158, 32, 86, 20);
		frame.getContentPane().add(username);
		username.setColumns(10);
		
		JButton btnNewButton = new JButton("Sample");
		btnNewButton.setBounds(158, 93, 79, 27);
		
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, username.getText());
				lblNewLabel.setText(username.getText());
			}
		});
		
				btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
				btnNewButton.setBackground(Color.CYAN);
				btnNewButton.setForeground(Color.BLUE);
				frame.getContentPane().add(btnNewButton);
	}
}
