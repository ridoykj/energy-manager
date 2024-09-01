package com.itbd.energymanager.services.reports;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;

public class ReportImageFeeder {
    public static FileInputStream feed(boolean internal, String path) {
        try {
            File file = internal ? ResourceUtils.getFile(String.format("classpath:%s", StringUtils.cleanPath(path))) : new File(path);
            return new FileInputStream(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
