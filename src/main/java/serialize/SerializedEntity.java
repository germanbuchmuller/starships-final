package serialize;

import model.Entity;

import java.io.Serializable;

public interface SerializedEntity extends Serializable {
     Entity toEntity();
}
