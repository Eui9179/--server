package leui.woojoo.bounded_context.reports.controller;

import leui.woojoo.bounded_context.reports.service.ReportsService;
import leui.woojoo.bounded_context.reports.dto.ReportRequest;
import leui.woojoo.base.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReportsController {

    private final ReportsService reportsService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/report")
    public String report(Principal principal, @RequestBody ReportRequest request) {
        Long userId = UserUtils.resolveUserId(principal);
        reportsService.save(userId, request.getReportedUserId(), request.getReportNumbers());
        return "ok";
    }
}
