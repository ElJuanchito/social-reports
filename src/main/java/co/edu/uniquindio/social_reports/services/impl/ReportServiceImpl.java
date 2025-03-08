package co.edu.uniquindio.social_reports.services.impl;

import co.edu.uniquindio.social_reports.dtos.report.*;
import co.edu.uniquindio.social_reports.model.enums.Category;
import co.edu.uniquindio.social_reports.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
//TODO add @Transaccional
public class ReportServiceImpl implements ReportService {

    //TODO add private final ReportRepository reportRepository;

    @Override
    public void createReport(CreateReportDTO reportDTO) throws Exception {
        //TODO
    }

    @Override
    public void updateReport(String id, UpdateReportDTO reportDTO) throws Exception {
        //TODO
    }

    @Override
    public void deleteReport(String id) throws Exception {
        //TODO
    }

    @Override
    public ReportInfoDTO getReportInfo(String id) throws Exception {
        return null;
        //TODO
    }

    @Override
    public void addCommentToReport(String id, CommentDTO commentDTO) throws Exception {
        //TODO
    }

    @Override
    public void setAsImportant(String id, String userId) throws Exception {
        //TODO
    }

    @Override
    public void changeStatus(String id, ChangeStatusDTO changeStatusDTO) throws Exception {
        //TODO
    }

    @Override
    public List<ReportInfoDTO> getReportsInfoByCategory(Category category) throws Exception {
        return List.of();
    }

    @Override
    public void checkReport(String id) throws Exception {

    }

    @Override
    public void refuseReport(RefuseReportDTO reportDTO) throws Exception {

    }

    @Override
    public void setAsResolved(String id) throws Exception {

    }

    @Override
    public void createViewReport(ViewReportDTO dto) throws Exception {

    }
}
