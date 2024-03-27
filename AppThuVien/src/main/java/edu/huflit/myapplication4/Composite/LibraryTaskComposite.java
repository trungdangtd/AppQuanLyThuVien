package edu.huflit.myapplication4.Composite;

import java.util.ArrayList;
import java.util.List;

public class LibraryTaskComposite implements LibraryComponent {
    public List<LibraryComponent> components = new ArrayList<>();

    public void addComponent(LibraryComponent component) {
        components.add(component);
    }

    @Override
    public void performTask() {
        for (LibraryComponent component : components) {
            component.performTask();
        }
    }
}
