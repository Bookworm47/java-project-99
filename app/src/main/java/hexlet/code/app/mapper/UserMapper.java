package hexlet.code.app.mapper;

import hexlet.code.app.model.User;
import hexlet.code.app.model.dto.UserCreateRequest;
import hexlet.code.app.model.dto.UserResponseDTO;
import hexlet.code.app.model.dto.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserResponseDTO mapUserToUserResponse(User user);

    List<UserResponseDTO> mapUsersToResponseList(List<User> users);

    User mapUserRequestToUser(UserCreateRequest userCreateRequest);

    void updateUser(UserUpdateRequest updateRequest, @MappingTarget User user);
}
