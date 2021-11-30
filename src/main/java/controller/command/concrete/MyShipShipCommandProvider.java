package controller.command.concrete;

import controller.Movement;
import controller.ShipController;
import controller.command.Command;
import controller.command.ShipCommandProvider;
import model.concrete.Ship;

public class MyShipShipCommandProvider implements ShipCommandProvider {

    @Override
    public Command<Ship, ShipController> getCommand(Movement movement) {
        switch (movement){
            case FORWARD:
                return new AccelerateShipCommand();
            case BACKWARDS:
                return new BreakShipCommand();
            case ROTATE_LEFT:
                return new RotateLeftShipCommand();
            case ROTATE_RIGHT:
                return new RotateRightShipCommand();
            case SHOOT:
                return new ShootShipCommand();
            default:
                return new ShipNoCommand();
        }
    }
}
