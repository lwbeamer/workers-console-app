package WorkerData;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс, содержащий личные данные работника
 */
public class Person implements Serializable {
    private  LocalDateTime birthday; //Поле не может быть null
    private  Float weight; //Поле может быть null, Значение поля должно быть больше 0
    private  String passportID; //Поле не может быть null
    private Location location; //Поле может быть null

    public Person(LocalDateTime birthday, Float weight, String passportID, Location location) {
        this.birthday = birthday;
        this.weight = weight;
        this.passportID = passportID;
        this.location = location;
    }

    public Person(LocalDateTime birthday, Float weight, String passportID) {
        this.birthday = birthday;
        this.weight = weight;
        this.passportID = passportID;
    }

    public Person(Float weight, String passportID) {
        this.weight = weight;
        this.passportID = passportID;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return возвращает описание координат в строковом представлении
     */
    public String description(){
        String info = "";
        info += "\nДата рождения: " + birthday.toLocalDate() + " " + birthday.toLocalTime();
        if (weight != null) info += "\nВес: " + weight;
        info += "\nНомер паспорта: " + passportID;
        if (location != null) info += "\nМесто жительства: " + location.description();
        return info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(birthday, person.birthday) && Objects.equals(weight, person.weight) && Objects.equals(passportID, person.passportID) && Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthday, weight, passportID, location);
    }

    /**
     * @return возвращает дату рождения
     */
    public LocalDateTime getBirthday() {
        return birthday;
    }

    /**
     * @return возвращает вес
     */
    public Float getWeight() {
        return weight;
    }

    /**
     * @return возвращает номер паспорта
     */
    public String getPassportID() {
        return passportID;
    }

    /**
     * @return возвращает Location
     */
    public Location getLocation() {
        return location;
    }
}
