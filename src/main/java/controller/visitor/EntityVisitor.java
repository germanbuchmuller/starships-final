package controller.visitor;

import model.Asteroid;
import model.Projectile;
import model.Ship;

public interface EntityVisitor {
    void visit(Ship ship);
    void visit(Asteroid asteroid);
    void visit(Projectile projectile);
}
