package com.jerry.officer.demo;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/8/10 10:00
 */
public class TagMergePush {
    public static void main(String[] args) throws Exception {
        String repoPath = null;
        if (args.length > 0) {
            repoPath = args[0];
        }
        if (repoPath == null || repoPath.trim().length()<=0){
            repoPath = "D:\\work\\workspace\\projects\\zhgd-person-data-report\\.git";
        }
        Repository repo = new FileRepositoryBuilder().setGitDir(new File(repoPath)).build();
        Git git = new Git(repo);

        //Ref ref = repo.getRef("refs/heads/main");
        String branch = repo.getBranch();
        System.out.println("current branch: " + branch);

        System.out.println("start to checkout testbranch1");
        Ref checkoutRef = git.checkout().setName("testbranch1").call();
        System.out.println("checkout ref : " + checkoutRef);
        String testbranch1 = repo.getBranch();
        System.out.println("current branch: " + testbranch1);

        //MergeResult call = git.merge().include(ref).call();
        //System.out.println(call);


    }
}
