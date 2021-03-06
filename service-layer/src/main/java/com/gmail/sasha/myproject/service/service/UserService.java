package com.gmail.sasha.myproject.service.service;

import com.gmail.sasha.myproject.service.model.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO save(UserDTO userDTO);

    void assignDiscountToUser();

    List<UserDTO> getUsers();

    UserDTO validateByEmail(String email);

    UserDTO getUserById(Long id);

    List<UserDTO> findAllUsers(Integer page, Integer elementsOnPage);

    Long getAmountOfPages();

    UserDTO findById(Long id);

    void updatePassword(String password, Long id);

    void enableUser(Long userId);

    void disableUser(Long userId);

    void softDeleteById(Long userId);
}
