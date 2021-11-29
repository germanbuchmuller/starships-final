package controller.visitor;

import edu.austral.dissis.starships.file.ImageLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import model.concrete.Asteroid;
import model.Entity;
import model.concrete.Projectile;
import model.concrete.Ship;
import view.EntityView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RenderVisitor implements EntityVisitor{
    private final List<EntityView> entitiesView;
    private final List<Entity> entitiesToRender;
    private final List<EntityView> entitiesViewToRemove;
    private final List<Node> viewsToRemove;
    private final ImageLoader imageLoader;
    private final Pane pane;

    public RenderVisitor(Pane pane) {
        entitiesView =new ArrayList<>();
        entitiesToRender=new ArrayList<>();
        viewsToRemove=new ArrayList<>();
        entitiesViewToRemove=new ArrayList<>();
        imageLoader=new ImageLoader();
        this.pane=pane;
    }

    @Override
    public void visit(Ship ship) {
        entitiesToRender.add(ship);
    }

    @Override
    public void visit(Asteroid asteroid) {
        entitiesToRender.add(asteroid);
    }

    @Override
    public void visit(Projectile projectile) {
        entitiesToRender.add(projectile);
    }

    public void update() throws IOException {
        int n = entitiesToRender.size();
        for (Entity entity : entitiesToRender) {
            entitiesView.add(0,new EntityView(entity,imageLoader.loadFromResources(entity.getImageFileName(),entity.getWidth(),entity.getHeight())));
        }
        entitiesToRender.clear();
        for (int i = 0; i < n; i++) {
            pane.getChildren().add(0,entitiesView.get(i).getView());
        }
        for (EntityView entityView : entitiesView) {
            if (!entityView.getEntity().isDestroyed()){
                entityView.update();
            }else{
                entitiesViewToRemove.add(entityView);
                viewsToRemove.add(entityView.getView());
                pane.getChildren().remove(entityView.getView());
            }
        }
        entitiesView.removeAll(entitiesViewToRemove);
        pane.getChildren().removeAll(viewsToRemove);
        entitiesViewToRemove.clear();
        viewsToRemove.clear();
    }
}
