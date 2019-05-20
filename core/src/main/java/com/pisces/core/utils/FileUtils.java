package com.pisces.core.utils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static String findJarPath() {
        String filePath = FileUtils.class.getProtectionDomain()
                .getCodeSource().getLocation().getFile();

        try {
            filePath = java.net.URLDecoder.decode(filePath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Path path = null;
        if (filePath.indexOf(".jar") == -1) {
            File f = new File(filePath);
            path = Paths.get(f.toURI());
        } else {
            try {
                path = Paths.get(new URI(filePath));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        if (filePath.indexOf(".jar") == -1) {
            return path.getParent().toString();
        }
        
        String tempPath = filePath.substring(0, filePath.indexOf(".jar"));
        if (tempPath.startsWith("file:")) {
            tempPath = tempPath.substring(5, tempPath.lastIndexOf("/"));
        } else {
            tempPath = tempPath.substring(0, tempPath.lastIndexOf("/"));
        }
        return tempPath;
    }

    public static boolean checkFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static String readFileContent(String filePath) {
        if (!checkFileExist(filePath)) {
            return "文件不存在:" + filePath;
        }
        StringBuffer stringBuffer = new StringBuffer("");
        try (FileReader fileReader = new FileReader(filePath);
        		BufferedReader reader = new BufferedReader(fileReader)) {
        	String str = null;
            while((str = reader.readLine()) != null){
                stringBuffer.append(str);
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
        return stringBuffer.toString();
    }

    public static void writeFile(String filePath,String content){
        if(!checkFileExist(filePath)) {
            try {
                File file = new File(filePath);
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileWriter writer = new FileWriter(filePath)) {
        	writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeFile(String filePath,String content, boolean append){
        if(!checkFileExist(filePath)){
            try {
                File file = new File(filePath);
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileWriter writer = new FileWriter(filePath, append)) {
        	writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
