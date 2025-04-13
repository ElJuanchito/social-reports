package co.edu.uniquindio.social_reports.repositories;

import co.edu.uniquindio.social_reports.model.entities.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepository extends MongoRepository<Category, ObjectId> {

    Optional<Category> findCategoryByName(String name);
}
