package com.nikitzainc.distrochooser.service;

import com.nikitzainc.distrochooser.model.Feedback;
import com.nikitzainc.distrochooser.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Transactional
    public void addSelectedDistro(String associatedLink, String distroName) {
        Feedback feedback = new Feedback();
        feedback.setResultLink(associatedLink);
        feedback.setSelectedDistro(distroName);
        feedbackRepository.save(feedback);
    }
}
