package CommandsOnServer;

import Answer.Answer;
import Answer.AnswerStatus;
import Control.CollectionOperator;
import Control.Database;
import Control.Sender;

import Exceptions.PermissonDeniedException;
import WorkerData.Worker;



/**
 * Команда "add". Добавляет элемент в коллекцию, узнав у пользователя все нужные данные
 */
public class Add implements Executable{


    private final CollectionOperator collectionOperator;
    private final Sender sender;
    private final Database database;


    public Add(CollectionOperator collectionOperator, Sender sender, Database database) {
        this.collectionOperator = collectionOperator;
        this.sender = sender;
        this.database = database;
    }

    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description() {
        return "add {element} : добавить новый элемент в коллекцию";
    }


    @Override
    public void execute(Object argument, String currentUser, String currentPassword) {
        try {
            if (!database.checkUser(currentUser,currentPassword)) throw new PermissonDeniedException();
            Worker workerToAdd = (Worker) argument;
            long checkNew = workerToAdd.getId();

            if (checkNew == 0) {
                collectionOperator.addToCollection(workerToAdd, currentUser);
                collectionOperator.sortCollection();
                sender.send(new Answer("Рабочий успешно добавлен!", AnswerStatus.OK));
            } else {
                collectionOperator.updateWorker(workerToAdd, currentUser);
                collectionOperator.sortCollection();
                sender.send(new Answer("Рабочий успешно обновлён!", AnswerStatus.OK));
            }
        } catch (PermissonDeniedException e){
            sender.send(new Answer("У вас нет прав для выполнения данной операции!",AnswerStatus.ERROR));
        }
    }
}
