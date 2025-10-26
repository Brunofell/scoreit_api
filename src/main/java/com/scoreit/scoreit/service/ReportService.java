package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.Report;
import com.scoreit.scoreit.dto.reports.ReportStatus;
import com.scoreit.scoreit.repository.MemberRepository;
import com.scoreit.scoreit.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private MemberRepository memberRepository;

    // Usuário envia denúncia
    @Transactional
    public Report createReport(Long reporterId, Long reportedId, String reason) {
        if (reporterId.equals(reportedId)) {
            throw new IllegalArgumentException("Você não pode se denunciar.");
        }

        Member reporter = memberRepository.findById(reporterId)
                .orElseThrow(() -> new RuntimeException("Usuário denunciante não encontrado"));
        Member reported = memberRepository.findById(reportedId)
                .orElseThrow(() -> new RuntimeException("Usuário denunciado não encontrado"));

        Report report = new Report();
        report.setReporter(reporter);
        report.setReported(reported);
        report.setReason(reason);
        return reportRepository.save(report);
    }

    // Admin: listar todas as denúncias
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    // Admin: alterar status
    @Transactional
    public Report updateStatus(Long reportId, ReportStatus newStatus) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Denúncia não encontrada"));

        report.setStatus(newStatus);
        return reportRepository.save(report);
    }

    // Admin: deletar denúncia
    @Transactional
    public void deleteReport(Long reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new RuntimeException("Denúncia não encontrada");
        }
        reportRepository.deleteById(reportId);
    }
}
