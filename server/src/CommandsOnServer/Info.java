package CommandsOnServer;


import Answer.Answer;
import Answer.AnswerStatus;
import Control.CollectionOperator;
import Control.Database;
import Control.Sender;
import Exceptions.PermissonDeniedException;


import java.time.LocalDateTime;

/**
 * Команда "info". Выводит информацию о коллекции.
 */
public class Info implements Executable{


    private final CollectionOperator collectionOperator;
    private final Sender sender;
    private final Database database;


    public Info(CollectionOperator collectionOperator, Sender sender, Database database) {
        this.collectionOperator = collectionOperator;
        this.sender = sender;
        this.database = database;
    }

    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description(){
        return "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    /**
     * Выполнение команды
     * @param argument - аргумент команды
     * @return Статус выполнения команды
     */
    @Override
    public void execute(Object argument, String currentUser, String currentPassword) {
        try {
            if (!database.checkUser(currentUser, currentPassword)) throw new PermissonDeniedException();

            LocalDateTime lastInitTime = collectionOperator.getLastInitialisationTime();
            String lastInitTimeString;

            if (lastInitTime == null)
                lastInitTimeString = "Инициализации ещё не было";
            else
                lastInitTimeString = lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

            String info = " Тип коллекции: " + collectionOperator.collectionType() + "\n" +
                    " Количество элементов: " + collectionOperator.collectionSize() + "\n" +
                    " Дата последней инициализации: " + lastInitTimeString + "\n";
            sender.send(new Answer(info, AnswerStatus.OK));
        } catch (PermissonDeniedException e){
            sender.send(new Answer("У вас нет прав для выполнения данной операции!",AnswerStatus.ERROR));
        }
    }
}
