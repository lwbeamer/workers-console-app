package CommandsOnServer;


import Answer.Answer;
import Answer.AnswerStatus;
import Control.CollectionOperator;
import Control.Database;
import Control.Sender;
import Exceptions.EmptyCollectionException;
import Exceptions.PermissonDeniedException;

/**
 * Команда "show". Выводит в консоль информацию обо всех элементах коллекции
 */
public class Show implements Executable{

    private final CollectionOperator collectionOperator;
    private final Sender sender;
    private final Database database;


    public Show(CollectionOperator collectionOperator, Sender sender, Database database){
        this.sender = sender;
        this.collectionOperator = collectionOperator;
        this.database = database;
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
    public void execute(Object argument, String currentUser, String currentPassword) {
        try {
            if (!database.checkUser(currentUser,currentPassword)) throw new PermissonDeniedException();
            if (collectionOperator.collectionSize() == 0) throw new EmptyCollectionException();
            sender.send(new Answer(collectionOperator.workersDesc(),AnswerStatus.OK));
        }  catch (EmptyCollectionException e) {
            sender.send(new Answer("Коллекция пуста!", AnswerStatus.ERROR));
        } catch (PermissonDeniedException e){
            sender.send(new Answer("У вас нет прав для выполнения данной операции!",AnswerStatus.ERROR));
        }
    }
}
