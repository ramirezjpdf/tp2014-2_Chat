package br.ufrj.tp.view.consoleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.ufrj.tp.controller.MainController;
import br.ufrj.tp.utils.SocketUtils;

public class MainConsoleView {
	private Scanner scan;
	private MainController controller;
	private List<String> usernameList;
	
	public MainConsoleView(){
		this.scan = new Scanner(System.in);
		this.controller = new MainController();
		this.usernameList = new ArrayList<String>();
	}
	
	public void initiateConnection(SocketUtils.TransportProtocol transpProtocol){
		System.out.println("Enter with your username: ");
		String username = scan.nextLine();
		controller.initiateConnection(username, transpProtocol);
		usernameList = controller.getClientsUsernames();
		System.out.println("Welcome to your chat!!\n");
	}
	
	public void runMainMenu(){
		while(true){
			printMenu(usernameList);
			String option = scan.nextLine();
			if(option.equalsIgnoreCase("a")){
				System.out.println("Choose a friend from the list by its number");
				int index = scan.nextInt();
				while(index < 0 || index > usernameList.size()){
					System.out.println("Invalid number. Try another, please");
					index = scan.nextInt();
				}
				controller.iniciateChatWith(usernameList.get(index));
			}
			else if(option.equalsIgnoreCase("b")){
				System.out.println("Leaving Chat...");
				System.out.println("Closing connections...");
				System.exit(0);
			}
			
		}
	}

	private void printMenu(List<String> usernameList) {
		System.out.println("These are your online friends!");
		System.out.println("------------------------------");
		int i = 1;
		for(String username : usernameList){
			System.out.println(i + ") " + username);
		}
		System.out.println("------------------------------\n");
		
		System.out.println("MENU");
		System.out.println("a) Initiate a chat with someone");
		System.out.println("b) Leave Chat");
		
	}
	
	
}
