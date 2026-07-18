package in.amulya.dingdong.service;

import in.amulya.dingdong.model.ioobject.UserRequest;
import in.amulya.dingdong.model.ioobject.UserResponse;

public interface UserService {

    UserResponse registerUser(UserRequest userRequest);
    String findBYUserId();
}
