package org.sakaiproject.scorm.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.sakaiproject.scorm.api.ScormConstants;
import org.sakaiproject.scorm.model.api.TinCanAPIManifest;
import org.sakaiproject.scorm.service.api.TinCanAPIContentService;

public abstract class TinCanAPIContentServiceImpl extends BaseContentServiceImpl implements TinCanAPIContentService, ScormConstants {

    public int processUpload(File file) {
        if (file == null) {
            return VALIDATION_NOFILE;
        }

        File[] files = getFiles(file);

        return VALIDATION_SUCCESS;
    }

    @Override
    public TinCanAPIManifest createManifest(File file) {
        

        
        TinCanAPIManifest tinCanAPIManifest = new TinCanAPIManifest();

        return tinCanAPIManifest;
    }

    private void unzip(File file) {
        try {
            // Open the zip file
            ZipFile zipFile = new ZipFile(file);
            Enumeration<?> enu = zipFile.entries();
            while (enu.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enu.nextElement();

                String name = zipEntry.getName();
                long size = zipEntry.getSize();
                long compressedSize = zipEntry.getCompressedSize();
                System.out.printf("name: %-20s | size: %6d | compressed size: %6d\n", name, size, compressedSize);

                // Do we need to create a directory ?
                File newFile = new File(name);
                if (name.endsWith("/")) {
                    newFile.mkdirs();
                    continue;
                }

                File parent = newFile.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }

                // Extract the file
                InputStream is = zipFile.getInputStream(zipEntry);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = is.read(bytes)) >= 0) {
                    fos.write(bytes, 0, length);
                }
                is.close();
                fos.close();
            }
            zipFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
