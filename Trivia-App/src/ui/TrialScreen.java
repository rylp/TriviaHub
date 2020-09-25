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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Sample");
		
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, username.getText());
				lblNewLabel.setText(username.getText());
			}
		});

		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setForeground(Color.BLUE);
		btnNewButton.setBounds(150, 150, 90, 30);
		frame.getContentPane().add(btnNewButton);
		
		username = new JTextField();
		username.setBounds(154, 85, 96, 20);
		frame.getContentPane().add(username);
		username.setColumns(10);
		
		lblNewLabel = new JLabel("username");
		lblNewLabel.setBounds(72, 88, 72, 20);
		frame.getContentPane().add(lblNewLabel);
	}
}
