package com.ais_db.mapper;

import com.ais_db.dto.HeadRespDto;
import com.ais_db.dto.OfficerRespoDto;
import com.ais_db.dto.UserRespDto;
import com.ais_db.model.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
//    public static User maptoUserEntity(OfficerRespoDto officerRespoDto) {
//        User user=new User();
//        user.setUsername(officerRespoDto.username());
//        user.setPassword(officerRespoDto.password());
//        return user;
//
//    }

    public static User maptoEntity(UserRespDto userRespDto) {
        User user=new User();
        user.setUsername(userRespDto.username());
        user.setPassword(userRespDto.password());
        return user;
    }

    public static User maptoHeadEntity(HeadRespDto headRespDto) {
        User user=new User();
        user.setUsername(headRespDto.username());
        user.setPassword(headRespDto.password());
        return user;
    }
}
