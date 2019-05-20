package com.pisces.report.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.report.bean.TreeNode;
import com.pisces.report.dao.TreeNodeDao;
import com.pisces.report.service.TreeNodeService;

@Service
public class TreeNodeServiceImpl extends EntityServiceImpl<TreeNode, TreeNodeDao> implements TreeNodeService {

}
