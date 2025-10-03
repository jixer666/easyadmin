package com.abc.system.convert;

import cn.hutool.core.bean.BeanUtil;
import com.abc.common.constant.CommonConstants;
import com.abc.system.domain.dto.RegisterDTO;
import com.abc.common.domain.entity.User;
import com.abc.system.domain.vo.UserVO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class UserConvert {

    public static User convertToUserByRegisterDTO(RegisterDTO registerDTO) {
        User user = User.builder()
                .username(registerDTO.getUsername())
                .password(new BCryptPasswordEncoder().encode(registerDTO.getPassword()))
                .nickname(CommonConstants.DEFAULT_NICKNAME)
                .avatar(CommonConstants.DEFAULT_AVATAR)
                .build();
        user.setCommonParams();
        return user;
    }

    public static UserVO convertToUserVO(User user, List<String> roleKeys) {
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        userVO.setRoles(roleKeys);
        return userVO;
    }

}
