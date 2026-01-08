package com.nikitzainc.distrochooser.controller;

import com.nikitzainc.distrochooser.model.Distro;
import com.nikitzainc.distrochooser.model.FeedbackRequest;
import com.nikitzainc.distrochooser.service.FeedbackService;
import com.nikitzainc.distrochooser.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/link")
public class LinkController {
    private final LinkService linkService;
    private final FeedbackService feedbackService;

    @Autowired
    public LinkController(LinkService linkService, FeedbackService feedbackService) {
        this.linkService = linkService;
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public ResponseEntity<List<Distro>> getLinks(@RequestParam String distroWatchLink) {
        List<Distro> distros = linkService.getDistros(distroWatchLink);
        return ResponseEntity.ok(distros);
    }

    @PostMapping("/feedback")
    public ResponseEntity<Void> sendFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        feedbackService.addSelectedDistro(feedbackRequest.getAssociatedLink(), feedbackRequest.getLikedDistro());
        return ResponseEntity.ok().build();
    }
}
