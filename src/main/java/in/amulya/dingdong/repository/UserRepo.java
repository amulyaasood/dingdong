package in.amulya.dingdong.repository;

import in.amulya.dingdong.model.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
@Repository
public interface UserRepo extends MongoRepository<UserEntity,String> {


    Optional<UserEntity> findByEmail(String email);
}
