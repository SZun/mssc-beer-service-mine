package guru.springframework.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbeerservice.web.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beers/" + UUID.randomUUID().toString())
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDTO beerDTO = BeerDTO.builder().build();
        String beerToJson = objectMapper.writeValueAsString(beerDTO);

        mockMvc.perform(post("/api/v1/beers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(beerToJson))
        .andExpect(status().isCreated());
    }

    @Test
    void updateBeerById() throws Exception {
        BeerDTO beerDTO = BeerDTO.builder().build();
        String beerToJson = objectMapper.writeValueAsString(beerDTO);

        mockMvc.perform(put("/api/v1/beers/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerToJson))
                .andExpect(status().isNoContent());
    }
}