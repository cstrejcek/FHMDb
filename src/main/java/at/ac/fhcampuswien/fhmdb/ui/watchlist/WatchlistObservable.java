package at.ac.fhcampuswien.fhmdb.ui.watchlist;

public interface WatchlistObservable {
    void addObserver(WatchlistObserver observer);
    void removeObserver(WatchlistObserver observer);
    void notifyObservers(String message);
}
