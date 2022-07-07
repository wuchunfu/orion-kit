package com.orion.ext.vcs.git;

import com.orion.ext.vcs.git.info.BranchInfo;
import com.orion.ext.vcs.git.info.LogInfo;
import com.orion.lang.able.SafeCloseable;
import com.orion.lang.utils.Exceptions;
import com.orion.lang.utils.io.Streams;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * git本地仓库基本操作
 * <p>
 * checkout pull reset log branch clean
 * <p>
 * 其他功能请用命令行
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2021/7/2 10:15
 */
public abstract class Gits implements SafeCloseable {

    private Git git;

    private CredentialsProvider credentialsProvider;

    protected Gits(Git git) {
        this.git = git;
    }

    public static Gits of(Git git) {
        return new Gits(git) {
        };
    }

    public static Gits of(File path) {
        try {
            return new Gits(Git.open(path)) {
            };
        } catch (IOException e) {
            throw Exceptions.vcs(e);
        }
    }

    public static Gits of(Repository repo) {
        return new Gits(Git.wrap(repo)) {
        };
    }

    public static Gits clone(String url, File path) {
        return clone(url, path, null, (char[]) null);
    }

    public static Gits clone(String url, File path, String username, String password) {
        return clone(url, path, username, password.toCharArray());
    }

    /**
     * clone 仓库
     * <p>
     * 可以使用 accessToken
     * <p>
     * github   username: token     password: ''
     * github   username: ''        password: token
     * gitee    username: username  password: token
     * gitlab   username: oauth2    password: token  url: https://oauth2:token@url
     *
     * @param url      remoteUrl
     * @param path     本地路径
     * @param username 用户名
     * @param password 密码
     * @return Gits
     */
    public static Gits clone(String url, File path, String username, char[] password) {
        try {
            CloneCommand clone = Git.cloneRepository().setURI(url).setDirectory(path);
            UsernamePasswordCredentialsProvider credential = null;
            if (username != null && password != null) {
                credential = new UsernamePasswordCredentialsProvider(username, password);
                clone.setCredentialsProvider(credential);
            }
            Gits gits = new Gits(clone.call()) {
            };
            gits.credentialsProvider = credential;
            return gits;
        } catch (Exception e) {
            throw Exceptions.vcs(e);
        }
    }

    public Gits auth(String username, String password) {
        return this.auth(username, password.toCharArray());
    }

    /**
     * 认证用户
     *
     * @param username username
     * @param password password
     * @return this
     */
    public Gits auth(String username, char[] password) {
        this.credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
        return this;
    }

    /**
     * 检出分支
     *
     * @param branchName 分支名称
     * @return this
     */
    public Gits checkout(String branchName) {
        try {
            git.checkout().setName(branchName)
                    .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.NOTRACK)
                    .setCreateBranch(false)
                    .call();
            return this;
        } catch (Exception e) {
            throw Exceptions.vcs(e);
        }
    }

    public Gits pull() {
        return this.pull(null, null);
    }

    public Gits pull(String remote) {
        return this.pull(remote, null);
    }

    /**
     * pull
     *
     * @param remote       远程主机
     * @param remoteBranch 远程分支
     * @return this
     */
    public Gits pull(String remote, String remoteBranch) {
        try {
            PullCommand pull = git.pull();
            if (credentialsProvider != null) {
                pull.setCredentialsProvider(credentialsProvider);
            }
            if (remote != null) {
                pull.setRemote(remote);
            }
            if (remoteBranch != null) {
                pull.setRemoteBranchName(remoteBranch);
            }
            pull.call();
            return this;
        } catch (Exception e) {
            throw Exceptions.vcs(e);
        }
    }

    /**
     * push
     *
     * @param remote 远程主机
     * @return this
     */
    public Gits push(String remote) {
        try {
            PushCommand push = git.push();
            if (credentialsProvider != null) {
                push.setCredentialsProvider(credentialsProvider);
            }
            if (remote != null) {
                push.setRemote(remote);
            }
            push.call();
            return this;
        } catch (Exception e) {
            throw Exceptions.vcs(e);
        }
    }

    public Gits fetch() {
        return this.fetch(null);
    }

    /**
     * fetch
     *
     * @param remote 远程主机
     * @return this
     */
    public Gits fetch(String remote) {
        try {
            FetchCommand fetch = git.fetch();
            if (credentialsProvider != null) {
                fetch.setCredentialsProvider(credentialsProvider);
            }
            if (remote != null) {
                fetch.setRemote(remote);
            }
            fetch.call();
            return this;
        } catch (Exception e) {
            throw Exceptions.vcs(e);
        }
    }

    public Gits rebase() {
        return rebase(RebaseCommand.Operation.CONTINUE);
    }

    /**
     * rebase
     *
     * @param operation operation
     * @return this
     */
    public Gits rebase(RebaseCommand.Operation operation) {
        try {
            git.rebase().setOperation(operation).call();
            return this;
        } catch (Exception e) {
            throw Exceptions.vcs(e);
        }
    }

    public Gits reset(String commitId) {
        return this.reset(commitId, ResetCommand.ResetType.HARD);
    }

    /**
     * 还原版本
     *
     * @param commitId commitId
     * @param type     类型
     * @return this
     */
    public Gits reset(String commitId, ResetCommand.ResetType type) {
        try {
            git.reset().setRef(commitId).setMode(type).call();
            return this;
        } catch (Exception e) {
            throw Exceptions.vcs(e);
        }
    }

    public List<BranchInfo> branchList() {
        return this.branchList(null);
    }

    /**
     * 分支列表
     *
     * @param branch 分支名称
     * @return list
     */
    public List<BranchInfo> branchList(String branch) {
        try {
            List<Ref> refs = git.branchList().setContains(branch)
                    .setListMode(ListBranchCommand.ListMode.REMOTE)
                    .call();
            return refs.stream()
                    .filter(r -> r.getName().startsWith("refs/remotes"))
                    .map(ref -> {
                        BranchInfo info = new BranchInfo();
                        String name = ref.getName().substring(13);
                        info.setId(ref.getObjectId().getName());
                        info.setRemote(name.substring(0, name.indexOf("/")));
                        info.setName(name.substring(name.indexOf("/") + 1));
                        return info;
                    }).collect(Collectors.toList());
        } catch (Exception e) {
            throw Exceptions.vcs(e);
        }
    }

    public List<LogInfo> logList() {
        try {
            return this.logList(git.getRepository().getBranch(), 10);
        } catch (Exception e) {
            throw Exceptions.vcs(e);
        }
    }

    public List<LogInfo> logList(int count) {
        try {
            return this.logList(git.getRepository().getBranch(), count);
        } catch (Exception e) {
            throw Exceptions.vcs(e);
        }
    }

    public List<LogInfo> logList(String branch) {
        return this.logList(branch, 10);
    }

    public List<LogInfo> logList(String branch, int count) {
        try {
            Repository repo = git.getRepository();
            Ref b = git.branchList()
                    .setContains(branch)
                    .setListMode(ListBranchCommand.ListMode.ALL)
                    .call()
                    .get(0);
            if (b == null) {
                return new ArrayList<>();
            }
            ObjectId bid = repo.resolve(b.getName());
            Iterable<RevCommit> commits = git.log().setMaxCount(count).add(bid).call();
            List<LogInfo> logs = new ArrayList<>();
            for (RevCommit commit : commits) {
                LogInfo log = new LogInfo();
                log.setId(commit.getId().name());
                log.setEmail(commit.getCommitterIdent().getName());
                log.setName(commit.getCommitterIdent().getName());
                log.setTime(commit.getCommitTime());
                log.setMessage(commit.getFullMessage());
                logs.add(log);
            }
            return logs;
        } catch (Exception e) {
            throw Exceptions.vcs(e);
        }
    }

    /**
     * 清空工作目录其他文件
     *
     * @return return
     */
    public Gits clean() {
        try {
            git.clean().setForce(true)
                    .setCleanDirectories(true)
                    .call();
            return this;
        } catch (Exception e) {
            throw Exceptions.vcs(e);
        }
    }

    /**
     * 获取目录
     *
     * @return 目录
     */
    public String getDirectory() {
        return git.getRepository().getDirectory().getParent();
    }

    /**
     * 获取远程url
     *
     * @return url
     */
    public String getRemoteUrl() {
        return git.getRepository().getConfig().getString("remote", "origin", "url");
    }

    /**
     * 获取当前分支
     *
     * @return branch
     */
    public String getBranch() {
        try {
            return git.getRepository().getBranch();
        } catch (Exception e) {
            throw Exceptions.vcs(e);
        }
    }

    /**
     * 检查ref
     *
     * @param name name
     * @return ref
     */
    public boolean hasRef(String name) {
        try {
            return git.getRepository().findRef(name) != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取ref
     *
     * @param name name
     * @return ref
     */
    public Ref getRef(String name) {
        try {
            return git.getRepository().findRef(name);
        } catch (Exception e) {
            throw Exceptions.vcs(e);
        }
    }

    public Git getGit() {
        return git;
    }

    public Repository getRepository() {
        return git.getRepository();
    }

    @Override
    public void close() {
        Streams.close(git);
    }

}
