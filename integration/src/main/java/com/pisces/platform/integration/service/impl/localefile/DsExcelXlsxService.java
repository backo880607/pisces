package com.pisces.platform.integration.service.impl.localefile;

import com.pisces.platform.core.utils.StringUtils;
import com.pisces.platform.integration.bean.DsLocaleFile;
import com.pisces.platform.integration.enums.LOCALE_FILE_TYPE;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;

public class DsExcelXlsxService extends DsExcelService<LOCALE_FILE_TYPE> {
    private static final String EXTENSION = "xlsx";

    @Override
    protected String getPath(DsLocaleFile excel, String tableName) {
        String path = excel.getHost();
        if (StringUtils.getTail(path, ".").equalsIgnoreCase(EXTENSION)) {
            multiSheet = true;
            return path;
        }
        multiSheet = false;
        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }
        return path + tableName + "." + EXTENSION;
    }

    @Override
    protected Workbook createBook() throws Exception {
        return new XSSFWorkbook();
    }

    @Override
    protected Workbook createBook(File file) throws Exception {
        return new XSSFWorkbook(new FileInputStream(file));
    }

}
