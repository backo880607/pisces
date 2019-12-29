package com.pisces.platform.core;

//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;

import com.pisces.platform.core.config.BaseConfiguration;
import com.pisces.platform.core.locale.LocaleManager;
import com.pisces.platform.core.primary.expression.EnumHelper;
import com.pisces.platform.core.utils.EntityUtils;

import java.util.List;

public class Initializer {
    private static final String DLL_CORE = "core";
    private static final String DLL_NAME = "primary";
    private static final String OS = System.getProperty("os.name").toLowerCase();

    protected Initializer() {}

    public static boolean isWindows() {
        return OS.indexOf("windows") >= 0;
    }

    public static boolean isLinux() {
        return OS.indexOf("linux") >= 0;
    }

    public static boolean isMacOS() {
        return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") < 0;
    }

    public static boolean isMacOSX() {
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
			URL url = Initializer.class.getClass().getResource("/" + DLL_NAME + "." + extensionName);
			String protocol = url.getProtocol();
			String desDir = url.getPath();
			if (protocol.equals("file")) {
				String coreDir = desDir.substring(0, desDir.lastIndexOf("/") + 1) + DLL_CORE + "." + extensionName;
				System.load(coreDir);
				System.load(desDir);
			} else if (protocol.equals("jar")) {
				desDir = FileUtils.findJarPath() + File.separator;
				File dllFile = new File(desDir + DLL_NAME + "." + extensionName);
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
            BaseConfiguration.invokeInit();
            //load(EntityUtil.getEntityClasses());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
