package co.edu.uniquindio.social_reports.services;

import co.edu.uniquindio.social_reports.dtos.report.*;

public interface ReportService {

    void createReport(CreateReportDTO reportDTO) throws Exception;
    void updateReport(String id, UpdateReportDTO reportDTO) throws Exception;
    void deleteReport(String id) throws Exception;
    ReportInfoDTO getReportInfo(String id) throws Exception;
    void addCommentToReport(String id, CommentDTO commentDTO) throws Exception;
    void setAsImportant(String id, String userId) throws Exception;
    void changeStatus(String id, ChangeStatusDTO changeStatusDTO) throws Exception;
}
