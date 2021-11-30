package controller.command.concrete;

import controller.ShipController;
import controller.command.Command;
import model.concrete.Ship;

public class RotateRightShipCommand implements Command<Ship, ShipController> {

    @Override
    public void execute(Ship entity, ShipController entityController, double secondsSinceLastFrame) {
        entityController.rotateRight(entity,secondsSinceLastFrame);
    }

}
