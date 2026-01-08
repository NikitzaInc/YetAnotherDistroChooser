package com.nikitzainc.distrochooser.service;

import com.nikitzainc.distrochooser.model.Feedback;
import com.nikitzainc.distrochooser.repository.FeedbackRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @Test
    void shouldSaveFeedbackWithCorrectData() {
        String testLink = "https://aboba24.gay";
        String testDistro = "Pedora Gaystation";
        ArgumentCaptor<Feedback> feedbackCaptor = ArgumentCaptor.forClass(Feedback.class);

        feedbackService.addSelectedDistro(testLink, testDistro);

        verify(feedbackRepository, times(1)).save(feedbackCaptor.capture());

        Feedback capturedFeedback = feedbackCaptor.getValue();
        assertEquals(testLink, capturedFeedback.getResultLink(), "The result link should match the input");
        assertEquals(testDistro, capturedFeedback.getSelectedDistro(), "The distro name should match the input");
    }
}