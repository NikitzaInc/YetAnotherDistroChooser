package com.nikitzainc.distrochooser.repository;

import com.nikitzainc.distrochooser.model.Distro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistroRepository extends JpaRepository<Distro, Integer> {
    Optional<Distro> findByDistrowatchUrl(String distroWatchLink);
    List<Distro> findAllByDistrowatchUrlIn(List<String> urls);
}
