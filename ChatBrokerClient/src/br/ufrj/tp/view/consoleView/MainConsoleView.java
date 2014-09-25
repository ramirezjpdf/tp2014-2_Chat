package br.ufrj.tp.view.consoleView;

import java.util.Scanner;

import br.ufrj.tp.controller.MainController;

public class MainConsoleView {
	private Scanner scan;
	private MainController controller;
	
	public MainConsoleView(){
		this.scan = new Scanner(System.in);
		this.controller = new MainController();
	}
	
	public void initiateConnection(){
		System.out.println("Enter with your username: ");
		String username = scan.nextLine();
		
	}
}
