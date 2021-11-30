package controller.command;

import controller.EntityController;
import model.Entity;

public interface Command<T extends Entity,U extends EntityController<T>> {
    void execute(T entity, U entityController, double secondsSinceLastFrame);
}
