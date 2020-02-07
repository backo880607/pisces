package com.pisces.platform.user.service.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.user.bean.MenuAccess;
import com.pisces.platform.user.dao.MenuAccessDao;
import com.pisces.platform.user.service.MenuAccessService;
import org.springframework.stereotype.Service;

/**
 * @ClassName MenuAccessServiceImpl
 * @Description service层实现
 * @Author Jason
 * @Date 2020-02-06
 * @ModifyDate 2020-02-06
 */
@Service
public class MenuAccessServiceImpl extends EntityServiceImpl<MenuAccess, MenuAccessDao> implements MenuAccessService {
}
