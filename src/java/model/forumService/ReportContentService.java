package model.forumService;

import model.Reportcontent;
import javax.persistence.*;
import java.util.List;
import model.Thread;
import model.Threadcomment;
import model.Users;

public class ReportContentService {
    @PersistenceContext
    private EntityManager mgr;

    public ReportContentService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public String generateNextReportId() {
        try {
            String lastId = mgr.createQuery(
                    "SELECT r.reportcontentid FROM Reportcontent r ORDER BY r.reportcontentid DESC", 
                    String.class)
                    .setMaxResults(1)
                    .getSingleResult();
            int number = Integer.parseInt(lastId.substring(3));
            return String.format("RPT%05d", number + 1);
        } catch (Exception e) {
            return "RPT00001";
        }
    }

    public void addReport(Reportcontent report) {
        mgr.persist(report);
    }

    public List<Reportcontent> findAllReports() {
        TypedQuery<Reportcontent> query = mgr.createQuery(
            "SELECT r FROM Reportcontent r ORDER BY r.reportcontentid DESC", 
            Reportcontent.class
        );
        return query.getResultList();
    }

    public List<Reportcontent> findUnsolvedReports() {
        TypedQuery<Reportcontent> query = mgr.createQuery(
            "SELECT r FROM Reportcontent r WHERE r.issolved = false ORDER BY r.reportcontentid DESC", 
            Reportcontent.class
        );
        return query.getResultList();
    }

    public Reportcontent findReportById(String reportId) {
        return mgr.find(Reportcontent.class, reportId);
    }

    public boolean markReportAsSolved(String reportId) {
        Reportcontent report = findReportById(reportId);
        if (report != null) {
            report.setIssolved(true);
            mgr.merge(report);
            return true;
        }
        return false;
    }

    public List<Reportcontent> findReportsByThread(String threadId) {
        TypedQuery<Reportcontent> query = mgr.createQuery(
            "SELECT r FROM Reportcontent r WHERE r.threadid.threadid = :threadId ORDER BY r.reportcontentid DESC", 
            Reportcontent.class
        );
        query.setParameter("threadId", threadId);
        return query.getResultList();
    }

    public List<Reportcontent> findReportsByComment(String commentId) {
        TypedQuery<Reportcontent> query = mgr.createQuery(
            "SELECT r FROM Reportcontent r WHERE r.threadcommentid.threadcommentid = :commentId ORDER BY r.reportcontentid DESC", 
            Reportcontent.class
        );
        query.setParameter("commentId", commentId);
        return query.getResultList();
    }
}