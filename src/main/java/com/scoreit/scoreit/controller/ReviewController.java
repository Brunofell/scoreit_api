package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.dto.review.ReviewRegister;
import com.scoreit.scoreit.dto.review.ReviewUpdate;
import com.scoreit.scoreit.entity.Review;
import com.scoreit.scoreit.repository.ReviewRepository;
import com.scoreit.scoreit.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/register")
    public ResponseEntity<?> reviewRegister(@RequestBody @Valid ReviewRegister data){
        reviewService.reviewRegister(data);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> reviewUpdate(@RequestBody @Valid ReviewUpdate data){
        reviewService.reviewUpdate(data);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllReviews")
    public List<Review> getAllReviews(){
        return reviewService.getAllReviews();
    }

    @GetMapping("/getReviewByMemberId/{memberId}")
    public List<Review> getReviewMemberById(@PathVariable Long memberId){
        return reviewService.getReviewMemberById(memberId);
    }

    @GetMapping("/getReviewByMediaId/{mediaId}")
    public List<Review> getReviewMediaById(@PathVariable String mediaId){
        return reviewService.getReviewMediaById(mediaId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/followed/{memberId}")
    public ResponseEntity<List<Review>> getReviewsFromFollowedMembers(@PathVariable Long memberId) {
        List<Review> reviews = reviewService.getReviewsFromFollowedMembers(memberId);
        return ResponseEntity.ok(reviews);
    }
}
