package com.nikitzainc.distrochooser.service;

import com.nikitzainc.distrochooser.model.Distro;
import com.nikitzainc.distrochooser.repository.DistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DistroService {
    private final DistroWatchScraperService distroWatchScraperService;
    private final DistroRepository distroRepository;

    @Autowired
    public DistroService(DistroWatchScraperService watchScraperService, DistroRepository distroRepository) {
        this.distroWatchScraperService = watchScraperService;
        this.distroRepository = distroRepository;
    }

    @Transactional
    public Distro saveNewDistro(String link) {
        Distro distro = distroWatchScraperService.getDistro(link);
        return distroRepository.save(distro);
    }
}
