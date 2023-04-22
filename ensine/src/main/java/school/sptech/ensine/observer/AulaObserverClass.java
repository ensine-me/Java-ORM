package school.sptech.ensine.observer;

import java.util.ArrayList;
import java.util.List;

public class AulaObserverClass {
    private static List<AulaObserver> observers = new ArrayList<>();

    public void addObserver(AulaObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(AulaObserver observer) {
        observers.remove(observer);
    }

    public static List<AulaObserver> getObservers() {
        return observers;
    }

    public void setObservers(List<AulaObserver> observers) {
        this.observers = observers;
    }
}
