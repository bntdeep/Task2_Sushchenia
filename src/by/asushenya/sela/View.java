package by.asushenya.sela;


import by.asushenya.sela.controller.Controller;

public class View {

	public static void main(String[] args) {
	
		Controller controller = new Controller();
		
		System.out.println(controller.executeTask("REGISTERED_NEW_USER Artyom 1111")); 
		System.out.println(controller.executeTask("SIGN_IN Artyom 1111")); 
		System.out.println(controller.executeTask("ADD_NEW_EQUIPMENT racket tennis 30 10")); 
		System.out.println(controller.executeTask("GET_ALL_EQUIPMENT ")); 
		System.out.println(controller.executeTask("RENT_EQUIPMENT 5 8 30 2 ")); 
		System.out.println(controller.executeTask("GET_OWER_REPORT ")); 
		System.out.println(controller.executeTask("THIS_COMMAND_IS_NOT_EXIST ")); 
		
	}

}
