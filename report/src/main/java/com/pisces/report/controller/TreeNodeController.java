package com.pisces.report.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.report.bean.TreeNode;
import com.pisces.report.service.TreeNodeService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/report/TreeNode")
public class TreeNodeController extends EntityController<TreeNode, TreeNodeService> {

}
