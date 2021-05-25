package WorkerData;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Класс работника
 */
public class Worker implements Comparable<Worker>, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double salary; //Значение поля должно быть больше 0
    private Position position; //Поле не может быть null
    private Status status; //Поле не может быть null
    private Person person; //Поле не может быть null

    public Worker(long id, String name, Coordinates coordinates, ZonedDateTime creationDate, double salary, Position position, Status status, Person person) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.salary = salary;
        this.position = position;
        this.status = status;
        this.person = person;
    }

    public Worker(Coordinates coordinates, Person person) {
        this.coordinates = coordinates;
        this.person = person;
    }

    public Worker(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Worker(){}

    public void setId(long id) {
        this.id = id;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public double getSalary() {
        return salary;
    }

    public Position getPosition() {
        return position;
    }

    public Status getStatus() {
        return status;
    }

    public Person getPerson() {
        return person;
    }


    /**
     * @return возвращает описание работника в строковом представлении
     */
    public String description() {
        String info = "";
        info += "Рабочий №" + id;
        info += "\nДата создания: " + creationDate.toLocalDate() + " " + creationDate.toLocalTime() + " " + creationDate.getZone();
        info += "\nИмя: " + name;
        info += "\nМестоположение: " + coordinates.description();
        info += "\nЗарплата: " + salary;
        info += "\nДолжность: " + position;
        info += "\nСтатус: " + status;
        info += person.description();
        return info;
    }


    /**
     * Сортировка по умолчанию. Переопределена для сортировки по зарплате:
     * @param o - объект для сравнения
     * @return возвращает число. На основании его знака выполняется сортировка
     */
    @Override
    public int compareTo(Worker o) {
        return (int) (this.salary - o.salary);
    }
}
