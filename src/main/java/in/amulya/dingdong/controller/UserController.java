package in.amulya.dingdong.controller;

import in.amulya.dingdong.model.ioobject.UserRequest;
import in.amulya.dingdong.model.ioobject.UserResponse;
import in.amulya.dingdong.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {
    private UserServiceImpl userService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody UserRequest userRequest) {
      return  userService.registerUser(userRequest);
    }
}
