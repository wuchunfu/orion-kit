package com.orion.utils.io.compress.zip;

import com.orion.constant.Const;
import com.orion.utils.io.Files1;
import com.orion.utils.io.Streams;
import com.orion.utils.io.compress.BaseFileDecompressor;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * zip解压器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2021/9/27 21:08
 */
public class ZipDecompressor extends BaseFileDecompressor {

    public ZipDecompressor() {
        this(Const.SUFFIX_ZIP);
    }

    public ZipDecompressor(String suffix) {
        super(suffix);
    }

    @Override
    public void doDecompress() throws Exception {
        try (ZipFile zipFile = new ZipFile(decompressFile)) {
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                File file = new File(decompressTargetPath, entry.getName());
                if (entry.isDirectory()) {
                    Files1.mkdirs(file);
                } else {
                    try (InputStream in = zipFile.getInputStream(entry);
                         OutputStream out = Files1.openOutputStreamFast(file)) {
                        Streams.transfer(in, out);
                    }
                }
            }
        }
    }

}
