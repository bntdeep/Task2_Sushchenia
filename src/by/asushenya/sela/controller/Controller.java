package by.asushenya.sela.controller;

import by.asushenya.sela.controller.command.Command;

public class Controller {
	private final CommandProvider provider = new CommandProvider();
	
	private final char paramDelimeter = ' ';
	
	public String executeTask(String request){
		String commandName = null;
		Command executionCommand = null;
		
		String response;
		
		try{
			commandName = request.substring(0,request.indexOf(paramDelimeter));
			
		} catch(IndexOutOfBoundsException e){
			response = "incorrect request (controller)";
			return response+ ": " +e.getMessage();// не надо пользователю сознательно отправлять сообщения, содержащиеся в объектах-исключениях
			// они и так могут многое поведать о твоем приложении
		}
				
		executionCommand = provider.getCommand(commandName);
		
		try{
			response = executionCommand.execute(request);
			
		} catch(NullPointerException e){// перехват NullPointer, мама, роди меня обратно
			
			response = "NullPointer excecution command (controller)";
		}	
		
		return response;
	}
}
