package view.concrete;

import edu.austral.dissis.starships.file.ImageLoader;
import javafx.scene.image.Image;
import model.concrete.Asteroid;
import model.Entity;
import model.concrete.Projectile;
import model.concrete.Ship;
import org.jetbrains.annotations.NotNull;
import view.EntityImageRepository;
import view.EntityView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyEntityImageRepository implements EntityImageRepository {
    private final Map<String, Image> entityImages;
    private final ImageLoader imageLoader;

    public MyEntityImageRepository(@NotNull ImageLoader imageLoader) {
        entityImages=new HashMap<>();
        this.imageLoader=imageLoader;
    }

    @Override
    public void addEntity(Entity entity) {
        putKey(entity);
    }

    @Override
    public Image getImage(Entity entity) {
        addEntity(entity);
        return entityImages.get(entity.getImageFileName());
    }

    private void putKey(Entity entity){
        if (hasNoKey(entity)){
            entityImages.put(entity.getImageFileName(),getImageFromEntity(entity));
        }
    }

    private boolean hasNoKey(Entity entity){
        return !entityImages.containsKey(entity.getImageFileName());
    }

    private Image getImageFromEntity(Entity entity){
        try{
            return imageLoader.loadFromResources(entity.getImageFileName(),entity.getWidth(),entity.getHeight());
        }catch (IOException e){
            e.printStackTrace();
            try{
                return imageLoader.loadFromResources("no-texture.png",entity.getWidth(),entity.getHeight());
            }catch (IOException ignored){
                return null;
            }
        }
    }
}
