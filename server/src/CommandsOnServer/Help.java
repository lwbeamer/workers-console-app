package CommandsOnServer;


import Answer.Answer;
import Answer.AnswerStatus;
import Control.Sender;

import java.io.Serializable;


/**
 * Команда "help". Класс отвечает за вывод всех описаний команд в консоль.
 */
public class Help implements Executable, Serializable {

    Sender sender;

    public Help(Sender sender) {
        this.sender = sender;
    }

    /**
     * Выполнение команды
     * @param argument - аргумент команды
     * @return Статус выполнения команды
     */
    @Override
    public void execute(Object argument, String currentUser) {

        String info = Help.description() + "\n" +
                Info.description() + "\n" +
                Show.description() + "\n" +
                Add.description() + "\n" +
                Update.description() + "\n" +
                RemoveById.description() + "\n" +
                Clear.description() + "\n" +
                ExecuteScript.description() + "\n" +
                Exit.description() + "\n" +
                RemoveHead.description() + "\n" +
                AddIfMax.description() + "\n" +
                AddIfMin.description() + "\n" +
                RemoveAllByPerson.description() + "\n" +
                FilterByStatus.description() + "\n" +
                PrintFieldDescendingStatus.description() + "\n" +
                SignUp.description() + "\n" +
                SignIn.description() + "\n";
        sender.send(new Answer(info, AnswerStatus.OK));
    }

    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description() {
        return "help : вывести справку по доступным командам";
    }
}
