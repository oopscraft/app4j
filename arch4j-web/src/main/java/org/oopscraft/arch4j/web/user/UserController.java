package org.oopscraft.arch4j.web.user;

import lombok.RequiredArgsConstructor;
import org.oopscraft.arch4j.core.user.UserStatus;
import org.oopscraft.arch4j.core.user.UserType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ModelAndView user() {
        ModelAndView modelAndView = new ModelAndView("user/user.html");
        modelAndView.addObject("userTypes", UserType.values());
        modelAndView.addObject("userStatuses", UserStatus.values());
        return modelAndView;
    }

    @GetMapping("change-password")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView changePassword() {
        return new ModelAndView("user/change-password.html");
    }

}
