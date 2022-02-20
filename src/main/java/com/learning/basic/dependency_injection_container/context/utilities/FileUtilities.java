package com.learning.basic.dependency_injection_container.context.utilities;

import com.learning.basic.dependency_injection_container.exception.PackageNotExistsException;
import lombok.experimental.UtilityClass;

import java.io.File;

@UtilityClass
public class FileUtilities {

    public File[] findFiles(String packageStructure) {
        return getFileOrThrowException(packageStructure)
                .listFiles(f -> f.getName().endsWith(".class"));
    }

    public File getFileOrThrowException(String fileName) {
        var file = new File(fileName);
        if (!file.exists()) {
            throw new PackageNotExistsException(String.format("File %s doesn't exists", fileName));
        }
        return file;
    }

    public String mapFileNameToClassName(String fileName, String fileSuffix, String classPackage) {
        return classPackage.isBlank() ? fileName.replace(fileSuffix, "") :
                classPackage + "." + fileName.replace(fileSuffix, "");
    }

}
