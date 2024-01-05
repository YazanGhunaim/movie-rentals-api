package cz.cvut.fit.tjv.movie_rentals.services;

import cz.cvut.fit.tjv.movie_rentals.domain.EntityWithId;

import java.util.Optional;

public interface CrudService<T extends EntityWithId<ID>, ID> {
    T create(T entity);

    Optional<T> readById(ID id);

    Iterable<T> readAll();

    void update(ID id, T entity);

    void deleteById(ID id);
}
