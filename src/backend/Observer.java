package backend;

public interface Observer {
    public void update(Observable observable, Object args, Enum action);
}