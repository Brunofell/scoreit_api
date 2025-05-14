package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.review.ReviewRegister;
import com.scoreit.scoreit.entity.Review;
import com.scoreit.scoreit.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public void reviewRegister(ReviewRegister dados){
        Review review = new Review(dados);
        reviewRepository.save(review);
    }

    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public List<Review> getReviewMemberById(String memberId){
        return reviewRepository.findByMemberId(memberId);
    }

    public List<Review> getReviewMediaById(String mediaId){
        return reviewRepository.findByMediaId(mediaId);
    }

    public void deleteReview(Long id){
        reviewRepository.deleteById(id);
    }
}
