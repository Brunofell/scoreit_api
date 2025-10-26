package com.scoreit.scoreit.entity;

import com.scoreit.scoreit.dto.reports.ReportStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private Member reporter;

    @ManyToOne
    @JoinColumn(name = "reported_id", nullable = false)
    private Member reported;

    @Column(nullable = false, length = 500)
    private String reason;

    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Report(Long id, Member reporter, Member reported, String reason, ReportStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.reporter = reporter;
        this.reported = reported;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Report() {
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Member getReported() {
        return reported;
    }

    public void setReported(Member reported) {
        this.reported = reported;
    }

    public Member getReporter() {
        return reporter;
    }

    public void setReporter(Member reporter) {
        this.reporter = reporter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}