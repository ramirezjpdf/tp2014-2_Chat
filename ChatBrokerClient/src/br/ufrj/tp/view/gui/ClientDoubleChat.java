package br.ufrj.tp.view.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientDoubleChat {

	private JFrame frame;
	private JTextField textField;
	private ClientChatList clientCList;
	private ClientDoubleChat clientDList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientDoubleChat window = new ClientDoubleChat();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientDoubleChat() {
		clientDList = this;
		initialize();
	}
	
	public ClientDoubleChat(ClientChatList clist) {
		clientDList = this;
		clientCList = clist;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 586, 355);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().getContentPane().setLayout(null);
		
		JList list = new JList();
		list.setBounds(28, 70, 113, 153);
		getFrame().getContentPane().add(list);
		
		JLabel lblListaDeConversadores = new JLabel("Lista de Conversadores");
		lblListaDeConversadores.setBounds(10, 21, 113, 14);
		getFrame().getContentPane().add(lblListaDeConversadores);
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				clientDList.getFrame().setVisible(false);
				clientCList.getFrame().setVisible(true);
			}
		});

		btnDisconnect.setBounds(17, 256, 124, 23);
		getFrame().getContentPane().add(btnDisconnect);
		
		textField = new JTextField();
		textField.setBounds(178, 249, 209, 45);
		getFrame().getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSendMessage = new JButton("Send Message");
		btnSendMessage.setBounds(397, 260, 163, 23);
		getFrame().getContentPane().add(btnSendMessage);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(178, 70, 382, 162);
		getFrame().getContentPane().add(textArea);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
