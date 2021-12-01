package model.factory;

import misc.Weapon;
import model.concrete.Ship;

public interface ShipFactory extends EntityFactory<Ship> {
    void setWeapon(Weapon weapon);
    void setPlayerId(int playerId);
    void setRandomSpawn(boolean randomSpawn);
    void reviveShip(Ship ship);
}
