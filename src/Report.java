import java.time.LocalDate;

public class Report {
    private final ReportCause reportCause;
    private final ReportedEntity reportedEntity;
    private final Profile reportingProfile;
    private final LocalDate timestamp;

    public Report(ReportCause reportCause, Profile reportingProfile, ReportedEntity reportedEntity) {
        this.reportCause = reportCause;
        this.reportingProfile = reportingProfile;
        this.reportedEntity = reportedEntity;
        this.timestamp = LocalDate.now();
    }

    public ReportCause getCause() {
        return reportCause;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public Profile getReportingProfile() {
        return reportingProfile;
    }

    public ReportedEntity getReportedEntity() {
        return reportedEntity;
    }
}
