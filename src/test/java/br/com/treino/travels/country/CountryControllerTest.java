package br.com.treino.travels.country;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private EntityManager manager;

    @Test
    @DisplayName("Must register a country")
    void test1() throws Exception {
        NewCountryRequest request = new NewCountryRequest("Colombia");

        mockMvc.perform(
                post("/api/v1/travels/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<Country> captor = ArgumentCaptor.forClass(Country.class);
        Mockito.verify(manager).persist(captor.capture());
        assertEquals("Colombia", captor.getValue().getName());
    }

    @Test
    @DisplayName("Must not register a country with name blank")
    void test2() throws Exception {
        NewCountryRequest request = new NewCountryRequest("");

        mockMvc.perform(
                post("/api/v1/travels/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[?(@.field == 'name')].error").value("must not be blank"));
    }

    @Test
    @DisplayName("Must not register a country with same name")
    void test3() throws Exception {
        Country country = new Country("Brazil");
        manager.persist(country);

        NewCountryRequest request = new NewCountryRequest("Brazil");

        mockMvc.perform(
                post("/api/v1/travels/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[?(@.field == 'name')].error").value("This registered value already exists"));
    }

}