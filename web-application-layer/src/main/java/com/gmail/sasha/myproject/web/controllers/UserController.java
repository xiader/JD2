package com.gmail.sasha.myproject.web.controllers;


import com.gmail.sasha.myproject.service.model.UserDTO;
import com.gmail.sasha.myproject.service.service.UserService;
import com.gmail.sasha.myproject.web.util.PageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PageProperties pageProperties;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("userValidator")
    private Validator userValidator;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    public String getUsers(ModelMap modelMap) {
        List<UserDTO> users = userService.getUsers();
        modelMap.addAttribute("users", users);
        return pageProperties.getUsersPagePath();
    }


    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    public String getUser(@PathVariable("id") Long id, ModelMap modelMap) {
        UserDTO user = userService.getUserById(id);
        modelMap.addAttribute("user", user);
        return pageProperties.getUpdateUserPagePath();
    }

    @GetMapping("/{id}/password")
    @PreAuthorize("hasAuthority('CHANGE_USER_PASSWORD')")
    public String getUserPassword(
            @PathVariable("id") Long id,
            ModelMap modelMap
    ) {
        UserDTO user = userService.findById(id);
        modelMap.addAttribute("user", user);
        return pageProperties.getUserPasswordPagePath();
    }

    @PostMapping("/{id}/password")
    @PreAuthorize("hasAuthority('CHANGE_USER_PASSWORD')")
    public String updatePassword(
            @ModelAttribute("user") UserDTO user,
            @PathVariable("id") Long id,
            @RequestParam String password,
            ModelMap modelMap
    ) {
        user.setId(id);
        modelMap.addAttribute("user", user);
        userService.updatePassword(password, id);
        return "redirect:/users/{id}/password";
    }

    @GetMapping(value = "/{id}/profile")
    @PreAuthorize("hasAuthority('UPDATE_PROFILE')")
    public String getUserProfile(
            @PathVariable("id") Long id,
            ModelMap modelMap
    ) {
        UserDTO user = userService.findById(id);
        modelMap.addAttribute("user", user);
        return pageProperties.getProfilePagePath();
    }

    @GetMapping(value = "/{id}/enable")
    @PreAuthorize("hasAuthority('DISABLE_USER')")
    public String enableUser(
            @PathVariable("id") Long id
    ) {
        userService.enableUser(id);
        return "redirect:/users";
    }

    @GetMapping(value = "/{id}/disable")
    @PreAuthorize("hasAuthority('DISABLE_USER')")
    public String disableUser(
            @PathVariable("id") Long id
    ) {
        //todo termDisabled
        userService.disableUser(id);
        return "redirect:/users";
    }

    @GetMapping(value = "/{id}/delete")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public String deleteUser(
            @PathVariable("id") Long id
    ) {
        userService.softDeleteById(id);
        return "redirect:/users";
    }




    /* @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }*/


      /*  @GetMapping
    @PreAuthorize("hasAuthority('VIEW_USERS')")
    public String getUsers(ModelMap modelMap,
                           @RequestParam(value = "page", defaultValue = "1") Integer page) {
        Long elementsOnPage = pageProperties.getElementsOnPage();
        List<UserDTO> users = userService.findAllUsers(page, Math.toIntExact(elementsOnPage));
        Long pages = userService.getAmountOfPages();
        modelMap.addAttribute("users", users);
        modelMap.addAttribute("pages", pages);
        return pageProperties.getUsersPagePath();
    }*/


}
