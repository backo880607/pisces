package com.pisces.platform.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.platform.bean.Form;
import com.pisces.platform.service.FormService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/Form")
public class FormController extends EntityController<Form, FormService> {

}
