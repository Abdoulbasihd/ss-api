package com.spacestudent.ssapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * No need token to access to these methods
 */
@RestController
@RequestMapping("/api/public")
public class SSPublicController {

    @GetMapping
    public String getMessage() {
        return "Hello from public API controller";
    }

}
