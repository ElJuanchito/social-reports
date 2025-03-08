package co.edu.uniquindio.social_reports.services;

import co.edu.uniquindio.social_reports.dtos.report.*;
import co.edu.uniquindio.social_reports.model.enums.Category;

import java.util.List;

public interface ReportService {

    void createReport(CreateReportDTO reportDTO) throws Exception;
    void updateReport(String id, UpdateReportDTO reportDTO) throws Exception;
    void deleteReport(String id) throws Exception;
    ReportInfoDTO getReportInfo(String id) throws Exception;
    void addCommentToReport(String id, CommentDTO commentDTO) throws Exception;
    void setAsImportant(String id, String userId) throws Exception;
    void changeStatus(String id, ChangeStatusDTO changeStatusDTO) throws Exception;
    List<ReportInfoDTO> getReportsInfoByCategory(Category category) throws Exception;
    void checkReport(String id) throws Exception;
    void refuseReport(RefuseReportDTO reportDTO) throws Exception;
    void setAsResolved(String id) throws Exception;
    void createViewReport(ViewReportDTO dto) throws Exception; //TODO Falta por completar
}