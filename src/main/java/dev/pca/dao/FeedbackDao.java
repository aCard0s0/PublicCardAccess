package dev.pca.dao;

import dev.pca.models.Feedback;
import dev.pca.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class FeedbackDao {
    @Value("${pca.api.maxRequestSize.cards:100}")
    private Long MAX_REQUEST_SIZE;

    private final FeedbackRepository feedbackRepo;

    public FeedbackDao(FeedbackRepository feedbackRepo) {
        this.feedbackRepo = feedbackRepo;
    }

    public Feedback insert(Feedback feedback) {
        return feedbackRepo.insert(feedback);
    }

    public Collection<Feedback> getAll() {
        return feedbackRepo.findAll().stream()
                .limit(MAX_REQUEST_SIZE)
                .collect(Collectors.toList());
    }
}
