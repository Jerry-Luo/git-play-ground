package com.jerry.officer.demo;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Iterator;
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

        System.out.println("rep path : " + repoPath);
        Repository repo = new FileRepositoryBuilder().setGitDir(new File(repoPath)).build();
        Git git = new Git(repo);

        String branch = repo.getBranch();
        System.out.println("current branch: " + branch);

        System.out.println("start to checkout master ... ");
        Ref checkoutRef = git.checkout().setName("master").call();
        System.out.println("checkout ref : " + checkoutRef);

        String master = repo.getBranch();
        System.out.println("current branch: " + master);

        System.out.println("start to merge release ..." );
        Ref ref = repo.getRef("refs/heads/release");

        MergeResult call = git.merge().include(ref).call();
        System.out.println("merge result : " + call);

        Iterable<RevCommit> logs = git.log().call();
        RevCommit commit = null;
        for (RevCommit c : logs){
            commit = c;
            break;
        }
        String fullMessage = commit.getFullMessage();
        System.out.println("last commit message : " + fullMessage);

        System.out.println("start to create tag ... ");
        Ref tagRef = git.tag()
                .setName("v" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd_HH.mm.ss")))
                .setMessage(fullMessage).call();
        System.out.println("create tag result : " + tagRef);
        CredentialsProvider cp = new UsernamePasswordCredentialsProvider(System.getenv("GIT_USERNAME"), System.getenv("GIT_PASSWORD"));
        PushCommand push = git.push().setRemote("origin").setCredentialsProvider(cp).setPushTags().add("master");
        Iterable<PushResult> pushResults = push.call();
        pushResults.forEach(r-> System.out.println("push result " + r.getMessages()));

        System.out.println("start to checkout release...");
        Ref releaseRef = git.checkout().setName("release").call();
        System.out.println("checkou result : " + releaseRef);
        System.out.println("current branch : " + repo.getBranch());
    }
}
