package com.techelevator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {

	private PrintWriter out;
	private Scanner in;
	private boolean mainMenu = true;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while(choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}
	
	public Object getChoiceSiteOptions(Object[] options) {
		Object choice = null;
		while(choice == null) {
			displaySiteOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		if (userInput.equals("Q") || userInput.equals("q") && mainMenu) {
			System.exit(0);
		}
		if (userInput.equals("R") || userInput.equals("r") && !mainMenu) {
			//TODO: Enter logic
		}
		try {
			int selectedOption = Integer.valueOf(userInput);
			if(selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch(NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if(choice == null) {
			out.println("\n*** "+userInput+" is not a valid option ***\n");
		}
		mainMenu = false;
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for(int i = 0; i < options.length; i++) {
			int optionNum = i+1;
			out.println(optionNum+") "+options[i]);
		}
		showQuit();
		showReturnToPreviousMenu();
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}
	
	 private void displaySiteOptions(Object[] options) {
		out.println();
		for(int i = 0; i < options.length; i++) {
			int optionNum = i+1;
			out.println(optionNum+") "+options[i]);
		}
		showQuit();
		showReturnToPreviousMenu();
		out.print("\nWhich site should be reserved (enter 0 to cancel)? >>> ");
		out.flush();
	}
	
	private void showQuit() {
		if(mainMenu) {
			out.print("\nQ) Quit\n");
		}
	}
	
	private void showReturnToPreviousMenu() {
		if(!mainMenu) {
			out.print("\nR) Return to Previous Screen\n");
		}
	}
	
}
