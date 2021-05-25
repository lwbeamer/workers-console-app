package CommandsOnServer;


import Answer.Answer;
import Answer.AnswerStatus;
import Control.CollectionOperator;
import Control.Sender;
import Exceptions.EmptyCollectionException;

/**
 * Команда "show". Выводит в консоль информацию обо всех элементах коллекции
 */
public class Show implements Executable{

    private final CollectionOperator collectionOperator;
    private final Sender sender;


    public Show(CollectionOperator collectionOperator, Sender sender){
        this.sender = sender;
        this.collectionOperator = collectionOperator;
    }

    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description(){
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    /**
     * Выполнение команды
     * @param argument - аргумент команды
     * @return Статус выполнения команды
     */
    @Override
    public void execute(Object argument, String currentUser) {
        try {
            if (collectionOperator.collectionSize() == 0) throw new EmptyCollectionException();
            sender.send(new Answer(collectionOperator.workersDesc(),AnswerStatus.OK));
        }  catch (EmptyCollectionException e) {
            sender.send(new Answer("Коллекция пуста!", AnswerStatus.ERROR));
        }
    }
}
