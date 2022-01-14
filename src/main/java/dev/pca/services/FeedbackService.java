package dev.pca.services;

import dev.pca.dao.FeedbackDao;
import dev.pca.models.Feedback;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FeedbackService {
    private final FeedbackDao feedbackDao;

    public FeedbackService(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
    }

    public Feedback insert(Feedback feedback) {
        return feedbackDao.insert(feedback);
    }

    public Collection<Feedback> getAll() {
        return feedbackDao.getAll();
    }
}
