package CommandsOnServer;

import Answer.Answer;
import Control.Database;
import Control.Sender;

public class SignUp implements Executable{

    private Database database;
    private Sender sender;

    public SignUp(Database database, Sender sender) {
        this.database = database;
        this.sender = sender;
    }

    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description(){
        return "sign_up login password: зарегистрировать нового пользователя";
    }


    @Override
    public void execute(Object argument, String currentUser) {
        String[] args = argument.toString().split(" ",2);
        sender.send(database.signUp(args[0],args[1]));
    }
}
