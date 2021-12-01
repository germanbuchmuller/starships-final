package view.concrete;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Entity;
import view.EntityView;

public class MyEntityView implements EntityView {
    private final ImageView imageView;
    private final Entity entity;

    public MyEntityView(Entity entity, Image image) {
        this.entity = entity;
        imageView=new ImageView(image);
        imageView.setFitWidth(entity.getWidth());
        imageView.setFitHeight(entity.getHeight());
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
