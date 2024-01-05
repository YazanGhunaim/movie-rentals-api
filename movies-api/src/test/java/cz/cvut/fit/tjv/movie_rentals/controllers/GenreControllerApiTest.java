package cz.cvut.fit.tjv.movie_rentals.controllers;

import cz.cvut.fit.tjv.movie_rentals.domain.Genre;
import cz.cvut.fit.tjv.movie_rentals.services.GenreService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(GenreController.class)
class GenreControllerApiTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Test
    void create() throws Exception {
        var genre = new Genre();
        genre.setMovieGenre("horror");
        Mockito.when(genreService.create(genre)).thenReturn(genre);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/genre")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                        "movieGenre" : "horror"
                                        }""")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieGenre", Matchers.is(genre.getMovieGenre())));
    }
}