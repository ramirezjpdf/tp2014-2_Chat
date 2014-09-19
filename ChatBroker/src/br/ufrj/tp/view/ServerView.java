package br.ufrj.tp.view;

import java.util.Scanner;

import br.ufrj.tp.controller.ServerController;

public class ServerView {

	private ServerController controller;
	private Scanner scn;

	public ServerView(ServerController controller) {
		this.controller = controller;
		this.scn = new Scanner(System.in);
	}
	
	private int showConfigurationSteps() {
		System.out.println("Please, choose the connection type your Chat Server will accept:");
		System.out.println("  1. TCP");
		System.out.println("  2. UDP");
		System.out.println();

		System.out.print("Your option: ");
		
		int connTypeOption = scn.nextInt();
		
		return connTypeOption;
	}

	@Override
	public void finalize() {
		scn.close();
	}
	
	public void showView() {
		
		//TODO: Substituir XPTO pelo nome do Chat (qual será o nome?)
		System.out.println("=====================================");
		System.out.println("XPTO - Your Chat Server - version 1.0");
		System.out.println("=====================================");
		System.out.println();

		System.out.println("Let's begin the one step configuration of your Chat Server...");
		System.out.println();

		int connTypeOption = showConfigurationSteps();

		while (!controller.startServer(connTypeOption)) {
			System.out.println(controller.getMsg());
			System.out.println();
			
			connTypeOption = showConfigurationSteps();
		}
		
		System.out.println(controller.getMsg());
		System.out.println();
	}
}
