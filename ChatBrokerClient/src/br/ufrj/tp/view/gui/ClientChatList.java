package br.ufrj.tp.view.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientChatList {

	private JFrame frame;
	private static ClientGUI window;
	private static ClientChatList clist;
	private static ClientDoubleChat dlist;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					clist = new ClientChatList();
					dlist = new ClientDoubleChat();
					clist.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientChatList() {
		clist = this;
		dlist = new ClientDoubleChat(clist);
		initialize();
	}
	
	public ClientChatList(ClientGUI window) {
		clist = this;
		dlist = new ClientDoubleChat(clist);
		this.window = window;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 479, 422);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().getContentPane().setLayout(null);
		
		JLabel lblListaDeConectados = new JLabel("Lista de Conectados");
		lblListaDeConectados.setBounds(33, 40, 174, 14);
		getFrame().getContentPane().add(lblListaDeConectados);
		
		JList list = new JList();
		list.setBounds(33, 65, 174, 309);
		getFrame().getContentPane().add(list);
		
		JLabel lblBemvindoAoChat = new JLabel("Bem-vindo ao chat!");
		lblBemvindoAoChat.setBounds(273, 40, 132, 14);
		getFrame().getContentPane().add(lblBemvindoAoChat);
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				clist.getFrame().setVisible(false);
				window.initialize();
				window.getFrmClient().setVisible(true);
				
			}
		});
		btnDisconnect.setBounds(251, 132, 168, 23);
		getFrame().getContentPane().add(btnDisconnect);
		
		JButton btnAskForChat = new JButton("Ask For Chat");
		btnAskForChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//c�digo para come�ar conversa
				clist.getFrame().setVisible(false);
				dlist.getFrame().setVisible(true);
			}
		});
		btnAskForChat.setBounds(273, 214, 127, 23);
		getFrame().getContentPane().add(btnAskForChat);
	}
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
}
