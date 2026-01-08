package com.nikitzainc.distrochooser.service;

import com.nikitzainc.distrochooser.model.Distro;
import com.nikitzainc.distrochooser.repository.DistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LinkService {
    private final DistroWatchScraperService distroWatchScraperService;
    private final DistroRepository distroRepository;
    private final DistroService distroService;

    @Autowired
    public LinkService(DistroWatchScraperService distroWatchScraperService,
                       DistroRepository distroRepository,
                       DistroService distroService) {
        this.distroWatchScraperService = distroWatchScraperService;
        this.distroRepository = distroRepository;
        this.distroService = distroService;
    }

    @Cacheable(value = "distroSearches", key = "#distroWatchLink")
    public List<Distro> getDistros(String distroWatchLink) {
        List<String> links = distroWatchScraperService.searchDistros(distroWatchLink);
        List<Distro> existingDistros = distroRepository.findAllByDistrowatchUrlIn(links);

        Map<String, Distro> distroMap = existingDistros.stream()
                .collect(Collectors.toMap(Distro::getDistrowatchUrl, d -> d));

        List<Distro> result = new ArrayList<>();

        for (String link : links) {
            if (distroMap.containsKey(link)) {
                result.add(distroMap.get(link));
            } else {
                result.add(distroService.saveNewDistro(link));
            }
        }
        return result;
    }
}
