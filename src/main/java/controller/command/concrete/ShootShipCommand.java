package controller.command.concrete;

import controller.ShipController;
import controller.command.Command;
import model.concrete.Ship;

public class ShootShipCommand implements Command<Ship, ShipController> {

    @Override
    public void execute(Ship entity, ShipController entityController, double secondsSinceLastFrame) {
        entityController.shoot(entity);
    }

}
