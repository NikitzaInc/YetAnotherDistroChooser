package com.nikitzainc.distrochooser.service;

import com.nikitzainc.distrochooser.model.Distro;
import com.nikitzainc.distrochooser.repository.DistroRepository;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DistroWatchScraperServiceTest {

    private DistroWatchScraperService scraperService;

    @BeforeEach
    void setUp() {
        scraperService = new DistroWatchScraperService();
    }

    @Test
    void searchDistros_ShouldReturnLinks_WhenHtmlIsValid() throws IOException {
        String url = "https://distrowatch.com/search.php";
        File html = new File("src/test/java/com/nikitzainc/distrochooser/resources/search_page.html");

        Document doc = Jsoup.parse(html, "UTF-8", url);

        try (MockedStatic<Jsoup> mockedJsoup = mockStatic(Jsoup.class)) {
            setupMockConnection(mockedJsoup, url, doc);

            List<String> results = scraperService.searchDistros(url);

            assertEquals(25, results.size());
            assertEquals("https://distrowatch.com/cachyos", results.get(0));
        }
    }

    @Test
    void getDistro_ShouldParseFieldsCorrectly() throws IOException {
        String url = "https://distrowatch.com/cachyos";
        File html = new File("src/test/java/com/nikitzainc/distrochooser/resources/cachyos_page.html");

        Document doc = Jsoup.parse(html, "UTF-8", url);

        try (MockedStatic<Jsoup> mockedJsoup = mockStatic(Jsoup.class)) {
            setupMockConnection(mockedJsoup, url, doc);

            Distro distro = scraperService.getDistro(url);

            assertEquals("CachyOS", distro.getName());
            assertEquals("https://cachyos.org/", distro.getHomepage());
        }
    }

    private void setupMockConnection(MockedStatic<Jsoup> mockedJsoup, String url, Document doc) throws IOException {
        Connection mockConnection = mock(Connection.class);

        mockedJsoup.when(() -> Jsoup.connect(url)).thenReturn(mockConnection);
        when(mockConnection.userAgent(anyString())).thenReturn(mockConnection);
        when(mockConnection.timeout(anyInt())).thenReturn(mockConnection);
        when(mockConnection.get()).thenReturn(doc);
    }
}