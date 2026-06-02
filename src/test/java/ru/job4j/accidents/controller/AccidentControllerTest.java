package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccidentControllerTest {

    @MockitoBean
    private AccidentService accidentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenCreateAccidentThenRedirect() throws Exception {
        Accident savedAccident = new Accident();
        savedAccident.setId(1);
        when(accidentService.save(org.mockito.ArgumentMatchers.any(Accident.class), eq(List.of(1, 2))))
                .thenReturn(savedAccident);
        mockMvc.perform(post("/accidents/create")
                        .with(user("test").roles("USER"))
                        .with(csrf())
                        .param("name", "Test accident")
                        .param("text", "Some text")
                        .param("address", "Some address")
                        .param("type.id", "1")
                        .param("ruleIds", "1", "2"))
                .andExpect(status().is3xxRedirection());

        ArgumentCaptor<Accident> captor = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).save(captor.capture(), eq(List.of(1, 2)));

        assertThat(captor.getValue().getName()).isEqualTo("Test accident");
    }

    @Test
    void whenUpdateAccidentThenRedirect() throws Exception {
        when(accidentService.update(org.mockito.ArgumentMatchers.any(Accident.class), eq(List.of(1, 2))))
                .thenReturn(true);

        mockMvc.perform(post("/accidents/edit/1")
                        .with(user("test").roles("USER"))
                        .with(csrf())
                        .param("name", "Updated accident")
                        .param("text", "Updated text")
                        .param("address", "Updated address")
                        .param("type.id", "2")
                        .param("ruleIds", "1", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accidents/1"));

        ArgumentCaptor<Accident> captor = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).update(captor.capture(), eq(List.of(1, 2)));

        assertThat(captor.getValue().getId()).isEqualTo(1);
        assertThat(captor.getValue().getName()).isEqualTo("Updated accident");
        assertThat(captor.getValue().getType().getId()).isEqualTo(2);
    }
}
