package br.ufrj.tp.main;

import br.ufrj.tp.utils.SocketUtils.TransportProtocol;
import br.ufrj.tp.view.consoleView.MainConsoleView;

public class Main {
	public static void main(String[] args) {
		MainConsoleView mainView = new MainConsoleView();
		mainView.initiateConnection(TransportProtocol.TCP);
		System.out.println("Connection Successfull!!!");
		System.exit(0);
	}
}
