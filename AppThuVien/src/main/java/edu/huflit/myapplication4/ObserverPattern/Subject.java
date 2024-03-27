package edu.huflit.myapplication4.ObserverPattern;

public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}
