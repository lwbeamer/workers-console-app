package CommandsOnServer;


import Answer.Answer;
import Answer.AnswerStatus;
import Control.CollectionOperator;
import Control.Database;
import Control.Sender;
import Exceptions.EmptyCollectionException;
import Exceptions.PermissonDeniedException;
import WorkerData.Person;
import WorkerData.Worker;

import java.util.ArrayList;

/**
 * Команда "remove_all_by_person". Удаляет все элементы, у которых поле Person совпадает с введённым.
 */
public class RemoveAllByPerson implements Executable{


    private final CollectionOperator collectionOperator;
    private final Sender sender;
    private final Database database;


    public RemoveAllByPerson(CollectionOperator collectionOperator, Sender sender, Database database) {
        this.collectionOperator = collectionOperator;
        this.sender = sender;
        this.database = database;
    }

    
    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description(){
        return "remove_all_by_person person : удалить из коллекции все элементы, значение поля person которого эквивалентно заданному";
    }

    /**
     * Выполнение команды
     * @param argument - аргумент команды
     * @return Статус выполнения команды
     */
    @Override
    public void execute(Object argument, String currentUser, String currentPassword) {
        try{
            boolean check = false;
            ArrayList<Long> listId = new ArrayList<>();
            if (!database.checkUser(currentUser,currentPassword)) throw new PermissonDeniedException();
            if (collectionOperator.collectionSize() == 0) throw new EmptyCollectionException();
            Person personToRemove = (Person) argument;
            for (Worker worker: collectionOperator.getWorkersCollection()){
                if (worker.getPerson().equals(personToRemove)){
                    listId.add(worker.getId());
                    check = true;
                }
            }
            for (Long id: listId){
                collectionOperator.removeFromCollection(collectionOperator.getById(id),currentUser);
            }
            if (check) {
                sender.send(new Answer("Рабочие успешно удалены!",AnswerStatus.OK));
            }
            else {
                sender.send(new Answer("Рабочих с такими личными данными не найдено!",AnswerStatus.OK));
            }
            } catch (EmptyCollectionException e) {
                sender.send(new Answer("Коллекция пуста!", AnswerStatus.ERROR));
            } catch (PermissonDeniedException e){
            sender.send(new Answer("У вас нет прав для выполнения данной операции!",AnswerStatus.ERROR));
            }
        }
}
