package com.example.babettahub.domain.listing;

import com.example.babettahub.common.ResourceNotFoundException;
import com.example.babettahub.config.SecurityConfig;
import com.example.babettahub.domain.listing.dto.ListingResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ListingController.class)
@Import(SecurityConfig.class)
class ListingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ListingService listingService;

    @Test
    void getById_existingId_returns200() throws Exception {
        ListingResponse response = new ListingResponse(
                1L, 7L, "babettar", "Babetta 210", "Pekný stav", 500,
                "0900123456", "predaj@example.com",
                ListingStatus.ACTIVE, LocalDateTime.of(2026, 11, 2, 10, 0));

        when(listingService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/listings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Babetta 210"));
    }

    @Test
    void getById_nonExistingId_returns404() throws Exception{
        when(listingService.getById(99L)).thenThrow(new ResourceNotFoundException("Inzerát s týmto ID neexistuje"));

        mockMvc.perform(get("/api/listings/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Inzerát s týmto ID neexistuje"));
    }

    @Test
    void post_emptyTitle_returns400() throws Exception{
        String body = """
        {
          "title": "",
          "description": "Pekný stav",
          "price": 500,
          "contactEmail": "predaj@example.com",
          "userId": 1
        }
        """;

        mockMvc.perform(post("/api/listings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").exists());

        verify(listingService, never()).create(any());
    }
}