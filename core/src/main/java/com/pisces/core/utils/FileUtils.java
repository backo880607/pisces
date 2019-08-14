package com.pisces.core.utils;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
    
    /**
	 * 将指定包下的类加载到内存中
	 * @param packName
	 */
	public static List<Class<?>> loadClass(String packName) {
		List<Class<?>> clses = new ArrayList<>();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String strFile = packName.replaceAll("\\.", "/");
		try {
			Enumeration<URL> urls = loader.getResources(strFile);
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				if (url != null) {
                	String protocol = url.getProtocol();
                	String filePath = url.getPath();
                	if (protocol.equals("file")) {
                		loadClassImpl(packName, filePath, clses);
                	} else if (protocol.equals("jar")) {
                		loadJarImpl(packName, url, clses);
                	}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return clses;
	}
	
	private static void loadClassImpl(String packName, String dirPath, List<Class<?>> clses) {
		File dir = new File(dirPath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		
		File[] dirFiles = dir.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File file) {
				return file.isDirectory() || file.getName().endsWith(".class");
			}
		});
		
		for (File file : dirFiles) {
			if (file.isDirectory()) {
				loadClassImpl(packName + "." + file.getName(), dirPath + "/" + file.getName(), clses);
			} else {
				String clsName = file.getName();
				clsName = clsName.substring(0, clsName.length() - 6);
				try {
					clses.add(Class.forName(packName + "." + clsName));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void loadJarImpl(String packName, URL url, List<Class<?>> clses) throws IOException {
		JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
		JarFile jarFile = jarURLConnection.getJarFile();
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		while (jarEntries.hasMoreElements()) {
			JarEntry jarEntry = jarEntries.nextElement();
			final String jarEntryName = jarEntry.getName();
			if (jarEntryName.endsWith(".class")) {
				String clsName = jarEntryName.replace("/", ".");
				if (clsName.startsWith(packName) && !clsName.contains("com.mwc2m.aps.bean.dto")
						&& !clsName.contains("com.mwc2m.aps.bean.enums")
						&& !clsName.contains("com.mwc2m.aps.bean.vo")) {
					clsName = clsName.substring(0, clsName.length() - 6);
					try {
						clses.add(Class.forName(clsName));
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
