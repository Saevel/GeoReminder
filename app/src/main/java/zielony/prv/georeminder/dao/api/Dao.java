package zielony.prv.georeminder.dao.api;

import java.util.List;

public interface Dao<EntityType, IdType> {

    EntityType save(EntityType entity);

    EntityType update(EntityType entity);

    void delete(EntityType entity);

    List<EntityType> findAll();

    EntityType findById(IdType id);
}