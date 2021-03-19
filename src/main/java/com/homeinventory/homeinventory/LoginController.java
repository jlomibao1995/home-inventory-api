package com.homeinventory.homeinventory;

import com.homeinventory.homeinventory.user.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;

@RestController
public class LoginController {

    @RequestMapping("/inventory/api/v1/login")
    public boolean login(@RequestBody User user){
        return
                user.getEmail().equals("user") && user.getPassword().equals("password");
    }

    @CrossOrigin
    @RequestMapping("/inventory/api/v1/authenticate")
    public Principal user(Principal user){
        return user;
    }
}
