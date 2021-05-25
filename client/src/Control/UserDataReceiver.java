package Control;

import WorkerData.*;
import Exceptions.ValueIsEmptyException;
import Exceptions.ValueOutOfRangeException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;


/**
 * Класс нужен для обработки данных пользователя при вводе информации об элементах коллекции. Например для работы команды add, update и т.д.
 */
public class UserDataReceiver {

    private Scanner inputScanner;
    private boolean scriptMode;


    public UserDataReceiver(Scanner inputScanner) {
        this.inputScanner = inputScanner;
        scriptMode = false;
    }

    /**
     * Установка режима работы скрипта
     */
    public void setScriptMode() {
        scriptMode = true;
    }

    /**
     * Установка нормального режима работы
     */
    public void setNormalMode() {
        scriptMode = false;
    }

    /**
     * Метод спрашивает у пользователя имя работника
     * @return возвращает введённое имя
     * чтобы используемая команда в скриптовом режиме вернула код ошибки
     */
    public String askName(){
        String name;
        while (true) {
            try {
                Outputer.println("Введите имя рабочего:");
                name = inputScanner.nextLine().trim();
                if (scriptMode) Outputer.println(name);
                if (name.equals("")) throw new ValueIsEmptyException();
                break;
            } catch (ValueIsEmptyException e) {
                Outputer.printError("Имя не может быть пустым!");
            }
        }
        return name;
    }

    /**
     * Метод спрашивает у пользователя координаты
     * @return возвращает введённые координаты
     */
    public Coordinates askCoordinates() {
        long x;
        long y;
        x = askX();
        y = askY();
        return new Coordinates(x, y);
    }

    /**
     * @return возвращает координату X
     */
    public long askX(){
        String strX;
        long x;
        while (true) {
            try {
                Outputer.println("Введите координату X:");
                strX = inputScanner.nextLine().trim();
                if (strX.equals("")) throw new ValueIsEmptyException();
                if (scriptMode) Outputer.println(strX);
                x = Long.parseLong(strX);
                break;
            } catch (NumberFormatException e) {
                Outputer.printError("Координата X должна быть представлена целым числом!");
            } catch (ValueIsEmptyException e){
                Outputer.printError("Поле не может быть пустым");
            }
        }
        return x;
    }

    /**
     * @return возвращает координату Y
     */
    public long askY(){
        String strY;
        long y;
        while (true) {
            try {
                Outputer.println("Введите координату Y:");
                strY = inputScanner.nextLine().trim();
                if (strY.equals("")) throw new ValueIsEmptyException();
                if (scriptMode) Outputer.println(strY);
                y = Long.parseLong(strY);
                break;
            } catch (NumberFormatException e) {
                Outputer.printError("Координата Y должна быть представлена целым числом!");
            } catch (ValueIsEmptyException e){
                Outputer.printError("Поле не может быть пустым");
            }
        }
        return y;
    }

    /**
     * Метод спрашивает зарплату работника
     * @return возвращает зарплату
     */
    public double askSalary() {
        String strSalary;
        double salary;
        while (true) {
            try {
                Outputer.println("Введите зарплату:");
                strSalary = inputScanner.nextLine().trim();
                if (strSalary.equals("")) throw new ValueIsEmptyException();
                if (scriptMode) Outputer.println(strSalary);
                salary = Double.parseDouble(strSalary);
                if (salary <= 0) throw new ValueOutOfRangeException();
                break;
            } catch (ValueOutOfRangeException e) {
                Outputer.printError("Зарплата должна быть больше нуля!");
            } catch (NumberFormatException e) {
                Outputer.printError("Зарплата должна быть представлена числом!");
            } catch (ValueIsEmptyException e){
                Outputer.printError("Поле не может быть пустым");
            }
        }
        return salary;
    }

    /**
     * Метод спрашивает должность работника
     * @return возвращает должность
     */
    public Position askPosition(){
        String strPosition;
        Position position;
        while (true) {
            try {
                Outputer.println("Список должностей - " + Position.getValues());
                Outputer.println("Введите должность:");
                strPosition = inputScanner.nextLine().trim();
                if (strPosition.equals("")) throw new ValueIsEmptyException();
                if (scriptMode) Outputer.println(strPosition);
                position = Position.valueOf(strPosition.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                Outputer.printError("Такой должности нет в списке!");
            } catch (ValueIsEmptyException e){
                Outputer.printError("Поле не может быть пустым");
            }
        }
        return position;
    }

    /**
     * Метод спрашивает статус работника
     * @return возвращает статус
     */
    public Status askStatus() {
        String strStatus;
        Status status;
        while (true) {
            try {
                Outputer.println("Список статусов - " + Status.getValues());
                Outputer.println("Введите статус:");
                strStatus = inputScanner.nextLine().trim();
                if (strStatus.equals("")) throw new ValueIsEmptyException();
                if (scriptMode) Outputer.println(strStatus);
                status = Status.valueOf(strStatus.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                Outputer.printError("Такого статуса нет в списке!");
            } catch (ValueIsEmptyException e){
                Outputer.printError("Поле не может быть пустым");
            }
        }
        return status;
    }

    /**
     * Метод возвращает личные данные работника. Этот составной класс, поэтому вызываются другие методы, для входящих в него данных
     * @return Person работника
     */
    public Person askPerson() {
        LocalDateTime birthday;
        Float weight;
        String passportID;
        Location location;

        birthday = askBirthday();
        weight = askWeight();
        passportID = askPassportID();
        location = askLocation();

        if (location != null) return new Person(birthday,weight,passportID,location);
        return new Person(birthday,weight,passportID);
    }

    /**
     * @return возвращается дата рождения работника
     */
    public LocalDateTime askBirthday() {
        return LocalDateTime.of(askDate(),askTime());
    }

    /**
     * @return возвращается дата
     */
    public LocalDate askDate(){
        LocalDate localDate;
        String strDate;
        String [] ArrDate;
        while (true){
            try{
                Outputer.println("Введите дату рождения в формате YYYY-MM-DD:");
                strDate = inputScanner.nextLine().trim();
                if (strDate.equals("")) throw new ValueIsEmptyException();
                if (scriptMode) Outputer.println(strDate);
                ArrDate = strDate.split("-");
                localDate = LocalDate.of(Integer.parseInt(ArrDate[0]), Integer.parseInt(ArrDate[1]), Integer.parseInt(ArrDate[2]));
                break;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException exception){
                Outputer.printError("Дата должна быть представлена числом в нужном формате");
            } catch (DateTimeException e){
                Outputer.printError("Похоже, что такой даты не существует в природе, попробуйте ещё раз!");
            } catch (ValueIsEmptyException e){
                Outputer.printError("Поле не может быть пустым");
            }
        }
        return localDate;
    }

    /**
     * @return возвращается время
     */
    public LocalTime askTime() {
        LocalTime localTime;
        String strTime;
        String [] ArrTime;
        while (true){
            try{
                Outputer.println("Введите время рождения в формате HH-MM:");
                strTime  = inputScanner.nextLine().trim();
                if (strTime.equals("")) throw new ValueIsEmptyException();
                if (scriptMode) Outputer.println(strTime);
                ArrTime = strTime.split("-");
                localTime = LocalTime.of(Integer.parseInt(ArrTime[0]), Integer.parseInt(ArrTime[1]));
                break;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException exception){
                Outputer.printError("Время должно быть представлено числом в нужном формате");
            } catch (DateTimeException e){
                Outputer.printError("Похоже, что такого времени не существует в природе, попробуйте ещё раз!");
            } catch (ValueIsEmptyException e){
                Outputer.printError("Поле не может быть пустым");
            }
        }
        return localTime;
    }

    /**
     * @return возвращается вес работника
     */
    public Float askWeight() {
        String strWeight;
        float weight;
        while (true) {
            try {
                Outputer.println("Введите вес:");
                strWeight = inputScanner.nextLine().trim();
                if (scriptMode) Outputer.println(strWeight);
                if (strWeight.equals("")) return null;
                weight = Float.parseFloat(strWeight);
                if (weight <= 0) throw new ValueOutOfRangeException();
                break;
            } catch (ValueOutOfRangeException e) {
                Outputer.printError("Вес должен быть больше нуля!");
            } catch (NumberFormatException e) {
                Outputer.printError("Вес должен быть представлен числом!");
            }
        }
        return weight;
    }

    /**
     * @return возвращается номер паспорта работника
     */
    public String askPassportID() {
        String passportID;
        while (true) {
            try {
                Outputer.println("Введите номер паспорта:");
                passportID = inputScanner.nextLine().trim();
                if (scriptMode) Outputer.println(passportID);
                if (passportID.equals("")) throw new ValueIsEmptyException();
                break;
            } catch (ValueIsEmptyException e) {
                Outputer.printError("Номер паспорта не может быть пустым!");
            }
        }
        return passportID;
    }

    /**
     * Метод спрашивает место жительства работника. Оно может отсутсвовать
     * @return Location работника
     */
    public Location askLocation() {
        if (!askQuestion("Хотите указать местоположение рабочего?")) return null;
        double x;
        Float y;
        double z;
        String name;
        x = askLocationX();
        y = askLocationY();
        z = askLocationZ();
        name = askLocationName();
        return new Location(x,y,z,name);
    }

    /**
     * @return вовзращает координату X места жительства
     */
    public double askLocationX() {
        String strX;
        double x;
        while (true) {
            try {
                Outputer.println("Введите координату X:");
                strX = inputScanner.nextLine().trim();
                if (strX.equals("")) throw new ValueIsEmptyException();
                if (scriptMode) Outputer.println(strX);
                x = Double.parseDouble(strX);
                break;
            } catch (NumberFormatException e) {
                Outputer.printError("Координата X должна быть представлена числом!");
            } catch (ValueIsEmptyException e){
                Outputer.printError("Поле не может быть пустым");
            }
        }
        return x;
    }

    /**
     * @return вовзращает координату Y места жительства
     */
    public Float askLocationY(){
        String strY;
        float y;
        while (true) {
            try {
                Outputer.println("Введите координату Y:");
                strY = inputScanner.nextLine().trim();
                if (strY.equals("")) throw new ValueIsEmptyException();
                if (scriptMode) Outputer.println(strY);
                y = Float.parseFloat(strY);
                break;
            } catch (NumberFormatException e) {
                Outputer.printError("Координата Y должна быть представлена числом!");
            } catch (ValueIsEmptyException e){
                Outputer.printError("Поле не может быть пустым");
            }
        }
        return y;
    }

    /**
     * @return вовзращает координату Z места жительства
     */
    public double askLocationZ(){
        String strZ;
        double z;
        while (true) {
            try {
                Outputer.println("Введите координату Z:");
                strZ = inputScanner.nextLine().trim();
                if (strZ.equals("")) throw new ValueIsEmptyException();
                if (scriptMode) Outputer.println(strZ);
                z = Double.parseDouble(strZ);
                break;
            } catch (NumberFormatException e) {
                Outputer.printError("Координата Z должна быть представлена числом!");
            } catch (ValueIsEmptyException e){
                Outputer.printError("Поле не может быть пустым");
            }
        }
        return z;
    }

    /**
     * @return возвращет название локации
     */
    public String askLocationName(){
        String LocationName;
        Outputer.println("Введите название локации:");
        LocationName = inputScanner.nextLine().trim();
        if (scriptMode) Outputer.println(LocationName);
        if (LocationName.equals("")) return null;
        return LocationName;
    }

    /**
     * Метод задаёт вопрос, на который пользователь отвечает да или нет.
     * @param question - параметр строка, содержащая сам вопрос
     * @return возрващается true или false, в зависимости от ответа
     */
    public boolean askQuestion(String question) {
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                Outputer.println(finalQuestion);
                answer = inputScanner.nextLine().trim();
                if (scriptMode) Outputer.println(answer);
                if (!answer.equals("+") && !answer.equals("-")) throw new ValueOutOfRangeException();
                break;
            }
            catch (ValueOutOfRangeException e) {
                Outputer.printError("Напишите в ответ только '+' или '-'!");
            }
        }
        return answer.equals("+");
    }

    /**
     * @return возвращает сканнер
     */
    public Scanner getInputScanner() {
        return inputScanner;
    }

    /**
     * устанавливает сканнер
     * @param inputScanner - передаётся объект класса Scanner
     */
    public void setInputScanner(Scanner inputScanner) {
        this.inputScanner = inputScanner;
    }
}
