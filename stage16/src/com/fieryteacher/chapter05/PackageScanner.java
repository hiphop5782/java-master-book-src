package com.fieryteacher.chapter05;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class PackageScanner {
	public static Set<Class<?>> scanAllPackage() {
		Package[] pkgList = Package.getPackages();//1
		String root = System.getProperty("user.dir");//2
		return Arrays.stream(pkgList)
        .map(pkg->pkg.getName())
        .map(pkgName->ClassLoader.getSystemClassLoader().getResource(pkgName.replace('.', '/')))//3
        .filter(Objects::nonNull)//4
        .map(url->new File(url.getFile()))//5
        .filter(File::isDirectory)
        .flatMap(dir->Arrays.stream(dir.listFiles()))//6
        .filter(file->file.getName().endsWith(".class"))//7
        .map(file->file.getAbsolutePath())
        .map(path->path.substring(root.length()+1))
        .map(path->(path.startsWith("bin") ? path.substring(4) : path))
        .map(path->path.replace('\\', '.'))
        .map(path->(path.endsWith(".class") ? path.substring(0, path.length()-6) : path))
        .map(path->{//8
          try {
            return Class.forName(path);
          } catch (ClassNotFoundException e) {
            return null;
          }
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());//9
	}
}
