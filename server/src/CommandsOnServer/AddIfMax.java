package CommandsOnServer;



import Answer.Answer;

import Answer.AnswerStatus;
import Control.CollectionOperator;
import Control.Sender;
import WorkerData.Worker;



/**
 * Команда "add". Добавляет элемент в коллекцию, узнав у пользователя все нужные данные
 */
public class AddIfMax implements Executable{

    private final CollectionOperator collectionOperator;
    private final Sender sender;


    public AddIfMax(CollectionOperator collectionOperator, Sender sender) {
        this.collectionOperator = collectionOperator;
        this.sender = sender;
    }


    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description() {
        return "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    /**
     * Выполнение команды
     * @param argument - аргумент команды
     * @return Статус выполнения команды
     */
    @Override
    public void execute(Object argument, String currentUser) {
        Worker workerToAdd = (Worker) argument;

        if (collectionOperator.collectionSize() == 0 || workerToAdd.compareTo(collectionOperator.getLast()) > 0) {
            collectionOperator.addToCollection(workerToAdd,currentUser);
            collectionOperator.sortCollection();
            sender.send(new Answer("Рабочий успешно добавлен!", AnswerStatus.OK));
        } else {
            sender.send(new Answer("Значение рабочего меньше, чем значение наибольшего из рабочих!", AnswerStatus.ERROR));
        }
    }
}
