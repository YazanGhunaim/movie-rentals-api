package cz.cvut.fit.tjv.movie_rentals.services;

import cz.cvut.fit.tjv.movie_rentals.domain.EntityWithId;
import cz.cvut.fit.tjv.movie_rentals.utilities.EntityStateException;
import cz.cvut.fit.tjv.movie_rentals.utilities.referencedEntityException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public abstract class CrudServiceImpl<R extends CrudRepository<T, ID>, T extends EntityWithId<ID>, ID> implements CrudService<T, ID> {
    protected abstract R getRepository();

    @Override
    public T create(T entity) {
        // duplicate id's
        if (entity.getID() != null && getRepository().existsById(entity.getID()))
            throw new EntityStateException();

        return getRepository().save(entity);
    }

    @Override
    public Optional<T> readById(ID id) {
        return getRepository().findById(id);
    }

    @Override
    public Iterable<T> readAll() {
        return getRepository().findAll();
    }

    @Override
    public void update(ID id, T entity) {
    }

    @Override
    public void deleteById(ID id) {
        try {
            if (!getRepository().existsById(id))
                throw new IllegalArgumentException("invalid id");

            getRepository().deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // entity trying to be deleted is referenced in another entity
            throw new referencedEntityException("Unable to delete record with ID " + id + " as its referenced by another entity.");
        }
    }
}
