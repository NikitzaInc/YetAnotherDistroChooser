package com.nikitzainc.distrochooser.service;

import com.nikitzainc.distrochooser.model.Distro;
import com.nikitzainc.distrochooser.repository.DistroRepository;
import com.nikitzainc.distrochooser.repository.FeedbackRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LinkServiceTest {

    @Mock
    private DistroWatchScraperService scraperService;

    @Mock
    private DistroRepository distroRepository;

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private LinkService linkService;

    @Test
    void getDistros_NoLinks() {
        String searchUrl = "https://distrowatch.com/search";
        when(scraperService.searchDistros(searchUrl)).thenReturn(Collections.emptyList());

        List<Distro> result = linkService.getDistros(searchUrl);

        assertTrue(result.isEmpty());
        verify(scraperService, times(1)).searchDistros(searchUrl);
        verifyNoInteractions(distroRepository);
    }

    @Test
    void getDistros_ExistingDistro() {
        String searchUrl = "https://distrowatch.com/search";
        String distroUrl = "https://distrowatch.com/ubuntu";
        Distro existingDistro = new Distro();
        existingDistro.setDistrowatchUrl(distroUrl);

        when(scraperService.searchDistros(searchUrl)).thenReturn(List.of(distroUrl));
        when(distroRepository.findByDistrowatchUrl(distroUrl)).thenReturn(Optional.of(existingDistro));

        List<Distro> result = linkService.getDistros(searchUrl);

        assertEquals(1, result.size());
        assertEquals(distroUrl, result.get(0).getDistrowatchUrl());
        verify(scraperService, never()).getDistro(anyString());
        verify(distroRepository, never()).save(any());
    }

    @Test
    void getDistros_NewDistro() {
        String searchUrl = "https://distrowatch.com/search";
        String distroUrl = "https://distrowatch.com/fedora";
        Distro newDistro = new Distro();
        newDistro.setDistrowatchUrl(distroUrl);

        when(scraperService.searchDistros(searchUrl)).thenReturn(List.of(distroUrl));
        when(distroRepository.findByDistrowatchUrl(distroUrl)).thenReturn(Optional.empty());
        when(scraperService.getDistro(distroUrl)).thenReturn(newDistro);

        List<Distro> result = linkService.getDistros(searchUrl);

        assertEquals(1, result.size());
        verify(distroRepository).save(newDistro);
        verify(scraperService).getDistro(distroUrl);
    }

    @Test
    @DisplayName("Should handle mixed existing and new distros")
    void getDistros_MixedData() {
        String searchUrl = "https://distrowatch.com/search";
        String link1 = "url1";
        String link2 = "url2";

        Distro distro1 = new Distro(); // Existing
        Distro distro2 = new Distro(); // New

        when(scraperService.searchDistros(searchUrl)).thenReturn(Arrays.asList(link1, link2));

        when(distroRepository.findByDistrowatchUrl(link1)).thenReturn(Optional.of(distro1));

        when(distroRepository.findByDistrowatchUrl(link2)).thenReturn(Optional.empty());
        when(scraperService.getDistro(link2)).thenReturn(distro2);

        List<Distro> result = linkService.getDistros(searchUrl);

        // Assert
        assertEquals(2, result.size());
        verify(distroRepository, times(1)).save(distro2);
        verify(distroRepository, times(1)).findByDistrowatchUrl(link1);
        verify(distroRepository, times(1)).findByDistrowatchUrl(link2);
    }
}