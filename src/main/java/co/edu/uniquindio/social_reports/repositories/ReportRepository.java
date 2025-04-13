package co.edu.uniquindio.social_reports.repositories;

import co.edu.uniquindio.social_reports.model.entities.Report;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends MongoRepository<Report, ObjectId> {
    List<Report> findAllByCategoryId(ObjectId id);
}
