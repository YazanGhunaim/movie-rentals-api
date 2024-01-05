package cz.cvut.fit.tjv.movie_rentals.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
EntityWithId interface: implemented by the project entities
                        enforces getID() method
*/
public interface EntityWithId<ID> {
    @JsonIgnore
    ID getID();
}
