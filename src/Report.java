import java.time.LocalDate;

public class Report {
    private  ReportCause reportCause;
    private  ReportedEntity reportedEntity;
    private  Profile reportingProfile;
    private final LocalDate timestamp;

    public Report() {
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

    public void setReportingProfile(Profile reportingProfile) {
        this.reportingProfile = reportingProfile;
    }

    public void setReportedEntity(ReportedEntity reportedEntity) {
        this.reportedEntity = reportedEntity;
    }

    public void setReportCause(ReportCause reportCause) {
        this.reportCause = reportCause;
    }
}
