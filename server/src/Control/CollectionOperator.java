package Control;

import Exceptions.PermissonDeniedException;
import Exceptions.WorkerNotFoundException;
import WorkerData.Status;
import WorkerData.Worker;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Класс для управления коллекцией. Выполняет все операции с коллекцией.
 */
public class CollectionOperator {

    public static final Logger log = Logger.getLogger("logger");
    private ArrayDeque<Worker> workersCollection = new ArrayDeque<>();
//    private Collection<Worker> col = Collections.synchronizedCollection(new ArrayDeque<>());
//    private ArrayDeque<Worker> workersCollection = (ArrayDeque<Worker>) col;
    private LocalDateTime lastInitialisationTime;
    private Database database;


    public CollectionOperator(Database database) throws IOException, SAXException, ParserConfigurationException {
        this.database = database;
        log.info("Сервер запущен!");
        loadCollection();
    }


    /**
     * @return возвращает время инициализации коллекции
     */
    public LocalDateTime getLastInitialisationTime() {
        return lastInitialisationTime;
    }

    /**
     * Метод запускает парсер, тот вовращает прочитанную из файла коллекцию, время инициализации обновляется.
     */
    private void loadCollection() {
        workersCollection = database.getData();
        sortCollection();
        lastInitialisationTime = LocalDateTime.now();
    }

    /**
     * Метод запускает загрузчик, который записывает коллекцию в файл
     */
    public void saveCollection() {

    }

    /**
     * Метод для прямой сортировки по умолчанию
     */
    public void sortCollection() {

        workersCollection = workersCollection.stream()
                .sorted()
                .collect(Collectors.toCollection(ArrayDeque<Worker>::new));
    }

    /**
     * Метод для обратной сортировки по умолчанию
     */
    public void sortReverseCollection() {

        workersCollection = workersCollection.stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toCollection(ArrayDeque<Worker>::new));
    }

    /**
     * @return возвращает тип коллекции
     */
    public String collectionType() {
        return workersCollection.getClass().getName();
    }

    /**
     * @return возвращает количество элементов коллекции
     */
    public int collectionSize() {
        return workersCollection.size();
    }

    /**
     * @return возвращет коллекцию
     */
    public ArrayDeque<Worker> getWorkersCollection() {
        return  workersCollection;
    }

    /**
     * @return возвращает строку, содержащую информацию обо всех элементах коллекции
     */
    public String workersDesc(){

        StringBuilder deskStr = new StringBuilder();

        workersCollection
                .stream()
                .forEach(worker -> deskStr.append(worker.description()).append("\nСоздатель: "+database.getUserById(worker.getId())).append("\n\n"));

        return deskStr.toString().trim().concat("\n");
    }

    /**
     * Добавляет элемент в коллекцию
     * @param worker элемент для добавления
     */
    public void addToCollection(Worker worker, String currentUser) {
        if(database.addWorker(worker,currentUser)){
            workersCollection = database.getData();
            sortCollection();
        }
    }

    public void updateWorker(Worker worker, String currentUser) throws PermissonDeniedException {

        if(!currentUser.equals(database.getUserById(worker.getId()))) throw new PermissonDeniedException();

        database.updateWorker(worker,currentUser);
        workersCollection = database.getData();
    }

    /**
     * @param id ID рабочего
     * @return вовзращает работника по id
     */
    public Worker getById(long id) {

        return workersCollection.stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * удаляет рабочего из коллекции
     * @param worker работник для удаления
     */
    public void removeFromCollection(Worker worker, String currentUser) throws PermissonDeniedException {
        long id = worker.getId();

        if(!currentUser.equals(database.getUserById(id))) throw new PermissonDeniedException();

        if (database.removeWorker(id)){
            workersCollection = workersCollection
                    .stream()
                    .filter(x->worker.getId() != x.getId())
                    .collect(Collectors.toCollection(ArrayDeque<Worker>::new));
        }
    }

    /**
     * Удаляет все элементы коллекции
     */
    public void clearCollection(String currentUser) {
        for (Worker i: workersCollection){
            if (currentUser.equals(database.getUserById(i.getId())))
            database.removeWorker(i.getId());
        }
        workersCollection= database.getData();
    }

    /**
     * @return возвращает последний элемент коллекции, если он есть
     */
    public Worker getLast() {
        if (workersCollection.isEmpty()) return null;
        return workersCollection.getLast();
    }

    /**
     * @return возвращает первый элемент коллекции, если он есть
     */
    public Worker getFirst() {
        if (workersCollection.isEmpty()) return null;
        return workersCollection.getFirst();
    }

    /**
     * @param statusToFilter статус, по которому будет идти отбор
     * @return возвращает описание элемента с нужным статусом
     */
    public String statusFilteredInfo(Status statusToFilter) {

        StringBuilder deskStr = new StringBuilder();

        workersCollection
                .stream()
                .filter(x -> x.getStatus().equals(statusToFilter))
                .forEach(worker -> deskStr.append(worker.description()).append("\n\n"));

        return deskStr.toString().trim();
    }
}


