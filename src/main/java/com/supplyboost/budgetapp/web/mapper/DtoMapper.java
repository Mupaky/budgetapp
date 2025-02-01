package com.supplyboost.budgetapp.web.mapper;

import com.supplyboost.budgetapp.user.model.User;
import com.supplyboost.budgetapp.web.dto.UserEditRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoMapper {

    public static UserEditRequest mapToUserEditRequest(User user){
        return UserEditRequest.builder()
                .fullName(user.getFullName())
                .country(user.getCountry())
                .profilePicture(user.getProfile_picture())
                .email(user.getEmail())
                .build();
    }
}
