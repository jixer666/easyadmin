package com.abc.system.service.impl;

import com.abc.system.domain.entity.Menu;
import com.abc.system.mapper.MenuMapper;
import com.abc.system.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
}
