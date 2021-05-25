package WorkerData;

import java.io.Serializable;

/**
 * Enum, содержащий статусы работника
 */
public enum Status implements Serializable {
    HIRED,
    RECOMMENDED_FOR_PROMOTION,
    PROBATION;

    /**
     * Возвращает список возможных статусов через запятую
     * @return строку - описание
     */
    public static String getValues() {
        String s = "";
        for (Status status : values()) {
            s = s + status.name() + ", ";
        }
        return s.substring(0, s.length()-2); //удаляем два ненужных символа в конце
    }
}
