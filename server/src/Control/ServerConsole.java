package Control;

import java.util.Scanner;

public class ServerConsole extends Thread{


    private final CommandManager commandManager;

    public ServerConsole(CommandManager commandManager,String name) {
        super(name);
        this.commandManager = commandManager;
    }

    @Override
    public void run() {
        Scanner inputScanner = new Scanner(System.in);


        String[] inputCommand;

        do {
            inputCommand = (inputScanner.nextLine().trim() + " ").split(" ", 2);
            launchCommand(inputCommand);
        } while (true);
    }

    private void launchCommand(String[] userCommand) {
        if (userCommand[0].equals("save")) commandManager.save();

        if(userCommand[0].equals("exit_server")) commandManager.exit();
    }

    public static void println(String toOut){
        System.out.println(toOut);
    }

    public static void print(String toOut){
        System.out.print(toOut);
    }

    public static void printError(String toOut){
        System.err.println(toOut);
    }
}
