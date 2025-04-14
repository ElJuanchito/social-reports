package co.edu.uniquindio.social_reports.repositories;

import co.edu.uniquindio.social_reports.model.entities.Report;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends MongoRepository<Report, ObjectId> {
    List<Report> findAllByCategoryId(ObjectId id);

    @Query("{ 'date': { $gte: ?0, $lte: ?1 }, 'categoryId': ?2 }")
    List<Report> findReportsByDateBetweenAndCategoryId(LocalDateTime dateAfter, LocalDateTime dateBefore, ObjectId categoryId);
}
