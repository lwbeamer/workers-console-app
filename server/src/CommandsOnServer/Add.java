package CommandsOnServer;

import Answer.Answer;
import Answer.AnswerStatus;
import Control.CollectionOperator;
import Control.Sender;

import Exceptions.PermissonDeniedException;
import WorkerData.Worker;



/**
 * Команда "add". Добавляет элемент в коллекцию, узнав у пользователя все нужные данные
 */
public class Add implements Executable{


    private final CollectionOperator collectionOperator;
    private final Sender sender;


    public Add(CollectionOperator collectionOperator, Sender sender) {
        this.collectionOperator = collectionOperator;
        this.sender = sender;
    }

    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description() {
        return "add {element} : добавить новый элемент в коллекцию";
    }


    @Override
    public void execute(Object argument, String currentUser) {
        Worker workerToAdd = (Worker) argument;
        long checkNew = workerToAdd.getId();

        try {
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
