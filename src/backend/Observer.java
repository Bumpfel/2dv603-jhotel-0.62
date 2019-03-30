package backend;

public interface Observer {
    public void update(Observer obs, Object args, Action action);
}