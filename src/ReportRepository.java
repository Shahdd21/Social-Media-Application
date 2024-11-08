import java.util.ArrayList;
import java.util.List;

public class ReportRepository {
    private static final List<Report> reportsList = new ArrayList<>();

    public static List<Report> getReportsList(Member admin) {
        if(admin.isAdmin()) return reportsList;
        else return null;
    }

    public static void addReport(Report report){
        reportsList.add(report);
    }

    public static void displayReports(Member admin){

        if(admin.isAdmin()) {
            for (Report report : reportsList) {
                System.out.println(report.getTimestamp());
                System.out.println(report.getCause());
                System.out.print(report.getReportedEntity().getType()+": ");
                System.out.println(report.getReportedEntity().getID());
                System.out.println(report.getReportingProfile().getMember().getUserName());
            }
        }
        else System.out.println("Non-authorized access");
    }
}
