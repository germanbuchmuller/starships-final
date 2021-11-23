package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Entity;

public class EntityView {
    private final ImageView imageView;
    private final Entity entity;

    public EntityView(Entity entity, Image image) {
        this.entity = entity;
        imageView=new ImageView(image);
        update();
    }

    public void update(){
        imageView.setLayoutX(entity.getX());
        imageView.setLayoutY(entity.getY());
        imageView.setRotate(entity.getAngle());
    }

    public ImageView getView(){
        return imageView;
    }

    public Entity getEntity() {
        return entity;
    }
}
