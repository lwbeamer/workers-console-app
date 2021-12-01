package Control;

import Answer.Answer;
import Answer.AnswerStatus;
import WorkerData.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

public class Database {

    public static final Logger log = Logger.getLogger("logger");
    Connection connection = null;
    Statement statement = null;

    public Database() {

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/workers", "postgres", "2281337");
            //connection = DriverManager.getConnection("jdbc:postgresql://localhost:5430/studs", "s******", "password");
            statement = connection.createStatement();

        } catch (SQLException e) {
            log.severe("Ошибка подключения к базе данных");
            System.exit(0);
        } catch (ClassNotFoundException e){
            log.severe("Не найден PostgreSQL JDBC Driver");
        }
        log.info("Подключено к БД");
    }

    public synchronized Answer signUp(String login, String password){
        try{
            ResultSet resultSet = statement.executeQuery("SELECT * FROM table_users;");
            String existingLogin = "";
            while (resultSet.next()){
                existingLogin = resultSet.getString("login");
                if (login.equals(existingLogin)){
                    return new Answer("Такой логин уже зарегистрирован!", AnswerStatus.ERROR);
                }
            }

            byte[] passwordData = password.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-384");
            byte[] digest = messageDigest.digest(passwordData);

            PreparedStatement userToInsert = connection.prepareStatement("insert into table_users (login, password) values (?, ?);");
            userToInsert.setString(1,login);
            userToInsert.setBytes(2,digest);
            userToInsert.execute();
            return  new Answer("Новый пользователь "+login+" успешно зарегестрирован",AnswerStatus.OK);

        } catch (SQLException | NoSuchAlgorithmException e) {
            return new Answer("Непредвиденная ошибка!",AnswerStatus.ERROR);
        }
    }

    public synchronized Answer signIn(String login, String password){
        try{
            ResultSet resultSet = statement.executeQuery("SELECT * FROM table_users;");
            String existingLogin = "";

            byte[] existingPassword = null;

            while (resultSet.next()){
                existingLogin = resultSet.getString("login");
                existingPassword = resultSet.getBytes("password");
                if (login.equals(existingLogin)) break;
            }

            if (!login.equals(existingLogin)){
                return new Answer("Пользователь с таким логином не зарегистрирован!",AnswerStatus.ERROR);
            }

            byte[] passwordData = password.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-384");
            byte[] digest = messageDigest.digest(passwordData);


            if (!Arrays.equals(existingPassword, digest)){
                return new Answer("Пароль неверный!",AnswerStatus.ERROR);
            }

            return new Answer("Вход выполнен успешно!", AnswerStatus.OK);

        } catch (SQLException | NoSuchAlgorithmException e) {
            return new Answer("Непредвиденная ошибка!",AnswerStatus.ERROR);
        }
    }

    public synchronized boolean checkUser(String login, String password){
        try{
            ResultSet resultSet = statement.executeQuery("SELECT * FROM table_users;");
            String existingLogin = "";

            byte[] existingPassword = null;

            while (resultSet.next()){
                existingLogin = resultSet.getString("login");
                existingPassword = resultSet.getBytes("password");
                if (login.equals(existingLogin)) break;
            }

            if (!login.equals(existingLogin)){
                return false;
            }

            byte[] passwordData = password.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-384");
            byte[] digest = messageDigest.digest(passwordData);


            if (!Arrays.equals(existingPassword, digest)){
                return false;
            }

            return true;

        } catch (SQLException | NoSuchAlgorithmException e) {
            return false;
        }
    }



    public synchronized boolean addWorker(Worker worker, String currentUser){
        String name = worker.getName();
        Coordinates coordinates = worker.getCoordinates();
        long coordinateX = coordinates.getX();
        long coordinateY = coordinates.getY();
        ZonedDateTime creationDate = worker.getCreationDate();
        double salary = worker.getSalary();
        Position position = worker.getPosition();
        Status status = worker.getStatus();
        Person person = worker.getPerson();

        LocalDateTime birthday = person.getBirthday();
        Float weight = person.getWeight();
        String passportID = person.getPassportID();
        Location location = person.getLocation();



        try{
            PreparedStatement workerToAdd = connection.prepareStatement(
                    "insert into table_workers (name, coordinates_num, creation_date, salary, position, status, person_num, user_name) values (?, ?, ?, ?, ?, ?, ?, ?);");
            workerToAdd.setString(1,name);
            workerToAdd.setLong(2,addCoordinates(coordinateX,coordinateY));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.of(creationDate.getYear(), creationDate.getMonth(), creationDate.getDayOfMonth(), creationDate.getHour(), creationDate.getMinute());
            String formattedDateTime = dateTime.format(formatter);
            formattedDateTime = formattedDateTime + " " + creationDate.getZone();
            workerToAdd.setString(3, formattedDateTime);

            workerToAdd.setDouble(4,salary);
            workerToAdd.setString(5,position.toString());
            workerToAdd.setString(6,status.toString());

            workerToAdd.setLong(7,addPerson(birthday, weight, passportID, location));

            workerToAdd.setString(8,currentUser);

            workerToAdd.execute();
            log.info("Рабочий добавлен в базу данных");
            return true;

        } catch (SQLException e){
            log.severe("Произошла ошибка в SQL-запросе");
        }
        return false;
    }

    public long addCoordinates(long x, long y){
        try{
            PreparedStatement coordinatesToAdd = connection.prepareStatement(
                    "insert into table_coordinates (x, y) values (?, ?) returning number");
            coordinatesToAdd.setLong(1,x);
            coordinatesToAdd.setLong(2,y);

            if (coordinatesToAdd.execute()){
                ResultSet rs = coordinatesToAdd.getResultSet();
                if (rs.next()){
                    return rs.getLong("number");
                }
            }

        } catch (SQLException e){
            log.severe("Произошла ошибка в SQL-запросе");
        }

        return -1;
    }


    public long addPerson(LocalDateTime birthday, Float weight, String passportID, Location location){
        try{
            PreparedStatement personToAdd = connection.prepareStatement(
                    "insert into table_person(birthday,weight,passport_id,location_num) values (?, ?, ?, ?) returning number");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.of(birthday.getYear(), birthday.getMonth(), birthday.getDayOfMonth(), birthday.getHour(), birthday.getMinute());
            String formattedDateTime = dateTime.format(formatter);
            personToAdd.setString(1, formattedDateTime);

            if (weight != null)
                personToAdd.setFloat(2,weight);
            else
                personToAdd.setNull(2,Types.REAL);

            personToAdd.setString(3,passportID);

            if (location != null){
                personToAdd.setLong(4,addLocation(location.getX(),location.getY(),location.getZ(),location.getName()));
            } else
                personToAdd.setNull(4,Types.BIGINT);

            if (personToAdd.execute()){
                ResultSet rs = personToAdd.getResultSet();
                if (rs.next()){
                    return rs.getInt("number");
                }
            }

        } catch (SQLException e) {
            log.severe("Произошла ошибка в SQL-запросе");
        }
        return -1;
    }


    public long addLocation(double x, Float y, double z, String name){
        try{
            PreparedStatement locationToAdd = connection.prepareStatement(
                    "insert into table_location(x, y, z, name) values (?, ?, ?, ?) returning number");

            locationToAdd.setDouble(1,x);
            locationToAdd.setFloat(2,y);
            locationToAdd.setDouble(3,z);

            if (name != null)
                locationToAdd.setString(4,name);
            else
                locationToAdd.setNull(4,Types.FLOAT);


            if (locationToAdd.execute()){
                ResultSet rs = locationToAdd.getResultSet();
                if (rs.next()){
                    return rs.getInt("number");
                }
            }

        } catch (SQLException e) {
            log.severe("Произошла ошибка в SQL-запросе");
        }
        return -1;
    }

    public synchronized ArrayDeque<Worker> getData(){
        try{
            ArrayDeque<Worker> workers = new ArrayDeque<>();
            long id;
            String name;
            Coordinates coordinates;
            ZonedDateTime creationDate;
            double salary;
            Position position;
            Status status;
            Person person;

            ResultSet workersSet = statement.executeQuery("select * from table_workers;");
            while (workersSet.next()) {
                id = workersSet.getLong("id");
                name = workersSet.getString("name");
                coordinates = getCoordinates(workersSet.getLong("coordinates_num"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm VV");
                creationDate = ZonedDateTime.parse(workersSet.getString("creation_date"), formatter);
                salary = workersSet.getDouble("salary");
                position = Position.valueOf(workersSet.getString("position"));
                status = Status.valueOf(workersSet.getString("status"));
                person = getPerson(workersSet.getLong("person_num"));

                Worker worker = new Worker(id,name,coordinates,creationDate,salary,position,status,person);
                workers.add(worker);
            }

            workersSet.close();
            return  workers;

        } catch (SQLException e) {
            log.severe("Произошла ошибка в SQL-запросе");
        }

        return new ArrayDeque<>();
    }

    public Coordinates getCoordinates(long coordinatesNumber){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM table_coordinates WHERE number = ?");
            preparedStatement.setLong(1, coordinatesNumber);

            if (preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()){
                    long x = resultSet.getLong("x");
                    long y = resultSet.getLong("y");
                    Coordinates coordinates = new Coordinates(x,y);
                    return coordinates;
                }
            }

        } catch (SQLException e) {
            log.severe("Произошла ошибка в SQL-запросе");
        }
        return null;
    }

    public Person getPerson(long personNumber){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM table_person WHERE number = ?");
            preparedStatement.setLong(1,personNumber);

            if (preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime birthday = LocalDateTime.parse(resultSet.getString("birthday"), formatter);
                    Float weight = resultSet.getFloat("weight");
                    String passportID = resultSet.getString("passport_id");
                    Location location = getLocation(resultSet.getLong("location_num"));
                    Person person = new Person(birthday,weight,passportID,location);
                    return person;
                }
            }


        } catch (SQLException e) {
            log.severe("Произошла ошибка в SQL-запросе");
        }

        return null;
    }

    public Location getLocation(long locationNumber){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM table_location WHERE number = ?");
            preparedStatement.setLong(1,locationNumber);

            if (preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()){
                    double x = resultSet.getDouble("x");
                    Float y = resultSet.getFloat("y");
                    double z = resultSet.getDouble("z");
                    String name = resultSet.getString("name");

                    Location location = new Location(x,y,z,name);
                    return location;
                }
            }


        } catch (SQLException e) {
            log.severe("Произошла ошибка в SQL-запросе");
        }

        return null;
    }

    public synchronized boolean removeWorker(long id){

        Long coordinatesNumber = null;
        Long personNumber = null;
        Long locationNumber = null;


        try{
            PreparedStatement preparedStatement = connection.prepareStatement("select * from table_workers where id = ?");
            preparedStatement.setLong(1,id);

            if (preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()){
                    coordinatesNumber = resultSet.getLong("coordinates_num");
                    personNumber = resultSet.getLong("person_num");
                }
            }

            preparedStatement = connection.prepareStatement("SELECT * FROM table_person WHERE number = ?");
            preparedStatement.setLong(1,personNumber);

            if(preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()){
                    locationNumber = resultSet.getLong("location_num");
                }
            }

            preparedStatement = connection.prepareStatement("DELETE FROM table_workers WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("DELETE FROM table_coordinates WHERE number = ?");
            preparedStatement.setLong(1, coordinatesNumber);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("DELETE FROM table_person WHERE number = ?");
            preparedStatement.setLong(1, personNumber);
            preparedStatement.execute();

            if (locationNumber != null) {
                preparedStatement = connection.prepareStatement("DELETE FROM table_location WHERE number = ?");
                preparedStatement.setLong(1, locationNumber);
                preparedStatement.execute();
            }

            return true;
        } catch (SQLException e) {
            log.severe("Произошла ошибка в SQL-запросе");
        }
        return false;
    }

    public synchronized void updateWorker(Worker worker, String currentUser){
        long idToAdd = worker.getId();
        removeWorker(idToAdd);
        addWorker(worker,currentUser);

        try {
            List<Long> listOfId = getIdData();
            long idToRemove = Collections.max(listOfId);
            PreparedStatement preparedStatement = connection.prepareStatement("update table_workers set id = ? where id = ?;");
            preparedStatement.setLong(1,idToAdd);
            preparedStatement.setLong(2,idToRemove);
            preparedStatement.execute();
        } catch (SQLException e) {
            log.severe("Произошла ошибка в SQL-запросе");
        }

    }

    public synchronized List<Long> getIdData(){
        List<Long> list = new ArrayList<>();
        try {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM table_workers;");

            while (resultSet.next()) {
                list.add(resultSet.getLong("id"));
            }

            return list;
        } catch (SQLException e){
            log.severe("Произошла ошибка в SQL-запросе");
        }
        return list;
    }

    public synchronized String getUserById(long id){
        String user = null;

        try{
            PreparedStatement preparedStatement = connection.prepareStatement("select user_name from table_workers where id =  ?");
            preparedStatement.setLong(1,id);

            if(preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()){
                    user = resultSet.getString("user_name");
                    return user;
                }
            }

        } catch (SQLException e) {
            log.severe("Произошла ошибка в SQL-запросе");
        }

        return null;
    }



}
