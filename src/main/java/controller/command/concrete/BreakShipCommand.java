package controller.command.concrete;

import controller.ShipController;
import controller.command.Command;
import model.concrete.Ship;

public class BreakShipCommand implements Command<Ship, ShipController> {

    @Override
    public void execute(Ship entity, ShipController entityController, double secondsSinceLastFrame) {
        entityController.moveBackwards(entity,secondsSinceLastFrame);
    }

}
