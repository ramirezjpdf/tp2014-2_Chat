package br.ufrj.tp.view.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JDesktopPane;

public class LoginView {

	private JFrame frmJmcChat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView window = new LoginView();
					window.frmJmcChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmJmcChat = new JFrame();
		frmJmcChat.setTitle("JMC Chat - Seja Bem-vindo!");
		frmJmcChat.setBounds(100, 100, 450, 300);
		frmJmcChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
