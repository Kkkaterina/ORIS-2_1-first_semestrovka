package com.vintageshop.service;

import com.vintageshop.dao.UserDao;
import com.vintageshop.entity.User;
import com.vintageshop.util.HashUtil;

import java.util.Optional;
import java.util.regex.Pattern;

public class UserService {
    private final UserDao userDao;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public UserService() {
        this.userDao = new UserDao();
    }

    public boolean registerUser(String email, String password, String name) {

        if (!isValidEmail(email)) {
            return false;
        }

        if (userDao.findByEmail(email).isPresent()) {
            return false;
        }

        if (password == null || password.length() < 6) {
            return false;
        }

        String hashedPassword = HashUtil.hashPassword(password);



        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setName(name);
        user.setRole("USER");
        userDao.save(user);
        return true;
    }

    public Optional<User> authenticate(String email, String password) {
        Optional<User> userOptional = userDao.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (HashUtil.checkPassword(password, user.getPassword())) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }


    public boolean updateUserProfile(long userId, String name, String email) {
        try {
            Optional<User> userOptional = userDao.findById(userId);
            if (userOptional.isEmpty()) {
                return false;
            }

            User user = userOptional.get();

            Optional<User> existingUser = userDao.findByEmail(email);
            if (existingUser.isPresent() && !existingUser.get().getId().equals(userId)) {
                return false;
            }

            user.setName(name);
            user.setEmail(email);
            userDao.update(user);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }


    public boolean deleteUser(long userId) {
        try {
            userDao.delete(userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}