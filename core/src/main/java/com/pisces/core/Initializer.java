package com.pisces.core;

//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
import java.util.List;

import com.pisces.core.locale.LocaleManager;
import com.pisces.core.primary.expression.EnumHelper;
import com.pisces.core.utils.EntityUtils;

public class Initializer {
	private final static String dllCore = "core";
	private final static String dllName =  "primary";
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	public static boolean isWindows() {
        return OS.indexOf("windows") >= 0;
    }
	
	public static boolean isLinux() {
        return OS.indexOf("linux") >= 0;
    }
	
	public static boolean isMacOS() {
        return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") < 0;
    }
	
	public static boolean isMacOSX(){
        return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") > 0;
    }
	
	/*static {
		String extensionName = "";
		if(isWindows()){  
		  extensionName = "dll";
		} else if (isLinux()) {
			extensionName = "so";
		} else if (isMacOS() || isMacOSX()) {
			extensionName = "dylib";
		}
		try {
			URL url = Initializer.class.getClass().getResource("/" + dllName + "." + extensionName);
			String protocol = url.getProtocol();
			String desDir = url.getPath();
			if (protocol.equals("file")) {
				String coreDir = desDir.substring(0, desDir.lastIndexOf("/") + 1) + dllCore + "." + extensionName;
				System.load(coreDir);
				System.load(desDir);
			} else if (protocol.equals("jar")) {
				desDir = FileUtils.findJarPath() + File.separator;
				File dllFile = new File(desDir + dllName + "." + extensionName);
				if (dllFile.exists()) {
					dllFile.delete();
				}
				dllFile.createNewFile();
				try (FileOutputStream out = new FileOutputStream(dllFile);
						InputStream in = url.openStream()) {
					int i;
					byte [] buf = new byte[1024];
					while((i = in.read(buf)) != -1) {
						out.write(buf, 0, i);
					}
				}
				System.load(dllFile.toString());
			}
		} catch (IOException e) {
		}
	}*/
	
	public static native void load(List<Class<?>> classes);
	
	public static void execute() {
		try {
			EnumHelper.init();
			EntityUtils.init();
			LocaleManager.init();
			//load(EntityUtil.getEntityClasses());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
