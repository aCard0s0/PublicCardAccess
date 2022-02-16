package dev.pca.controllers;

import dev.pca.controllers.exceptions.FeedbackNotFoundException;
import dev.pca.models.Feedback;
import dev.pca.services.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkArgument;

@RestController
@RequestMapping(path="v0/feedback", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeedbackController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackController.class);

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        checkArgument(feedbackService != null, "cardService cannot be null.");
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        LOGGER.debug("operation='createFeedback', message='{}'", feedback);
        return new ResponseEntity<>(feedbackService.insert(feedback), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Feedback>> getAllFeedback() {
        LOGGER.debug("operation='getAllFeedback', message=''");
        return buildCollectionResponse(feedbackService.getAll(), "Empty DB");
    }

    private ResponseEntity<Collection<Feedback>> buildCollectionResponse(Collection<Feedback> feedbacks, String errorMessage) {
        if (feedbacks.isEmpty()) {
            throw new FeedbackNotFoundException(errorMessage);
        }
        return ResponseEntity.ok(feedbacks);
    }
}
