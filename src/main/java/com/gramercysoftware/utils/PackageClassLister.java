package com.gramercysoftware.utils;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.commons.lang.StringUtils;

public class PackageClassLister {
	private static final String JAR_PROTOCOL = "jar";

	public static List<Class<?>> getClasses(String pkg) {
		return getClasses(pkg, null);
	}

	public static List<Class<?>> getClasses(String pkg, String pattern) {
		List<Class<?>> classes = new ArrayList<Class<?>>();

		// Get a File object for the package
		File directory = null;
		String path = null;
		URL resource = null;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			path = pkg.replace('.', '/');
			resource = classLoader.getResource(path);
			directory = new File(URLDecoder.decode(resource.getFile(), "UTF-8"));
		} catch (Exception x) {
			throw new RuntimeException(MessageFormat.format(
				">{0}< does not appear to be a valid package (searching real directory {1}).", new Object[] { pkg, path }), x);
		}

		if (resource.getProtocol().equals(JAR_PROTOCOL)) {
			classes = searchJar(pkg, StringUtils.substringBefore(resource.getFile(), "!"), pattern);
		} else {
			classes = searchDirectory(pkg, directory, pattern);
		}
		return classes;
	}

	private static List<Class<?>> searchDirectory(String pkg, File directory, String pattern) {
		List<Class<?>> classes = new ArrayList<Class<?>>();

		if (directory.exists()) {
			// Get the list of the files contained in the package
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				// we are only interested in .class files
				if (files[i].endsWith(".class") && matchesPattern(files[i], pattern)) {
					// removes the .class extension
					String className = pkg + '.' + files[i].substring(0, files[i].length() - 6);
					try {
						classes.add(Class.forName(className));
					} catch (ClassNotFoundException e) {
						// Shouldn't be possible; you're in this package, no?
						throw new RuntimeException(MessageFormat.format(
							"Cannot instantiate class >{0}<.", new Object[] {
								className }));
					}
				}
			}
		} else {
			throw new RuntimeException(MessageFormat.format(
				">{0}< does not appear to be a valid package (searching real directory {1}).", new Object[] {
					pkg,
					directory.getPath() }));
		}
		return classes;
	}

	private static List<Class<?>> searchJar(String pkg, String jarName, String pattern) {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		pkg = pkg.replaceAll("\\.", "/");
		try {
			JarInputStream jarFile = new JarInputStream(new URL(jarName).openStream());
			JarEntry jarEntry;

			while (true) {
				jarEntry = jarFile.getNextJarEntry();
				if (jarEntry == null) {
					break;
				}
				if ((jarEntry.getName().startsWith(pkg)) 
					&& (jarEntry.getName().endsWith(".class"))
					&& matchesPattern(jarEntry.getName(), pattern)) {
					String className = StringUtils.substringBefore(jarEntry.getName().replaceAll("/", "\\."), ".class");
					classes.add(Class.forName(className));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(MessageFormat.format("Error searching jar file >{0}< for classes in package >{1}<",
				new Object[] { jarName, pkg }));
		}
		return classes;
	}

	private static boolean matchesPattern(String className, String pattern) {
		if(pattern == null) {
			return true;
		}
		return StringUtils.substringBeforeLast(className, ".").matches(pattern);
	}
}
