package com.nikitzainc.distrochooser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikitzainc.distrochooser.model.Distro;
import com.nikitzainc.distrochooser.model.FeedbackRequest;
import com.nikitzainc.distrochooser.service.FeedbackService;
import com.nikitzainc.distrochooser.service.LinkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LinkController.class)
class LinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LinkService linkService;

    @MockitoBean
    private FeedbackService feedbackService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnDistros_WhenLinkIsValid() throws Exception {
        String testLink = "https://distrowatch.com/dwres.php?resource=major";

        Distro distro1 = new Distro();
        distro1.setName("Ubuntu");
        Distro distro2 = new Distro();
        distro2.setName("BebraOS");
        List<Distro> mockDistros = Arrays.asList(distro1, distro2);

        when(linkService.getDistros(testLink)).thenReturn(mockDistros);

        // When & Then
        mockMvc.perform(get("/api/link")
                        .param("distroWatchLink", testLink))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Ubuntu"))
                .andExpect(jsonPath("$[1].name").value("BebraOS"));
    }

    @Test
    void shouldReturnBadRequest_WhenParamIsMissing() throws Exception {
        mockMvc.perform(get("/api/link"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldProcessFeedback_WhenRequestIsValid() throws Exception {
        FeedbackRequest request = new FeedbackRequest();
        request.setAssociatedLink("https://benis.com");
        request.setLikedDistro("Bebra Linux");

        mockMvc.perform(post("/api/link/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(feedbackService).addSelectedDistro("https://benis.com", "Bebra Linux");
    }
}