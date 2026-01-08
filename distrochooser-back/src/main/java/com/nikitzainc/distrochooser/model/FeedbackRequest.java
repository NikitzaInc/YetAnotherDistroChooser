package com.nikitzainc.distrochooser.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class FeedbackRequest {
    private String associatedLink;
    private String likedDistro;
}
