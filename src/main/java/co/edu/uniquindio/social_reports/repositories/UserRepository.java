package co.edu.uniquindio.social_reports.repositories;

import co.edu.uniquindio.social_reports.model.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    boolean existsByEmailIgnoreCase(String email);
}
