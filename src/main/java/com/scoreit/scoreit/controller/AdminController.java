package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.dto.graph.MediaPopularityDTO;
import com.scoreit.scoreit.dto.graph.ReviewsByDateDTO;
import com.scoreit.scoreit.dto.graph.UsersGrowthDTO;
import com.scoreit.scoreit.dto.reports.ReportStatus;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.Report;
import com.scoreit.scoreit.service.MemberService;
import com.scoreit.scoreit.service.ReportService;
import com.scoreit.scoreit.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private StatisticsService statisticsService;

    // MEMBROS

    @PatchMapping("/members/{id}/toggle")
    public ResponseEntity<String> toggleMemberStatus(@PathVariable Long id) {
        memberService.toggleMemberStatus(id);
        return ResponseEntity.ok("Status atualizado com sucesso!");
    }

    @PatchMapping("/members/{id}/role")
    public ResponseEntity<String> toggleMemberRole(@PathVariable Long id) {
        memberService.toggleMemberRole(id);
        return ResponseEntity.ok("Papel (role) atualizado com sucesso!");
    }

    @GetMapping("/members")
    public ResponseEntity<Page<Member>> listAllMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String query
    ) {
        Page<Member> members = memberService.listAllMembers(page, size, query);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member member = memberService.getMemberByIdOrThrow(id);
        return ResponseEntity.ok(member);
    }

    @PatchMapping("/members/{id}/update")
    public ResponseEntity<Member> updateMemberAdmin(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Boolean enabled
    ) {
        Member updated = memberService.updateMemberAdmin(id, name, email, enabled);
        return ResponseEntity.ok(updated);
    }

    // DENÚNCIAS

    @GetMapping("/reports")
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @PatchMapping("/reports/{id}/status")
    public ResponseEntity<Report> updateReportStatus(
            @PathVariable Long id,
            @RequestParam ReportStatus status
    ) {
        Report updated = reportService.updateStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/reports/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.ok("Denúncia removida com sucesso.");
    }

    //  GRÁFICOS

    @GetMapping("/members/created-on")
    public ResponseEntity<List<Member>> getMembersByCreationDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<Member> members = memberService.getMembersByDate(date);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/members/created-between")
    public ResponseEntity<List<Member>> getMembersBetween(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        List<Member> members = memberService.getMembersByDateRange(start, end);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/statistic/reviews-by-date")
    public List<ReviewsByDateDTO> getReviewsByDate() {
        return statisticsService.getReviewsByDate();
    }

    @GetMapping("/statistic/popular-media")
    public List<MediaPopularityDTO> getPopularMedia() {
        return statisticsService.getMostPopularMedia();
    }

    @GetMapping("/statistic/users-growth")
    public List<UsersGrowthDTO> getUsersGrowth() {
        return statisticsService.getUsersGrowth();
    }


}
