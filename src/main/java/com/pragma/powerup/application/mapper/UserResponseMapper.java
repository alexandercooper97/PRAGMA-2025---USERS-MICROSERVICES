package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.UserSimpleResponseDto;
import com.pragma.powerup.application.dto.response.UserResponseDTO;
import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserResponseMapper {
    @Mapping(target = "role", qualifiedByName= "getRoleName")
    UserResponseDTO toResponse(UserModel userModel);
    @Mapping(target = "role", qualifiedByName= "getRoleName")
    UserSimpleResponseDto toResponseSimple(UserModel userModel);

    List<UserResponseDTO> toResponseList(List<UserModel> userModelList);


    @Named("getRoleName")
    default String getRoleName(RoleModel roleModel) {
        if (roleModel != null) {
            return roleModel.getName();
        }
        return "";
    }
}
