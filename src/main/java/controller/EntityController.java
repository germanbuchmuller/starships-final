package controller;

import model.Entity;

public interface EntityController<T extends Entity>{
    void move(T entity, double secondsSinceLastFrame);
}
