package com.github.phpple.errorcode.service.spi.impl;

import com.google.common.collect.ImmutableMap;
import com.github.phpple.errorcode.service.dto.FooDto;
import com.github.phpple.errorcode.service.errorcode.FooServiceErrorCode;
import com.github.phpple.errorcode.service.exception.FooException;
import com.github.phpple.errorcode.service.spi.FooService;
import org.springframework.stereotype.Service;

@Service
public class FooServiceImpl implements FooService {
    @Override
    public FooDto getById(Long id) {
        if (id < 100L) {
            throw new FooException(FooServiceErrorCode.FOO_NOT_FOUND, ImmutableMap.of("id", Long.toString(id)));
        }
        FooDto dto = new FooDto();
        dto.setId(id);
        dto.setName(String.format("name-%06d",id));
        return dto;
    }
}
