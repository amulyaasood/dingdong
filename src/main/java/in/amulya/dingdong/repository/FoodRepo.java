package in.amulya.dingdong.repository;

import in.amulya.dingdong.model.entity.FoodEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepo extends MongoRepository<FoodEntity, String> {
}
