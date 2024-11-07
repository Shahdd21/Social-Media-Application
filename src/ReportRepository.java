import java.util.ArrayList;
import java.util.List;

public class ReportRepository {
    private static final List<Report> reportsList = new ArrayList<>();

    public static List<Report> getReportsList() {
        return reportsList;
    }

    public static void addReport(Report report){
        reportsList.add(report);
    }

    public static void displayReports(){
        for(Report report : reportsList){
            System.out.println(report.getTimestamp());
            System.out.println(report.getCause());
            System.out.println(report.getReportedEntity().getEntity());
            System.out.println(report.getReportingProfile().getMember().getUserName());
        }
    }
}
