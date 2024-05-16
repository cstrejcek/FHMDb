package at.ac.fhcampuswien.fhmdb;

import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object> {
    // Singleton instance of controller
    private HomeController homeControllerInstance;

    @Override
    public Object call(Class<?> aClass) {
        if (homeControllerInstance == null) {
            try {
                homeControllerInstance = (HomeController) aClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return homeControllerInstance;
    }
}
