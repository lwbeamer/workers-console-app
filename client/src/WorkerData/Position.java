package WorkerData;

import java.io.Serial;
import java.io.Serializable;

/**
 * Enum, содержащий должности работника
 */
public enum Position implements Serializable {
    HUMAN_RESOURCES,
    HEAD_OF_DIVISION,
    CLEANER;


    /**
     * Возвращает список возможных должностей через запятую
     * @return строку - описание
     */
    public static String getValues() {
        String s = "";
        for (Position position : values()) {
            s += position.name() + ", ";
        }
        return s.substring(0, s.length()-2); //удаляем два ненужных символа в конце
    }
}
