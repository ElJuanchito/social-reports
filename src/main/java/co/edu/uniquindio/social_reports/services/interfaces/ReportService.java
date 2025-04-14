package co.edu.uniquindio.social_reports.services.interfaces;

import co.edu.uniquindio.social_reports.dtos.report.*;
import co.edu.uniquindio.social_reports.model.entities.Category;
import co.edu.uniquindio.social_reports.model.enums.City;
import co.edu.uniquindio.social_reports.model.enums.ReportStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReportService {

    void createReport(CreateReportDTO reportDTO) throws Exception;
    void updateReport(String id, UpdateReportDTO reportDTO) throws Exception;
    void deleteReport(String id, DeleteReportDTO dto) throws Exception;
    ReportInfoDTO getReportInfo(String id) throws Exception;
    void addCommentToReport(String id, CommentDTO commentDTO) throws Exception;
    void setAsImportant(String id, String userId) throws Exception;
    void changeStatus(ChangeStatusDTO changeStatusDTO) throws Exception;
    List<ReportInfoDTO> getReportsInfoByCategory(String categoryName) throws Exception;
    void checkReport(ChangeStatusDTO dto) throws Exception;
    void refuseReport(RefuseReportDTO reportDTO) throws Exception;
    void setAsResolved(ChangeStatusDTO dto) throws Exception;
    void createViewReport(ViewReportDTO dto) throws Exception; //TODO Falta por completar
    List<City> getCities() throws Exception;
    List<ReportStatus> getStatuses() throws Exception;
    List<CategoryDTO> getCategories() throws Exception;
    void createCategory(CategoryDTO categoryDTO) throws Exception;
    List<CommentDTO> getAllCommentsFromReport(String reportId) throws Exception;
}