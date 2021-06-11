package CommandsOnServer;


import Answer.Answer;
import Answer.AnswerStatus;
import Control.CollectionOperator;
import Control.Database;
import Control.Sender;
import Exceptions.EmptyCollectionException;
import Exceptions.PermissonDeniedException;

/**
 * Команда "remove_head". Удаляет первый элемент коллекции, перед этим выводит его в консоль.
 */
public class RemoveHead implements Executable{



    private final CollectionOperator collectionOperator;
    private final Sender sender;
    private final Database database;


    public RemoveHead(CollectionOperator collectionOperator, Sender sender, Database database) {
        this.collectionOperator = collectionOperator;
        this.sender = sender;
        this.database = database;
    }

    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description(){
        return "remove_head : вывести первый элемент коллекции и удалить его";
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
            sender.send(new Answer(collectionOperator.getWorkersCollection().getFirst().description(), AnswerStatus.OK));
            collectionOperator.removeFromCollection(collectionOperator.getWorkersCollection().getFirst(),currentUser);
        }  catch (EmptyCollectionException e){
            sender.send(new Answer("Коллекция пуста!",AnswerStatus.ERROR));
        } catch (PermissonDeniedException e){
            sender.send(new Answer("У вас нет прав для выполнения данной операции!",AnswerStatus.ERROR));
        }
    }
}
