package co.edu.uniquindio.social_reports.services.impl;

import co.edu.uniquindio.social_reports.dtos.report.*;
import co.edu.uniquindio.social_reports.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
