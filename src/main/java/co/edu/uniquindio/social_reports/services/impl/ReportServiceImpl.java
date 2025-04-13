package co.edu.uniquindio.social_reports.services.impl;

import co.edu.uniquindio.social_reports.dtos.report.*;
import co.edu.uniquindio.social_reports.exceptions.Report.ReportDeletedException;
import co.edu.uniquindio.social_reports.exceptions.Report.ReportNotBelongToUserException;
import co.edu.uniquindio.social_reports.exceptions.Report.ReportNotExistException;
import co.edu.uniquindio.social_reports.exceptions.user.*;
import co.edu.uniquindio.social_reports.model.entities.Category;
import co.edu.uniquindio.social_reports.model.entities.Comment;
import co.edu.uniquindio.social_reports.model.entities.Report;
import co.edu.uniquindio.social_reports.model.entities.User;
import co.edu.uniquindio.social_reports.model.enums.City;
import co.edu.uniquindio.social_reports.model.enums.ReportStatus;
import co.edu.uniquindio.social_reports.model.enums.UserStatus;
import co.edu.uniquindio.social_reports.model.vo.Location;
import co.edu.uniquindio.social_reports.model.vo.ReportHistory;
import co.edu.uniquindio.social_reports.repositories.CategoryRepository;
import co.edu.uniquindio.social_reports.repositories.CommentRepository;
import co.edu.uniquindio.social_reports.repositories.ReportRepository;
import co.edu.uniquindio.social_reports.repositories.UserRepository;
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
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
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

    @Override
    public void updateReport(String id, UpdateReportDTO reportDTO, MultipartFile[] images) throws Exception {
        Report report = reportRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("Report not found"));

        if(!Objects.equals(report.getClientId(), new ObjectId(reportDTO.userId()))) {
            throw new ReportNotBelongToUserException("The report does not belong to the user");
        }

        if (report.getCurrentStatus() == ReportStatus.DELETED){
            throw new ReportDeletedException("This report has been deleted and cannot be edited.");
        }

        Category category = categoryRepository.findCategoryByName(reportDTO.categoryName())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        report.setTitle(reportDTO.title());
        report.setDescription(reportDTO.description());
        report.setLocation(locationDtoToEntity(reportDTO.location()));
        report.setCategoryId(category.getId());

        List<String> urls = new ArrayList<> ();
        if(images != null && images.length > 0) {
            for (MultipartFile image : images) {
                Map uploadResult = imageService.uploadImage(image);
                String imageUrl = (String) uploadResult.get("secure_url");
                urls.add(imageUrl);
            }
        }
        report.setImages(urls);
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
    public void deleteReport(String id, DeleteReportDTO deleteReportDTO) throws Exception {

        Report report = reportRepository.findById(new ObjectId(id))
                .orElseThrow(()-> new RuntimeException("Report not found"));

        report.setCurrentStatus(ReportStatus.DELETED);
        ReportHistory reportHistory = ReportHistory.builder()
                .status(ReportStatus.DELETED)
                .date(LocalDateTime.now())
                .clientId(new ObjectId(deleteReportDTO.userID()))
                .comments("Report deleted")
                .build();

        List<ReportHistory> reportHistories = report.getHistory();
        reportHistories.add(reportHistory);
        report.setHistory(reportHistories);

        reportRepository.save(report);
    }

    @Override
    public ReportInfoDTO getReportInfo(String id) throws Exception {
        Report report = reportRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("Report not found"));

        Category category = categoryRepository.findById(report.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        User user = userRepository.findById(report.getClientId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new ReportInfoDTO(
                report.getTitle(),
                category,
                report.getDescription(),
                report.getLocation(),
                report.getImages(),
                report.getClientId().toString(),
                report.getCurrentStatus()
        );
    }

    @Override
    public void addCommentToReport(String id, CommentDTO commentDTO) throws Exception {

        Report report = reportRepository.findById(new ObjectId(id))
                .orElseThrow(()-> new ReportNotExistException("Report not found"));

        if(report.getCurrentStatus() == ReportStatus.DELETED){
            throw new ReportDeletedException("This report has been deleted");
        }

        if(!userRepository.existsById(new ObjectId(commentDTO.userId()))){
            throw new UserNotExistsException("User not found");
        }

        Comment comment = Comment.builder()
                .reportId(new ObjectId(id))
                .message(commentDTO.comment())
                .date(LocalDateTime.now())
                .clientId(new ObjectId(commentDTO.userId()))
                .build();

        commentRepository.save(comment);
    }

    @Override
    public void setAsImportant(String id, String userId) throws Exception {
        Report report = reportRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("Report not found"));

        User user = userRepository.findById(new ObjectId(userId))
                .orElseThrow(()-> new UserNotExistsException("User not found"));

        if(user.getStatus() == UserStatus.INACTIVE || user.getStatus() == UserStatus.DELETED){
            throw new UserNotActiveException("User without activating account");
        }

        if(report.getCurrentStatus() == ReportStatus.DELETED){
            throw new ReportDeletedException("This report has been deleted and cannot be edited.");
        }

        report.setImportanceCount(report.getImportanceCount()+1);
        reportRepository.save(report);
    }

    @Override
    public void changeStatus(ChangeStatusDTO changeStatusDTO) throws Exception {
        Report report = reportRepository.findById(new ObjectId(changeStatusDTO.reportId()))
                .orElseThrow(() -> new RuntimeException("Report not found"));

        if(!Objects.equals(report.getClientId(), new ObjectId(changeStatusDTO.userId()))) {
            throw new ReportNotBelongToUserException("The report does not belong to the user");
        }

        if(report.getCurrentStatus() == ReportStatus.DELETED){
            throw new ReportDeletedException("This report has been deleted and cannot be edited.");
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
    public List<ReportInfoDTO> getReportsInfoByCategory(String categoryName) throws Exception {
        Category category = categoryRepository.findCategoryByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        List<Report> reports = reportRepository.findAllByCategoryId(category.getId());
        return reports
                .stream()
                .map(this::ReportToReportInfoDTO)
                .toList();
    }

    private ReportInfoDTO ReportToReportInfoDTO(Report report) {

        Category category = categoryRepository.findById(report.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return new ReportInfoDTO(
                report.getTitle(),
                category,
                report.getDescription(),
                report.getLocation(),
                report.getImages(),
                report.getClientId().toString(),
                report.getCurrentStatus());
    }

    @Override
    public void checkReport(ChangeStatusDTO dto) throws Exception {
        Report report = reportRepository.findById(new ObjectId(dto.reportId()))
                .orElseThrow(() -> new RuntimeException("Report not found"));

        if(report.getCurrentStatus() == ReportStatus.DELETED){
            throw new ReportDeletedException("This report has been deleted and cannot be edited.");
        }

        report.setCurrentStatus(ReportStatus.VERIFIED);
        ReportHistory reportHistory = ReportHistory.builder()
                .status(ReportStatus.VERIFIED)
                .date(LocalDateTime.now())
                .clientId(new ObjectId(dto.userId()))
                .comments("Report verified")
                .build();
        List<ReportHistory> reportHistories = report.getHistory();
        reportHistories.add(reportHistory);
        report.setHistory(reportHistories);

        reportRepository.save(report);
    }

    @Override
    public void refuseReport(RefuseReportDTO reportDTO) throws Exception {

        Report report = reportRepository.findById(new ObjectId(reportDTO.reportId()))
                .orElseThrow(() -> new RuntimeException("Report not found"));

        if(report.getCurrentStatus() == ReportStatus.DELETED){
            throw new ReportDeletedException("This report has been deleted and cannot be edited.");
        }

        report.setCurrentStatus(ReportStatus.REFUSED);
        ReportHistory reportHistory = ReportHistory.builder()
                .status(ReportStatus.REFUSED)
                .date(LocalDateTime.now())
                .clientId(new ObjectId(reportDTO.userId()))
                .comments(reportDTO.refuseMotive())
                .build();
        List<ReportHistory> reportHistories = report.getHistory();
        reportHistories.add(reportHistory);
        report.setHistory(reportHistories);

        reportRepository.save(report);
    }

    @Override
    public void setAsResolved(ChangeStatusDTO dto) throws Exception {
        Report report = reportRepository.findById(new ObjectId(dto.reportId()))
                .orElseThrow(() -> new RuntimeException("Report not found"));

        if(report.getCurrentStatus() == ReportStatus.DELETED){
            throw new ReportDeletedException("This report has been deleted and cannot be edited.");
        }

        report.setCurrentStatus(ReportStatus.RESOLVED);

        ReportHistory reportHistory = ReportHistory.builder()
                .status(ReportStatus.RESOLVED)
                .date(LocalDateTime.now())
                .clientId(new ObjectId(dto.userId()))
                .comments(dto.reason())
                .build();

        List<ReportHistory> reportHistories = report.getHistory();
        reportHistories.add(reportHistory);
        report.setHistory(reportHistories);
        reportRepository.save(report);
    }

    @Override
    public void createViewReport(ViewReportDTO dto) throws Exception {

    }

    @Override
    public List<City> getCities() throws Exception {
        return Arrays.asList(City.values());
    }

    @Override
    public List<ReportStatus> getStatuses() throws Exception {
        return Arrays.asList(ReportStatus.values());
    }

    @Override
    public List<CategoryDTO> getCategories() throws Exception {
        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryDTO(category.getName()))
                .toList();
    }

    @Override
    public void createCategory(CategoryDTO categoryDTO) throws Exception {

        Category category = Category.builder()
                .name(categoryDTO.name())
                .build();

        categoryRepository.save(category);
    }
}
