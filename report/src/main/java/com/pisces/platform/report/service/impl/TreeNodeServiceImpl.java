package com.pisces.platform.report.service.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.report.bean.TreeNode;
import com.pisces.platform.report.dao.TreeNodeDao;
import com.pisces.platform.report.service.TreeNodeService;
import org.springframework.stereotype.Service;

@Service
public class TreeNodeServiceImpl extends EntityServiceImpl<TreeNode, TreeNodeDao> implements TreeNodeService {

}
