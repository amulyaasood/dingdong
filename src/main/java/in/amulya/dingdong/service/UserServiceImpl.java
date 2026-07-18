package in.amulya.dingdong.service;

import in.amulya.dingdong.model.entity.UserEntity;
import in.amulya.dingdong.model.ioobject.UserRequest;
import in.amulya.dingdong.model.ioobject.UserResponse;
import in.amulya.dingdong.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepo userRepo;
    PasswordEncoder passwordEncoder;
    AuthenticationFacade authenticationFacade;

    @Override
    public UserResponse registerUser(UserRequest userRequest) {
        UserEntity newUser =convertToUserEntity(userRequest);
        newUser = userRepo.save(newUser);
        UserResponse response = convertToUserResponse(newUser);

        // ADD THESE
        System.out.println("Saved entity: " + newUser);
        System.out.println("Response object: " + response);

        return response;
    }

    @Override
    public String findBYUserId() {
        String loggedUserMail =  authenticationFacade.getAuthentication().getName();
        UserEntity user = userRepo.findByEmail(loggedUserMail).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return user.getId();
    }


    private UserEntity convertToUserEntity(UserRequest userRequest) {
       return UserEntity.builder()
                .email(userRequest.getEmail())
                .name(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();
    }

    private UserResponse convertToUserResponse(UserEntity newUser) {
       return UserResponse.builder()
                .id(newUser.getId())
                .email(newUser.getEmail())
                .username(newUser.getName())
                .build();
    }
}
