package com.pisces.platform.report.controller;

import com.pisces.platform.report.bean.TreeNode;
import com.pisces.platform.report.service.TreeNodeService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report/TreeNode")
public class TreeNodeController extends EntityController<TreeNode, TreeNodeService> {

}
