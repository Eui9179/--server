package leui.woojoo.bounded_context.reports.service;

import leui.woojoo.bounded_context.reports.entity.Reports;
import leui.woojoo.bounded_context.reports.repository.ReportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportsService {

    private final ReportsRepository reportsRepository;

    public void save(Long userId, Long reportedUserId, Integer reportNumbers) {
        Reports reports = Reports.builder()
                .userId(userId)
                .reportedUserId(reportedUserId)
                .reportNumbers(reportNumbers)
                .build();
        reportsRepository.save(reports);
    }

}
