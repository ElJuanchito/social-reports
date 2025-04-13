package co.edu.uniquindio.social_reports.services.impl;

import co.edu.uniquindio.social_reports.dtos.report.*;
import co.edu.uniquindio.social_reports.exceptions.user.ReportNotBelongToUserException;
import co.edu.uniquindio.social_reports.model.entities.Category;
import co.edu.uniquindio.social_reports.model.entities.Report;
import co.edu.uniquindio.social_reports.model.enums.City;
import co.edu.uniquindio.social_reports.model.enums.ReportStatus;
import co.edu.uniquindio.social_reports.model.vo.Location;
import co.edu.uniquindio.social_reports.model.vo.ReportHistory;
import co.edu.uniquindio.social_reports.repositories.CategoryRepository;
import co.edu.uniquindio.social_reports.repositories.ReportRepository;
import co.edu.uniquindio.social_reports.services.interfaces.ImageService;
import co.edu.uniquindio.social_reports.services.interfaces.ReportService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    @Override
    public void createReport(CreateReportDTO reportDTO, MultipartFile[] images) throws Exception {

        Report report = dtoToEntity(reportDTO);

        List<String> urls = new ArrayList<> ();
        if(images != null && images.length > 0) {
            for (MultipartFile image : images) {
                Map uploadResult = imageService.uploadImage(image);
                String imageUrl = (String) uploadResult.get("secure_url");
                urls.add(imageUrl);
            }
        }
        report.setImages(urls);
        report.setCurrentStatus(ReportStatus.PENDING);

        ReportHistory reportHistory = ReportHistory.builder()
                .status(ReportStatus.PENDING)
                .date(LocalDateTime.now())
                .clientId(new ObjectId(reportDTO.userId()))
                .comments("New Report")
                .build();

        List<ReportHistory> reportHistories = new ArrayList<>();
        reportHistories.add(reportHistory);
        report.setHistory(reportHistories);
        reportRepository.save(report);
    }

    private Report dtoToEntity(CreateReportDTO reportDTO) {

        Category category = categoryRepository.findCategoryByName(reportDTO.categoryName())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return Report.builder()
                .title(reportDTO.title())
                .categoryId(category.getId())
                .description(reportDTO.description())
                .location(locationDtoToEntity(reportDTO.location()))
                .clientId(new ObjectId(reportDTO.userId()))
                .build();
    }

    private Location locationDtoToEntity(LocationDTO locationDTO) {
        return Location.builder()
                .latitude(locationDTO.latitude())
                .longitude(locationDTO.longitude())
                .build();
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
    public void changeStatus(ChangeStatusDTO changeStatusDTO) throws Exception {
        Report report = reportRepository.findById(new ObjectId(changeStatusDTO.reportId()))
                .orElseThrow(() -> new RuntimeException("Report not found"));

        if(!Objects.equals(report.getClientId(), new ObjectId(changeStatusDTO.userId()))) {
            throw new ReportNotBelongToUserException("The report does not belong to the user");
        }

        report.setCurrentStatus(ReportStatus.RESOLVED);
        ReportHistory reportHistory = ReportHistory.builder()
                .status(ReportStatus.RESOLVED)
                .date(LocalDateTime.now())
                .clientId(new ObjectId(changeStatusDTO.userId()))
                .comments(changeStatusDTO.reason())
                .build();

        List<ReportHistory> reportHistories = report.getHistory();
        reportHistories.add(reportHistory);
        report.setHistory(reportHistories);
        reportRepository.save(report);
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

    @Override
    public List<City> getCities() throws Exception {
        return List.of();
    }

    @Override
    public List<ReportStatus> getStatuses() throws Exception {
        return List.of();
    }

    @Override
    public List<CategoryDTO> getCategories() throws Exception {
        return List.of();
    }

    @Override
    public void createCategory(CategoryDTO categoryDTO) throws Exception {

        Category category = Category.builder()
                .name(categoryDTO.name())
                .build();

        categoryRepository.save(category);
    }
}
