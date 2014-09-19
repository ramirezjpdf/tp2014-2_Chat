package br.ufrj.tp.controller;

import br.ufrj.tp.view.ServerView;

public class MainServerController {

	public static void main(String[] args) {
		ServerController controller = new ServerController();
		ServerView view = new ServerView(controller);
		
		view.showView();
	}
}
