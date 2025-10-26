package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.entity.Report;
import com.scoreit.scoreit.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // Usuário cria denúncia
    @PostMapping
    public ResponseEntity<Report> createReport(
            @RequestParam Long reporterId,
            @RequestParam Long reportedId,
            @RequestParam String reason
    ) {
        Report report = reportService.createReport(reporterId, reportedId, reason);
        return ResponseEntity.ok(report);
    }
}
