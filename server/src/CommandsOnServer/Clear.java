package CommandsOnServer;


import Answer.Answer;
import Answer.AnswerStatus;
import Control.CollectionOperator;
import Control.Database;
import Control.Sender;
import Exceptions.PermissonDeniedException;

/**
 * Команда "add". Добавляет элемент в коллекцию, узнав у пользователя все нужные данные
 */
public class Clear implements Executable{

    private final CollectionOperator collectionOperator;
    private final Sender sender;
    private final Database database;


    public Clear(CollectionOperator collectionOperator, Sender sender, Database database) {
        this.collectionOperator = collectionOperator;
        this.sender = sender;
        this.database = database;
    }



    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description() {
        return "clear : очистить коллекцию";
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

            collectionOperator.clearCollection(currentUser);
            sender.send(new Answer("Коллекция очищена!", AnswerStatus.OK));
        } catch (PermissonDeniedException e){
            sender.send(new Answer("У вас нет прав для выполнения данной операции!",AnswerStatus.ERROR));
        }
    }
}
