package com.nikitzainc.distrochooser.repository;

import com.nikitzainc.distrochooser.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Feedback findByResultLink(String resultLink);
}
