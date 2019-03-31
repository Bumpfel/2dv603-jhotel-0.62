package functions;

import java.awt.Frame;
import java.util.ArrayList;

import functions.Action;
import functions.Observer;

@SuppressWarnings("serial")
public class ObservableFrame extends Frame {

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