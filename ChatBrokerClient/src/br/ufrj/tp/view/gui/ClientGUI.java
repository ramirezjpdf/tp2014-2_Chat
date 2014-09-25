package br.ufrj.tp.view.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;


import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientGUI {

	private JFrame frmClient;
	private JTextField textFieldUsername;
	private static ClientGUI window;
	private static ClientChatList clist;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new ClientGUI();
					window.getFrmClient().setVisible(true);
					clist = new ClientChatList(window);
					clist.getFrame().setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	void initialize() {
		setFrmClient(new JFrame());
		getFrmClient().setTitle("Client");
		getFrmClient().setBounds(100, 100, 300, 430);
		getFrmClient().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrmClient().getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(96, 95, 80, 23);
		getFrmClient().getContentPane().add(lblNewLabel);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(26, 139, 235, 20);
		getFrmClient().getContentPane().add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				if (textFieldUsername.getText() != ""){
					//Codigo para criacao de cliente.
					if (clist == null) clist = new ClientChatList(window);
					window.getFrmClient().setVisible(false);
					clist.getFrame().setVisible(true);
					try {
						window.finalize();
					} catch (Throwable e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
			}
		});
		
		btnConnect.setBounds(96, 228, 89, 23);
		getFrmClient().getContentPane().add(btnConnect);
	}
	
	public JFrame getFrmClient() {
		return frmClient;
	}

	public void setFrmClient(JFrame frmClient) {
		this.frmClient = frmClient;
	}

}