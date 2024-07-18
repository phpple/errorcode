package com.github.phpple.errorcode.service.spi;

import com.github.phpple.errorcode.service.dto.FooDto;

public interface FooService {
    FooDto getById(Long id);
}
