package com.github.phpple.errorcode.web.controller;

import com.google.common.collect.ImmutableMap;
import com.github.phpple.errorcode.lib.exception.WebException;
import com.github.phpple.errorcode.lib.http.WebResponse;
import com.github.phpple.errorcode.service.dto.FooDto;
import com.github.phpple.errorcode.service.spi.FooService;
import com.github.phpple.errorcode.web.errorcode.CommonWebErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class FooController {
    @Autowired
    private FooService fooService;

    @GetMapping("/foo/detail/{id}")
    public WebResponse<FooDto> getFooDetail(
            @PathVariable(name = "id")
            @NotNull(message = "id is required")
            @Valid
            Long id
    ) {
        if (id <= 0) {
            throw new WebException(CommonWebErrorCode.BAD_PARAMETER, "Id must greater than 0. id:${id}", ImmutableMap.of("id", id + ""));
        }

        return WebResponse.success(fooService.getById(id));
    }
}
