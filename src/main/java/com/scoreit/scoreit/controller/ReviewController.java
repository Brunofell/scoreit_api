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
    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping("/register")
    private ResponseEntity<?> reviewRegister(@RequestBody @Valid ReviewRegister data){
        reviewService.reviewRegister(data);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    private ResponseEntity<?> reviewUpdate(@RequestBody @Valid ReviewUpdate data){
        reviewService.reviewUpdate(data);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllReviews")
    private List<Review> getAllReviews(){
        return reviewService.getAllReviews();
    }

    @GetMapping("/getReviewByMemberId/{memberId}")
    private List<Review> getReviewMemberById(@RequestBody @Valid @PathVariable String memberId){
        return reviewService.getReviewMemberById(memberId);
    }

    @GetMapping("/getReviewByMediaId/{mediaId}")
    private List<Review> getReviewMediaById(@RequestBody @Valid @PathVariable String mediaId){
        return reviewService.getReviewMediaById(mediaId);
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<?> deleteReview(@RequestBody @Valid @PathVariable Long id){
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/followed/{memberId}")
    public ResponseEntity<List<Review>> getReviewsFromFollowedMembers(@PathVariable Long memberId) {
        List<Review> reviews = reviewService.getReviewsFromFollowedMembers(memberId);
        return ResponseEntity.ok(reviews);
    }



}