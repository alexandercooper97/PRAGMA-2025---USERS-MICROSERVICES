package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.UserAuthRequestDto;
import com.pragma.powerup.domain.model.UserAuthModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserAuthRequestMapper {
    UserAuthModel toUserAuthModel(UserAuthRequestDto userAuthRequestDto);
}
