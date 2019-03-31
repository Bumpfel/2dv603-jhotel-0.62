package functions;

import java.util.ArrayList;

import functions.Action;
import functions.Observer;

public class ObservableThread extends Thread {

    private ArrayList<Observer> subscribers = new ArrayList<>();

    public void addSubscriber(Observer o) {
        subscribers.add(o);
    }

    public void notifySubscribers(Observer obs, Object args, Action action) { //TODO shouldnt really be public
        for(Observer o : subscribers) {
            o.update(obs, args, action);
        }
    }
}