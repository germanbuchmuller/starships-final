package view;

import javafx.scene.image.Image;
import model.Entity;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;

public interface EntityImageRepository {
    void addEntity(Entity entity);
    Image getImage(Entity entity);
}
