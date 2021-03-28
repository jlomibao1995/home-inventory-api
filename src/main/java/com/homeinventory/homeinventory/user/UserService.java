package com.homeinventory.homeinventory.user;

import com.homeinventory.homeinventory.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void addNewUser(User user){
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        if (userOptional.isPresent()){
            throw new IllegalStateException("Email taken");
        }
        userRepository.save(user);
    }

    public void deleteUser(Long userId){

        boolean exists = userRepository.existsById(userId);
        if (!exists){
            throw new IllegalStateException("User with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(Long userId, String email, String name, boolean active, String password){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with user id " + userId + " does not exist"));

        if (name != null && name.length() > 0 && !Objects.equals(user.getName(), name)) {
            user.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)){
            Optional<User> userOptional = userRepository.findUserByEmail(email);

            if (userOptional.isPresent()){
                throw new IllegalStateException("Email taken");
            }

            user.setEmail(email);
        }

        if (!Objects.equals(user.isActive(), active)){
            user.setActive(active);
        }

        if (password != null && password.length() > 0 && !Objects.equals(user.getPassword(), password)){
            user.setPassword(passwordEncoder.encode(password));
        }
    }

    public User getUser(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);

        if(user.isPresent()){
            for (Item item: user.get().getItems()){
                item.setUser(null);
            }
            return user.get();
        } else {
            throw new IllegalArgumentException("User with that email does not exist");
        }
    }
}
