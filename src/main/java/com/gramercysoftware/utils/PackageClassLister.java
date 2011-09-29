package com.gramercysoftware.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang.StringUtils;

/**
 * PackageClassLister
 * 
 * Utility to list classes in packages, which can handle both directory and jar file paths.
 * 
 * 
 * @author dharcombe
 */
public class PackageClassLister {
	private static final String JAR_PROTOCOL = "jar";
	private static final String VFSZIP = "vfszip";
//	private static Logger logger = Logger.getLogger(PackageClassLister.class);

	/**
	 * List all classes in a package
	 * 
	 * @param pkg
	 * @return
	 */
	public List<Class<?>> getClasses(String pkg) {
		return getClasses(pkg, null, null);
	}

	/**
	 * List all classes in a package who extend a given interface
	 * 
	 * @param pkg
	 * @return
	 */
	public List<Class<?>> getClasses(String pkg, Class<?> interfaceClass) {
		return getClasses(pkg, null, interfaceClass);
	}

	/**
	 * List all classes in a package
	 * 
	 * @param pkg
	 * @return
	 */
	public List<Class<?>> getFirstNamedClasses(String pkg) {
		return getFirstNamedClasses(pkg, null, null);
	}
	
	/**
	 * List classes whose names match the regex pattern in the given package
	 * 
	 * @param pkg
	 * @param pattern
	 * @return
	 */
	public List<Class<?>> getFirstNamedClasses(String pkg, String pattern) {
		return getFirstNamedClasses(pkg, null, null);
	}
		/**
		 * List classes whose names match the regex pattern in the given package
		 * 
		 * @param pkg
		 * @param pattern
		 * @return
		 */
	public List<Class<?>> getFirstNamedClasses(String pkg, String pattern, Class<?> interfaceClass) {
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
			throw new RuntimeException(MessageFormat.format(">{0}< does not appear to be a valid package (searching real directory {1}).", new Object[] { pkg, path }), x);
		}

		if (resource.getProtocol().equals(JAR_PROTOCOL)) {
			classes = searchJar(pkg, StringUtils.substringBefore(resource.getFile(), "!"), pattern, interfaceClass);
		} else {
			classes = searchDirectory(pkg, directory, pattern, interfaceClass);
		}
		return classes;
	}

	/**
	 * List classes whose names match the regex pattern in the given package
	 * 
	 * @param pkg
	 * @param pattern
	 * @return
	 */
	public List<Class<?>> getClasses(String pkg, String pattern) {
		return getClasses(pkg, pattern, null);
	}

	public List<Class<?>> getClasses(String pkg, String pattern, Class interfaceClass) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		
		// Get a File object for the package
		File directory = null;
		String path = null;
		URL resource = null;
		Enumeration<URL> resources; 
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			path = pkg.replace('.', '/');
			resources = classLoader.getResources(path);
			while(resources.hasMoreElements()) {
				resource = resources.nextElement();
				directory = new File(URLDecoder.decode(resource.getFile(), "UTF-8"));

				String resourceProtocol = resource.getProtocol();
				
				if (JAR_PROTOCOL.equals(resourceProtocol)) {
					classes.addAll(searchJar(pkg, StringUtils.substringBefore(resource.getFile(), "!"), pattern, interfaceClass));
				} else if (VFSZIP.equals(resourceProtocol)) {
					classes.addAll(searchVFSZIP(pkg, resource.getFile(), pattern, interfaceClass));
				} else {
					classes.addAll(searchDirectory(pkg, directory, pattern, interfaceClass));
				}
			}
		} catch (Exception x) {
			throw new RuntimeException(MessageFormat.format(
					">{0}< does not appear to be a valid package (searching real directory {1}).", new Object[] { pkg, path }), x);
		}
		
		return classes;
	}
	
	 public void extractJar(String filename, String destinationname) throws Exception  {
            byte[] buf = new byte[1024];
            ZipInputStream zipinputstream = null;
            ZipEntry zipentry;
            zipinputstream = new ZipInputStream( new FileInputStream(filename));

            zipentry = zipinputstream.getNextEntry();
            while (zipentry != null) { 
                //for each entry to be extracted
                String entryName = zipentry.getName();
                
                FileOutputStream fileoutputstream;
                File newFile = new File(entryName);
                String directory = newFile.getParent();
                
                if (zipentry.isDirectory()) {
                	String entryDestination = destinationname+ File.separator + entryName;
                	File file = new File(entryDestination);
                	file.mkdirs();
                	zipentry = zipinputstream.getNextEntry();	
                	continue;
                }
                if(directory == null)
                {
                    if(newFile.isDirectory()) {
                        break;
                    }
                }
                
                String entryDestination = destinationname+ File.separator + entryName;
                File file = new File(entryDestination);
                if (!file.getParentFile().exists()) {
                	file.getParentFile().mkdirs();
                }
				fileoutputstream = new FileOutputStream(entryDestination);             

                int n;
                while ((n = zipinputstream.read(buf, 0, 1024)) > -1) {
                    fileoutputstream.write(buf, 0, n);
                }

                fileoutputstream.close(); 
                zipinputstream.closeEntry();
                zipentry = zipinputstream.getNextEntry();

            }

            zipinputstream.close();
	    }
	/**
	 * The implementation of this VFSZIP assumes that the file we are looking 
	 * for classes inside of a JAR inside of a folder.
	 * @param pkg
	 * @param substringBefore
	 * @param pattern
	 * @return
	 */
	private List<? extends Class<?>> searchVFSZIP(String pkg, String substringBefore, String pattern, Class<?> interfaceClass) {
		
		File createTempFile = null;
		int indexOf = substringBefore.indexOf(".war");
		String temp = substringBefore.substring(0, indexOf + 4);
		String leftOver = substringBefore.substring(indexOf + 4);
		
		File warFileTestFile = new File(temp);
		boolean exists = warFileTestFile.exists();
		
		// If there is a .war and such path is a file, (unlike ROOT.war which is often just a directory name), then we need to extract it and deal with the extracted path.
		if (exists && warFileTestFile.isFile()) {
			try {
				createTempFile = File.createTempFile("class_loading_zip_file_extracted", "_folder");
				createTempFile.delete();
				createTempFile.mkdir();
				
				extractJar(warFileTestFile.getAbsolutePath(), createTempFile.getAbsolutePath());
				substringBefore = new File(createTempFile, leftOver ).getAbsolutePath();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		File substringBeforeFile = new File(substringBefore);
		
		// classes are extracted in a directory and its not in a JAR...
		if (!substringBefore.contains(".jar") && substringBeforeFile.isDirectory()) {
			try {
				return searchDirectory(pkg, substringBeforeFile, pattern, interfaceClass);
			} finally {
				deleteFile(createTempFile);
			}
		}
		
//		substringBefore = substringBefore.substring((VFSZIP + ":").length());
		String jarlocation = substringBefore.substring(0, substringBefore.indexOf(".jar/") + ".jar/".length());
		
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		pkg = pkg.replaceAll("\\.", "/");
		try {
			JarInputStream jarFile = new JarInputStream(new FileInputStream(new File(jarlocation)));
			JarEntry jarEntry;

			while (true) {
				jarEntry = jarFile.getNextJarEntry();
				if (jarEntry == null) {
					break;
				}
				if ((jarEntry.getName().startsWith(pkg + "/")) 
						&& (jarEntry.getName().endsWith(".class"))
						&& matchesPattern(StringUtils.substringAfterLast(jarEntry.getName(), "/"), pattern)) {
					String className = StringUtils.substringBefore(jarEntry.getName().replaceAll("/", "\\."), ".class");
					if(interfaceClass == null || interfaceClass.isAssignableFrom(Class.forName(className))) {
						classes.add(Class.forName(className));
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(MessageFormat.format("Error searching jar file >{0}< for classes in package >{1}<",
					new Object[] { jarlocation, pkg }));
		} finally {
			deleteFile(createTempFile);
		}
		
		return classes;
	}

	private void deleteFile(File createTempFile) {
		if (createTempFile == null) {
			return;
		}
		
		if (createTempFile.isFile()) {
			createTempFile.delete();
		} else if (createTempFile.isDirectory()) {
			File[] listFiles = createTempFile.listFiles();
			for (File file : listFiles) {
				deleteFile(file);
			}
			createTempFile.delete();
		}
		
	}

	private List<Class<?>> searchDirectory(String pkg, File directory, String pattern, Class<?> interfaceClass) {
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
						if(interfaceClass == null || interfaceClass.isAssignableFrom(Class.forName(className))) {
							classes.add(Class.forName(className));
						}
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(MessageFormat.format(">{0}< cannot be instantiated.", new Object[] { className, }));
					}
				}
			}
		} else {
			throw new RuntimeException(MessageFormat.format(">{0}< does not appear to be a valid package (searching real directory {1}).", new Object[] { pkg, directory.getPath() }));
		}
		return classes;
	}

	private List<Class<?>> searchJar(String pkg, String jarName, String pattern, Class<?> interfaceClass) {
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
				if ((jarEntry.getName().startsWith(pkg + "/")) 
						&& (jarEntry.getName().endsWith(".class"))
						&& matchesPattern(StringUtils.substringAfterLast(jarEntry.getName(), "/"), pattern)) {
					String className = StringUtils.substringBefore(jarEntry.getName().replaceAll("/", "\\."), ".class");
					if(interfaceClass == null || interfaceClass.isAssignableFrom(Class.forName(className))) {
						classes.add(Class.forName(className));
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(MessageFormat.format("Error searching jar file >{0}< for classes in package >{1}<",
					new Object[] { jarName, pkg }));
		}
		return classes;
	}

	private boolean matchesPattern(String className, String pattern) {
		if(pattern == null) {
			return true;
		}
		
		String klass = StringUtils.substringBeforeLast(className, ".");
		return klass.matches(pattern);
	}
}
