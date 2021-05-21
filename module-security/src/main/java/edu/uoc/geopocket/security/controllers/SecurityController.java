package edu.uoc.geopocket.security.controllers;

import edu.uoc.geopocket.security.common.UserDetails;
import edu.uoc.geopocket.security.helper.SecurityContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {

    private final SecurityContextHelper securityContextHelper;

    @Autowired
    public SecurityController(final SecurityContextHelper securityContextHelper) {
        this.securityContextHelper = securityContextHelper;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserDetails login() {
        return securityContextHelper.getUserDetails();
    }
}
