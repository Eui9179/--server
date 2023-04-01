package leui.woojoo.domain.reports.service;

import leui.woojoo.domain.reports.entity.Reports;
import leui.woojoo.domain.reports.entity.ReportsRepository;
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
