package com.github.phpple.errorcode.lib.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * class scanner
 *
 * @author zhangyunan
 */
public class ClassScanner {
    private static Logger log = LoggerFactory.getLogger(ClassScanner.class);

    private final String basePackage;
    private final boolean recursive;
    private final Predicate<String> packagePredicate;
    private final Predicate<Class> classPredicate;


    /**
     * Instantiates a new Class scanner.
     *
     * @param basePackage      the base package
     * @param recursive        whether recursive search
     * @param packagePredicate the package predicate
     * @param classPredicate   the class predicate
     */
    public ClassScanner(String basePackage, boolean recursive, Predicate<String> packagePredicate,
                        Predicate<Class> classPredicate) {
        this.basePackage = basePackage;
        this.recursive = recursive;
        this.packagePredicate = packagePredicate;
        this.classPredicate = classPredicate;
    }

    /**
     * Do scan all classes set.
     *
     * @return the set
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public Set<Class<?>> doScanAllClasses() throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();

        String packageName = basePackage;

        // 如果最后一个字符是“.”，则去掉
        if (packageName.endsWith(".")) {
            packageName = packageName.substring(0, packageName.lastIndexOf('.'));
        }

        // 将包名中的“.”换成系统文件夹的“/”
        String basePackageFilePath = packageName.replace('.', '/');

        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(basePackageFilePath);
        log.info("found resources: {}", resources);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String protocol = resource.getProtocol();
            log.info("resource protocol: {} @ {}", protocol, resource.toString());
            if ("file".equals(protocol)) {
                String filePath = URLDecoder.decode(resource.getFile(), "UTF-8");
                // scan package and class in the directory
                doScanPackageClassesByFile(classes, packageName, filePath, recursive);
            } else if ("jar".equals(protocol)) {
                doScanPackageClassesByJar(packageName, resource, classes);
            }
        }
        log.info("found classes:{}", classes.size());
        return classes;
    }

    private void doScanPackageClassesByJar(String basePackage, URL url, Set<Class<?>> classes)
            throws IOException, ClassNotFoundException {
        // package name
        String packageName = basePackage;
        // get the path of the file
        String basePackageFilePath = packageName.replace('.', '/');
        // tranfer to jar
        JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
        // scan all elements in the jar file
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            // If the paths are inconsistent, or it is a directory, continue
            if (!name.startsWith(basePackageFilePath) || entry.isDirectory()) {
                continue;
            }
            // Determines whether to search for subpackages recursively
            if (!recursive && name.lastIndexOf('/') != basePackageFilePath.length()) {
                continue;
            }

            if (packagePredicate != null) {
                String jarPackageName = name.substring(0, name.lastIndexOf('/')).replace("/", ".");
                if (!packagePredicate.test(jarPackageName)) {
                    continue;
                }
            }

            // Check whether the filtering conditions are met
            String className = name.replace('/', '.');
            className = className.substring(0, className.length() - 6);
            // Load the class with the current thread's class loader
            Class<?> loadClass = Thread.currentThread().getContextClassLoader().loadClass(className);
            if (classPredicate == null || classPredicate.test(loadClass)) {
                classes.add(loadClass);
            }
        }
    }

    /**
     * Scan for packages and classes in folders
     */
    private void doScanPackageClassesByFile(Set<Class<?>> classes, String packageName, String packagePath,
                                            boolean recursive) throws ClassNotFoundException {
        // Convert to file
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        final boolean fileRecursive = recursive;
        // List the files and filter them
        // User-defined file filtering rules
        File[] dirFiles = dir.listFiles((FileFilter) file -> {
            String filename = file.getName();

            if (file.isDirectory()) {
                if (!fileRecursive) {
                    return false;
                }

                if (packagePredicate != null) {
                    return packagePredicate.test(packageName + "." + filename);
                }
                return true;
            }

            return filename.endsWith(".class");
        });

        if (null == dirFiles) {
            return;
        }

        for (File file : dirFiles) {
            if (file.isDirectory()) {
                // Recursion if it is a directory
                doScanPackageClassesByFile(classes, packageName + "." + file.getName(), file.getAbsolutePath(), recursive);
            } else {
                // Load with the current class loader, remove the ".class "6 characters from the file name
                String className = file.getName().substring(0, file.getName().length() - 6);
                Class<?> loadClass = Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className);
                if (classPredicate == null || classPredicate.test(loadClass)) {
                    classes.add(loadClass);
                }
            }
        }
    }
}
