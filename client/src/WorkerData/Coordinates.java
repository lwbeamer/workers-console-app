package WorkerData;

import java.io.Serializable;

/**
 * Класс для хранения координат работника
 */
public class Coordinates implements Serializable {
    private  long x;
    private  long y;

    public Coordinates() {
    }

    public Coordinates(long x, long y) {
        this.x = x;
        this.y = y;
    }


    public void setX(long x) {
        this.x = x;
    }

    public void setY(long y) {
        this.y = y;
    }

    /**
     * @return возвращает описание координат в строковом представлении
     */
    public String description() {
        return "X:" + x + " Y:" + y;
    }

    /**
     * @return координата Х
     */
    public long getX() {
        return x;
    }

    /**
     * @return координата Y
     */
    public long getY() {
        return y;
    }
}
