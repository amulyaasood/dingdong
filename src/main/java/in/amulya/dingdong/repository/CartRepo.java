package in.amulya.dingdong.repository;

import in.amulya.dingdong.model.entity.CartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends MongoRepository<CartEntity , String> {
    Optional<CartEntity> findByUserId(String id);
    void deleteByUserId(String userId);

}
