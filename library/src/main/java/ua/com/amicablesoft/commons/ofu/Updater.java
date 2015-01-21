package ua.com.amicablesoft.commons.ofu;

/**
 * Created by Tymoshenko Bogdan <bogdan.tymoshenko@gmail.com> on 1/21/15.
 */
public final class Updater {

    private Updater() {}

    public static <T> void update(T origin, T update) {
        String clsName = origin.getClass().getName();
        try {
            Class<?> updaterClass = Class.forName(clsName + "Updater");
            ObjectUpdater updater = (ObjectUpdater) updaterClass.newInstance();
            updater.update(origin,update);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
