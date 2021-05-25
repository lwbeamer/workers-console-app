package Control;

/**
 * Клиентский функциональный интерфейс. Служит для передачи командам аргумента.
 * @param <T> Тип аргумента
 * @param <F> Тип, возвращаемый командой.
 */
public interface Convertible<T,F>{

    T convert(F argument);
}
