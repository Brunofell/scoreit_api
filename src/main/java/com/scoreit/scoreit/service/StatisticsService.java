package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.graph.MediaPopularityDTO;
import com.scoreit.scoreit.dto.graph.ReviewsByDateDTO;
import com.scoreit.scoreit.dto.graph.UsersGrowthDTO;
import com.scoreit.scoreit.repository.MemberRepository;
import com.scoreit.scoreit.repository.ReviewRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    public StatisticsService(ReviewRepository reviewRepository, MemberRepository memberRepository) {
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
    }

    public List<ReviewsByDateDTO> getReviewsByDate() {
        return reviewRepository.countReviewsByDate().stream()
                .map(obj -> new ReviewsByDateDTO(
                        ((java.sql.Date) obj[0]).toLocalDate(),
                        ((Number) obj[1]).longValue()))
                .collect(Collectors.toList());
    }

    public List<MediaPopularityDTO> getMostPopularMedia() {
        return reviewRepository.findMostPopularMedia().stream()
                .map(obj -> new MediaPopularityDTO(
                        (String) obj[0],
                        obj[1].toString(),
                        ((Number) obj[2]).longValue()))
                .collect(Collectors.toList());
    }

    public List<UsersGrowthDTO> getUsersGrowth() {
        return memberRepository.countMembersByMonth().stream()
                .map(obj -> new UsersGrowthDTO(
                        (String) obj[0],
                        ((Number) obj[1]).longValue()))
                .collect(Collectors.toList());
    }
}
