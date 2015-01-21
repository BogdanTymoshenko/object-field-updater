package ua.com.amicablesoft.commons.ofu;

/**
 * Created by Tymoshenko Bogdan <bogdan.tymoshenko@gmail.com> on 1/21/15.
 */
public interface ObjectUpdater<T> {
    void update(T origin, T update);
}
