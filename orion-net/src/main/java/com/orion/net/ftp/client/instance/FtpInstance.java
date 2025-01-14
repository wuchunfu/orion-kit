package com.orion.net.ftp.client.instance;

import com.orion.lang.constant.Const;
import com.orion.lang.define.StreamEntry;
import com.orion.lang.utils.Exceptions;
import com.orion.lang.utils.Strings;
import com.orion.lang.utils.io.Files1;
import com.orion.lang.utils.io.Streams;
import com.orion.lang.utils.time.Dates;
import com.orion.net.ftp.client.FtpFile;
import com.orion.net.ftp.client.FtpFileFilter;
import com.orion.net.ftp.client.bigfile.FtpDownloader;
import com.orion.net.ftp.client.bigfile.FtpUploader;
import com.orion.net.ftp.client.config.FtpConfig;
import com.orion.net.ftp.client.pool.FtpClientPool;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * FTP 操作实例
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2020/3/17 16:10
 */
@SuppressWarnings("ALL")
public class FtpInstance extends BaseFtpInstance {

    public FtpInstance(FtpClientPool pool) {
        super(pool);
    }

    public FtpInstance(FTPClient client, FtpConfig config) {
        super(client, config);
    }

    @Override
    public void change() {
        try {
            client.changeWorkingDirectory(this.serverCharset(config.getRemoteRootDir()));
        } catch (Exception e) {
            throw Exceptions.ftp(e);
        }
    }

    @Override
    public void change(String dir) {
        try {
            if (!client.changeWorkingDirectory(this.serverCharset(config.getRemoteRootDir() + dir))) {
                this.mkdirs(dir);
                client.changeWorkingDirectory(this.serverCharset(config.getRemoteRootDir() + dir));
            }
        } catch (Exception e) {
            throw Exceptions.ftp(e);
        }
    }

    @Override
    public String getWorkDirectory() {
        try {
            return client.printWorkingDirectory();
        } catch (Exception e) {
            throw Exceptions.ftp(e);
        }
    }

    @Override
    public boolean isExist(String file) {
        String parentPath = Files1.getParentPath(file);
        List<FtpFile> list = this.listFiles(parentPath, false, true);
        for (FtpFile s : list) {
            if (Files1.getFileName(s.getPath()).equals(Files1.getFileName(file.trim()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FtpFile getFile(String file) {
        String parentPath = Files1.getParentPath(file);
        List<FtpFile> files = this.listFiles(parentPath, false, true);
        for (FtpFile ftpFile : files) {
            if (ftpFile.getName().equals(Files1.getFileName(file))) {
                return ftpFile;
            }
        }
        return null;
    }

    @Override
    public long getSize(String file) {
        FtpFile ftpFile = this.getFile(file);
        if (ftpFile == null) {
            return -1;
        }
        return ftpFile.getSize();
    }

    @Override
    public long getModifyTime(String file) {
        try {
            // .e.g 213 20200202020202.000
            String modificationTime = client.getModificationTime(file);
            if (modificationTime == null) {
                return -1;
            }
            String time = modificationTime.split(Const.SPACE)[1];
            return Dates.parse(time, Dates.YMD_HMS2).getTime();
        } catch (Exception e) {
            throw Exceptions.ftp(e);
        }
    }

    @Override
    public void setModifyTime(String file, Date time) {
        try {
            client.setModificationTime(file, Dates.format(time, Dates.YMD_HMS2));
        } catch (Exception e) {
            throw Exceptions.ftp(e);
        }
    }

    @Override
    public void truncate(String file) throws IOException {
        OutputStream out = null;
        try {
            out = client.storeFileStream(this.serverCharset(config.getRemoteRootDir() + file));
            out.flush();
        } catch (Exception e) {
            throw Exceptions.ftp(e);
        } finally {
            Streams.close(out);
            if (out != null) {
                client.completePendingCommand();
            }
        }
    }

    @Override
    public void rm(String file) {
        FtpFile ftpFile = this.getFile(file);
        if (ftpFile == null) {
            return;
        }
        if (ftpFile.isDirectory()) {
            this.removeDir(file);
        } else {
            this.removeFile(file);
        }
    }

    @Override
    public void removeFile(String file) {
        try {
            client.deleteFile(this.serverCharset(file));
        } catch (Exception e) {
            throw Exceptions.ftp(e);
        }
    }

    @Override
    public void removeDir(String dir) {
        try {
            String d = this.serverCharset(config.getRemoteRootDir() + dir);
            List<FtpFile> list = this.listFiles(dir, false, true);
            for (FtpFile s : list) {
                String path = s.getPath();
                if (s.isDirectory()) {
                    this.removeDir(path);
                    client.removeDirectory(this.serverCharset(Files1.getPath(config.getRemoteRootDir(), path)));
                } else {
                    client.deleteFile(this.serverCharset(Files1.getPath(config.getRemoteRootDir(), path)));
                }
            }
            client.changeWorkingDirectory(this.serverCharset(config.getRemoteRootDir() + Files1.getParentPath(dir)));
            client.removeDirectory(this.serverCharset(Files1.getFileName(dir)));
        } catch (Exception e) {
            throw Exceptions.ftp(e);
        }
    }

    @Override
    public void mkdirs(String dir) {
        try {
            String[] dirs = Files1.getPath(dir).split(SEPARATOR);
            String base = config.getRemoteRootDir();
            for (String d : dirs) {
                if (d == null || Strings.EMPTY.equals(d)) {
                    continue;
                }
                base = this.serverCharset(base + SEPARATOR + d);
                if (!client.changeWorkingDirectory(base)) {
                    client.makeDirectory(base);
                    client.changeWorkingDirectory(base);
                }
            }
        } catch (Exception e) {
            throw Exceptions.ftp(e);
        }
    }

    @Override
    public void touch(String file) {
        String fileName = Files1.getFileName(file);
        String filePath = this.serverCharset(config.getRemoteRootDir() + file);
        String parentPath = Files1.getParentPath(Files1.getPath(file));
        this.mkdirs(parentPath);
        for (FtpFile s : this.listFiles(parentPath, false)) {
            if (Files1.getFileName(s.getPath()).equals(fileName)) {
                return;
            }
        }
        this.change(parentPath);
        try {
            client.storeFile(this.serverCharset(filePath), new ByteArrayInputStream(new byte[]{}));
        } catch (IOException e) {
            throw Exceptions.ftp(e);
        }
    }

    @Override
    public void mv(String source, String target) {
        source = Files1.getPath(source);
        target = Files1.getPath(target);
        try {
            if (target.charAt(0) == '/') {
                // 绝对路径
                target = config.getRemoteRootDir() + Files1.normalize(target);
            } else {
                // 相对路径
                target = Files1.normalize(Files1.getPath(source + "/../" + target));
            }
            this.change(Files1.getParentPath(source));
            this.mkdirs(Files1.getParentPath(target));
            client.rename(source, target);
        } catch (Exception e) {
            throw Exceptions.ftp(e);
        }
    }

    // -------------------- stream --------------------

    @Override
    public InputStream openInputStream(String file) throws IOException {
        try {
            return client.retrieveFileStream(this.serverCharset(config.getRemoteRootDir() + file));
        } catch (Exception e) {
            throw Exceptions.io("cannot get file input stream " + file, e);
        }
    }

    @Override
    public InputStream openInputStream(String file, long skip) throws IOException {
        try {
            client.setRestartOffset(skip);
            return this.openInputStream(file);
        } finally {
            client.setRestartOffset(0);
        }
    }

    @Override
    public OutputStream openOutputStream(String file) throws IOException {
        return this.openOutputStream(file, false);
    }

    @Override
    public OutputStream openOutputStream(String file, boolean append) throws IOException {
        this.mkdirs(Files1.getParentPath(file));
        try {
            if (append) {
                return client.appendFileStream(this.serverCharset(config.getRemoteRootDir() + file));
            } else {
                return client.storeFileStream(this.serverCharset(config.getRemoteRootDir() + file));
            }
        } catch (Exception e) {
            throw Exceptions.io("cannot get file out stream " + file, e);
        }
    }

    @Override
    public void readFromFile(String file, OutputStream out) throws IOException {
        this.mkdirs(Files1.getParentPath(file));
        try {
            client.retrieveFile(this.serverCharset(config.getRemoteRootDir() + file), out);
        } catch (Exception e) {
            throw Exceptions.io("cannot write to stream " + file, e);
        }
    }

    @Override
    public void appendToFile(String file, InputStream in) throws IOException {
        this.mkdirs(Files1.getParentPath(file));
        try {
            client.appendFile(this.serverCharset(config.getRemoteRootDir() + file), in);
        } catch (Exception e) {
            throw Exceptions.io("cannot write to stream " + file, e);
        }
    }

    @Override
    public void writeToFile(String file, InputStream in) throws IOException {
        this.mkdirs(Files1.getParentPath(file));
        try {
            client.storeFile(this.serverCharset(config.getRemoteRootDir() + file), in);
        } catch (Exception e) {
            throw Exceptions.io("cannot write to stream " + file, e);
        }
    }

    // -------------------- read --------------------

    @Override
    public int read(String file, long skip, byte[] bs, int off, int len) throws IOException {
        InputStream in = null;
        try {
            client.setRestartOffset(skip);
            in = this.openInputStream(file);
            return in.read(bs, off, len);
        } finally {
            Streams.close(in);
            client.setRestartOffset(0);
            if (in != null) {
                client.completePendingCommand();
            }
        }
    }

    @Override
    public String readLine(String file, long skip) throws IOException {
        BufferedReader in = null;
        try {
            client.setRestartOffset(skip);
            in = new BufferedReader(new InputStreamReader(this.openInputStream(file), config.getRemoteFileNameCharset()));
            return in.readLine();
        } finally {
            Streams.close(in);
            client.setRestartOffset(0);
            if (in != null) {
                client.completePendingCommand();
            }
        }
    }

    @Override
    public List<String> readLines(String file, long skip, int lines) throws IOException {
        BufferedReader in = null;
        try {
            client.setRestartOffset(skip);
            in = new BufferedReader(new InputStreamReader(this.openInputStream(file), config.getRemoteFileNameCharset()));
            List<String> list = new ArrayList<>();
            if (lines != -1) {
                String line;
                for (int i = 0; i < lines && (line = in.readLine()) != null; i++) {
                    list.add(line);
                }
            } else {
                String line;
                while ((line = in.readLine()) != null) {
                    list.add(line);
                }
            }
            return list;
        } finally {
            Streams.close(in);
            client.setRestartOffset(0);
            if (in != null) {
                client.completePendingCommand();
            }
        }
    }

    // -------------------- transfer --------------------

    @Override
    protected long doTransfer(String path, OutputStream out, long skip, int size, boolean close) throws IOException {
        long r = 0;
        byte[] bs = new byte[bufferSize];
        InputStream in = null;
        try {
            in = this.openInputStream(path, skip);
            if (size != -1) {
                boolean mod = size % bufferSize == 0;
                long readTimes = size / bufferSize;
                if (mod || readTimes == 0) {
                    readTimes++;
                }
                for (int i = 0; i < readTimes; i++) {
                    if (readTimes == 1) {
                        int read = in.read(bs, 0, size);
                        out.write(bs, 0, read);
                        r += read;
                    } else {
                        int read = in.read(bs, 0, bufferSize);
                        if (read != -1) {
                            out.write(bs, 0, read);
                            r += read;
                        } else {
                            break;
                        }
                    }
                }
            } else {
                int read;
                while ((read = in.read(bs, 0, bufferSize)) != -1) {
                    out.write(bs, 0, read);
                    r += read;
                }
            }
        } finally {
            Streams.close(in);
            client.setRestartOffset(0);
            if (in != null) {
                client.completePendingCommand();
            }
            if (close) {
                Streams.close(out);
            }
        }
        return r;
    }

    // -------------------- write --------------------

    @Override
    protected void doWrite(String path, InputStream in, StreamEntry entry, List<String> lines) throws IOException {
        this.write(path, in, entry, lines, false);
    }

    // -------------------- append --------------------

    @Override
    protected void doAppend(String path, InputStream in, StreamEntry entry, List<String> lines) throws IOException {
        this.write(path, in, entry, lines, true);
    }

    private void write(String path, InputStream in, StreamEntry entry, List<String> lines, boolean append) throws IOException {
        OutputStream out = null;
        try {
            out = this.openOutputStream(path, append);
            if (in != null) {
                byte[] bs = new byte[bufferSize];
                int read;
                while ((read = in.read(bs)) != -1) {
                    out.write(bs, 0, read);
                }
            } else if (entry != null) {
                out.write(entry.getBytes(), entry.getOff(), entry.getLen());
            } else if (lines != null) {
                for (String line : lines) {
                    out.write(Strings.bytes(line + Const.LF));
                }
            }
            out.flush();
        } finally {
            Streams.close(out);
            if (out != null) {
                client.completePendingCommand();
            }
        }
    }

    // -------------------- upload --------------------

    @Override
    public void uploadFile(String remoteFile, InputStream in, boolean close) throws IOException {
        BufferedInputStream buffer = null;
        try {
            String parentPath = Files1.getParentPath(remoteFile);
            this.mkdirs(parentPath);
            client.storeFile(this.serverCharset(config.getRemoteRootDir() + remoteFile), buffer = new BufferedInputStream(in));
        } finally {
            if (close) {
                Streams.close(in);
                Streams.close(buffer);
            }
        }
    }

    @Override
    public void uploadDir(String remoteDir, String localDir, boolean child) throws IOException {
        localDir = Files1.getPath(localDir);
        List<File> dirs = Files1.listDirs(localDir, child);
        List<File> files = Files1.listFiles(localDir, child);
        for (File dir : dirs) {
            this.mkdirs(Files1.getPath(remoteDir, dir.getAbsolutePath().substring(localDir.length())));
        }
        for (File file : files) {
            String path = Files1.getPath(remoteDir, file.getAbsolutePath().substring(localDir.length()));
            this.change(Files1.getParentPath(path));
            this.uploadFile(path, file);
        }
    }

    // -------------------- download --------------------

    @Override
    public void downloadFile(String remoteFile, OutputStream out, boolean close) throws IOException {
        InputStream in = null;
        try {
            client.setRestartOffset(0);
            in = this.openInputStream(remoteFile);
            if (in == null) {
                throw Exceptions.ftp("not found file " + remoteFile);
            }
            Streams.transfer(in, out);
        } finally {
            if (close) {
                Streams.close(out);
            }
            Streams.close(in);
            client.setRestartOffset(0);
            if (in != null) {
                client.completePendingCommand();
            }
        }
    }

    @Override
    public void downloadDir(String remoteDir, String localDir, boolean child) throws IOException {
        remoteDir = Files1.getPath(remoteDir);
        if (!child) {
            List<FtpFile> list = this.listFiles(remoteDir, false);
            for (FtpFile s : list) {
                this.downloadFile(s.getPath(), Files1.getPath(localDir, Files1.getFileName(s.getPath())));
            }
        } else {
            List<FtpFile> list = this.listDirs(remoteDir, true);
            for (FtpFile s : list) {
                Files1.mkdirs(Files1.getPath(localDir, s.getPath().substring(remoteDir.length())));
            }
            list = this.listFiles(remoteDir, true);
            for (FtpFile s : list) {
                this.downloadFile(s.getPath(), Files1.getPath(localDir, s.getPath().substring(remoteDir.length())));
            }
        }
    }

    // -------------------- big file --------------------

    @Override
    public FtpUploader upload(String remote, String local) {
        return new FtpUploader(this, remote, local);
    }

    @Override
    public FtpUploader upload(String remote, File local) {
        return new FtpUploader(this, remote, local);
    }

    @Override
    public FtpDownloader download(String remote, String local) {
        return new FtpDownloader(this, remote, local);
    }

    @Override
    public FtpDownloader download(String remote, File local) {
        return new FtpDownloader(this, remote, local);
    }

    // -------------------- list --------------------

    @Override
    protected List<FtpFile> listFilesSearch(String path, FtpFileFilter filter, boolean child, boolean dir) {
        String base = config.getRemoteRootDir();
        List<FtpFile> list = new ArrayList<>();
        try {
            FTPFile[] files = client.listFiles(this.serverCharset(Files1.getPath(base, path)));
            for (FTPFile file : files) {
                String fn = file.getName();
                String t = Files1.getPath(path, fn);
                boolean isDir = file.isDirectory();
                if (!isDir || dir) {
                    FtpFile f = new FtpFile(t, file);
                    if (filter.accept(f)) {
                        list.add(f);
                    }
                }
                if (isDir && child) {
                    list.addAll(this.listFilesSearch(t + SEPARATOR, filter, true, dir));
                }
            }
        } catch (IOException e) {
            throw Exceptions.ftp(e);
        }
        return list;
    }

}
