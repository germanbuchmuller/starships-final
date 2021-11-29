package controller.visitor;

import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;

public interface EntityVisitor {
    void visit(Ship ship);
    void visit(Asteroid asteroid);
    void visit(Projectile projectile);
}
