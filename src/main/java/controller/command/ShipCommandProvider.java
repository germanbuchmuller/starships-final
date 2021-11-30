package controller.command;

import controller.EntityController;
import controller.Movement;
import controller.ShipController;
import model.Entity;
import model.concrete.Ship;

public interface ShipCommandProvider {
    Command<Ship, ShipController> getCommand(Movement movement);
}
