package com.nikitzainc.distrochooser.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nikitzainc.distrochooser.model.Distro;
import com.nikitzainc.distrochooser.repository.DistroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DistroServiceTest {
    @Mock
    private DistroWatchScraperService scraperService;

    @Mock
    private DistroRepository repository;

    @InjectMocks
    private DistroService distroService;

    @Test
    void saveNewDistro_Success() {
        String link = "https://distrowatch.com/fedora";
        Distro mockDistro = new Distro();
        mockDistro.setName("Fedora");

        when(scraperService.getDistro(link)).thenReturn(mockDistro);
        when(repository.save(any(Distro.class))).thenReturn(mockDistro);

        Distro result = distroService.saveNewDistro(link);

        assertNotNull(result);
        assertEquals("Fedora", result.getName());

        verify(scraperService, times(1)).getDistro(link);
        verify(repository, times(1)).save(mockDistro);
    }

    @Test
    void saveNewDistro_ScraperFails() {
        String link = "invalid-link";
        when(scraperService.getDistro(link)).thenThrow(new RuntimeException("Scraping failed"));

        assertThrows(RuntimeException.class, () -> {
            distroService.saveNewDistro(link);
        });

        verify(repository, never()).save(any());
    }
}