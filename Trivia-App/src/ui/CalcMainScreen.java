package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CalcMainScreen {

	private JFrame frame;
	private JTextField textFieldFirstNum;
	private JTextField textFieldSecondNum;
	private JTextField textFieldAns;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalcMainScreen window = new CalcMainScreen();
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
	public CalcMainScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textFieldFirstNum = new JTextField();
		textFieldFirstNum.setBounds(60, 50, 80, 30);
		frame.getContentPane().add(textFieldFirstNum);
		textFieldFirstNum.setColumns(10);
		
		textFieldSecondNum = new JTextField();
		textFieldSecondNum.setBounds(200, 50, 80, 30);
		frame.getContentPane().add(textFieldSecondNum);
		textFieldSecondNum.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int num1,num2,ans;
				
				try
				{
					num1=Integer.parseInt(textFieldFirstNum.getText());
					num2=Integer.parseInt(textFieldSecondNum.getText());
					ans=num1+num2;
					
					textFieldAns.setText(String.valueOf(ans));
				}
				catch(Exception e_add)
				{
					JOptionPane.showMessageDialog(null, "Enter valid numbers");
				}
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAdd.setBounds(75, 125, 90, 30);
		frame.getContentPane().add(btnAdd);
		
		JButton btnSubtract = new JButton("Subtract");
		btnSubtract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int num1,num2,ans;
				try
				{
					num1=Integer.parseInt(textFieldFirstNum.getText());
					num2=Integer.parseInt(textFieldSecondNum.getText());
					ans=num1-num2;
					
					textFieldAns.setText(String.valueOf(ans));
					
//					frame.dispose();
					
					CalcingScreen calc=new CalcingScreen();
					calc.setVisible(true);
				}
				catch(Exception e_sub)
				{
					JOptionPane.showMessageDialog(null, "Enter valid numbers");
				}
			}
		});
		btnSubtract.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSubtract.setBounds(175, 125, 90, 30);
		frame.getContentPane().add(btnSubtract);
		
		JLabel lblAns = new JLabel("Answer:");
		lblAns.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAns.setBounds(75, 200, 75, 20);
		frame.getContentPane().add(lblAns);
		
		textFieldAns = new JTextField();
		textFieldAns.setColumns(10);
		textFieldAns.setBounds(160, 200, 80, 30);
		frame.getContentPane().add(textFieldAns);
	}
}
