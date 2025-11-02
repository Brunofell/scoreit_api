package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.reports.ReportStatus;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.Report;
import com.scoreit.scoreit.repository.MemberRepository;
import com.scoreit.scoreit.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // =========================
    // Teste de criação de denúncia
    // =========================
    @Test
    void testCreateReportSuccess() {
        Member reporter = new Member();
        reporter.setId(1L);
        Member reported = new Member();
        reported.setId(2L);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(reporter));
        when(memberRepository.findById(2L)).thenReturn(Optional.of(reported));

        Report savedReport = new Report();
        savedReport.setReporter(reporter);
        savedReport.setReported(reported);
        savedReport.setReason("Spam");

        when(reportRepository.save(any(Report.class))).thenReturn(savedReport);

        Report result = reportService.createReport(1L, 2L, "Spam");

        assertNotNull(result);
        assertEquals("Spam", result.getReason());
        assertEquals(reporter, result.getReporter());
        assertEquals(reported, result.getReported());

        verify(reportRepository).save(any(Report.class));
    }

    @Test
    void testCreateReportSelfReport() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> reportService.createReport(1L, 1L, "Spam"));
        assertEquals("Você não pode se denunciar.", ex.getMessage());
    }

    @Test
    void testCreateReportReporterNotFound() {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reportService.createReport(1L, 2L, "Spam"));
        assertEquals("Usuário denunciante não encontrado", ex.getMessage());
    }

    @Test
    void testCreateReportReportedNotFound() {
        Member reporter = new Member();
        reporter.setId(1L);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(reporter));
        when(memberRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reportService.createReport(1L, 2L, "Spam"));
        assertEquals("Usuário denunciado não encontrado", ex.getMessage());
    }

    // =========================
    // Teste de listagem de denúncias
    // =========================
    @Test
    void testGetAllReports() {
        Report report1 = new Report();
        Report report2 = new Report();

        when(reportRepository.findAll()).thenReturn(List.of(report1, report2));

        List<Report> reports = reportService.getAllReports();

        assertEquals(2, reports.size());
        verify(reportRepository).findAll();
    }

    // =========================
    // Teste de atualização de status
    // =========================
    @Test
    void testUpdateStatusSuccess() {
        Report report = new Report();
        report.setId(1L);
        report.setStatus(ReportStatus.PENDING);

        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));
        when(reportRepository.save(any(Report.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Report updated = reportService.updateStatus(1L, ReportStatus.RESOLVED);

        assertEquals(ReportStatus.RESOLVED, updated.getStatus());
        verify(reportRepository).save(report);
    }

    @Test
    void testUpdateStatusReportNotFound() {
        when(reportRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reportService.updateStatus(1L, ReportStatus.RESOLVED));
        assertEquals("Denúncia não encontrada", ex.getMessage());
    }

    // =========================
    // Teste de exclusão de denúncia
    // =========================
    @Test
    void testDeleteReportSuccess() {
        when(reportRepository.existsById(1L)).thenReturn(true);
        doNothing().when(reportRepository).deleteById(1L);

        reportService.deleteReport(1L);

        verify(reportRepository).deleteById(1L);
    }

    @Test
    void testDeleteReportNotFound() {
        when(reportRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reportService.deleteReport(1L));
        assertEquals("Denúncia não encontrada", ex.getMessage());
    }
}
