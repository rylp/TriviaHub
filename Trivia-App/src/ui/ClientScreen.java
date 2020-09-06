package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import com.google.gson.Gson;

import javax.swing.JLabel;

public class ClientScreen {

	private JFrame frame;
	private JTextField textFieldUserName;
	private JTextField textFieldPassword;
	
	Socket s=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientScreen window = new ClientScreen();
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
	public ClientScreen() {
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
		
		JButton btnConnectToServer = new JButton("Connect");
		btnConnectToServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String ip="localhost";
				int port=9997;
				
				try {
					s=new Socket(ip,port);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.println("Connection Established...");
				
			}
		});
		btnConnectToServer.setBounds(335, 40, 89, 23);
		frame.getContentPane().add(btnConnectToServer);
		
		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(159, 88, 86, 20);
		frame.getContentPane().add(textFieldUserName);
		textFieldUserName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("UserName");
		lblNewLabel.setBounds(86, 91, 63, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(159, 130, 86, 20);
		frame.getContentPane().add(textFieldPassword);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(86, 133, 63, 14);
		frame.getContentPane().add(lblPassword);
		
		JButton btnLogin = new JButton("LogIn");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JsonDataContract jdc=new JsonDataContract();
				Gson gson=new Gson();
				
				jdc.setMessageType("LOGIN");
				jdc.setEmail(textFieldUserName.getText());
				jdc.setPassword(textFieldPassword.getText());
				jdc.setClientIp(s.getInetAddress().getHostAddress());
				
				String data=gson.toJson(jdc,JsonDataContract.class);
				
				System.out.println(data);
				
				try (OutputStreamWriter out = new OutputStreamWriter(
				        s.getOutputStream(), StandardCharsets.UTF_8)) {
				    out.write(data);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnLogin.setBounds(159, 172, 89, 23);
		frame.getContentPane().add(btnLogin);
	}
}
