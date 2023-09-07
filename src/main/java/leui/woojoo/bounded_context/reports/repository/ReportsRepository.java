package leui.woojoo.bounded_context.reports.repository;

import leui.woojoo.bounded_context.reports.entity.Reports;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportsRepository extends JpaRepository<Reports, Long> {
}
