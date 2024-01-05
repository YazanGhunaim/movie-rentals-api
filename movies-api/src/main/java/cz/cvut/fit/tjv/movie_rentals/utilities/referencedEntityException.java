package cz.cvut.fit.tjv.movie_rentals.utilities;

// thrown when an entity has been attempted to delete, while it's referenced by another entity
public class referencedEntityException extends RuntimeException {
    public referencedEntityException(String message) {
        super(message);
    }
}
