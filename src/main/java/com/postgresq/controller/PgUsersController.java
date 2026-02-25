package com.postgresq.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.postgresq.api.dto.PgUserDtos.CreatePgUserRequest;
import com.postgresq.api.dto.PgUserDtos.PgUserResponse;
import com.postgresq.model.User;
import com.postgresq.service.PgUserService;

@RestController
@RequestMapping("/pg/users")
public class PgUsersController {

    private final PgUserService userService;

    public PgUsersController(PgUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<PgUserResponse> list() {
        return userService.listUsers().stream().map(PgUsersController::toResponse).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PgUserResponse create(@RequestBody CreatePgUserRequest request) {
        if (request == null || !request.isValid()) {
            throw new IllegalArgumentException("username, name, surname, email, and password are required");
        }
        return toResponse(userService.createUser(request));
    }

    private static PgUserResponse toResponse(User user) {
        return new PgUserResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getTimezone());
    }
}
