package com.abc.system.controller;

import com.abc.common.domain.vo.ApiResult;
import com.abc.common.domain.vo.PageResult;
import com.abc.common.util.SecurityUtils;
import com.abc.system.domain.vo.UserVO;
import com.abc.system.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation("查询用户信息")
    @GetMapping("/info")
    public ApiResult<UserVO> getUserInfo() {
        UserVO userVO = userService.getUserInfoWithUiParam(SecurityUtils.getUserId());

        return ApiResult.success(userVO);
    }

    @ApiOperation("查询用户分页")
    @GetMapping("/page")
    public ApiResult<PageResult<UserVO>> getPage() {

        return ApiResult.success();
    }



}
