package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.integration.bean.DsLocaleFile;
import com.pisces.integration.dao.DsLocaleFileDao;
import com.pisces.integration.service.DsLocaleFileService;

@Service
public class DsLocaleFileServiceImpl extends EntityServiceImpl<DsLocaleFile, DsLocaleFileDao> implements DsLocaleFileService {

}
