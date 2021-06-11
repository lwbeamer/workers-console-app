package CommandsOnServer;

import Control.Database;
import Control.Sender;

public class SignIn implements Executable{

    private Database database;
    private Sender sender;

    public SignIn(Database database, Sender sender) {
        this.database = database;
        this.sender = sender;
    }

    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description(){
        return "sign_in login password: авторизироваться";
    }

    @Override
    public void execute(Object argument, String currentUser, String currentPassword) {
        String[] args = argument.toString().split(" ",2);
        sender.send(database.signIn(args[0],args[1]));
    }
}