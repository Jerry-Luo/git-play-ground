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
        // Open an existing repository
        Repository repo = new FileRepositoryBuilder().setGitDir(new File(repoPath)).build();

        //// Get a reference
        //Ref master = repo.getRef("master");
        //// Get the object the reference points to
        //ObjectId masterTip = master.getObjectId();
        //System.out.println(masterTip);
        //
        //// Rev-parse
        //ObjectId obj = repo.resolve("HEAD^{tree}");
        //System.out.println(obj);
        //
        //// Load raw object contents
        //ObjectLoader loader = repo.open(masterTip);
        //loader.copyTo(System.out);

        //String username = System.getenv("GIT_USERNAME");
        //String password = System.getenv("GIT_PASSWORD");
        //
        Git git = new Git(repo);
        //CredentialsProvider cp = new UsernamePasswordCredentialsProvider(username, password);
        //Collection<Ref> remoteRefs = git.lsRemote()
        //        .setCredentialsProvider(cp)
        //        .setRemote("origin")
        //        .setTags(true)
        //        .setHeads(true)
        //        .call();
        //for (Ref ref : remoteRefs) {
        //    System.out.println(ref.getName() + " -> " + ref.getObjectId().name());
        //}

        Ref main = git.checkout().setName("main").call();

        //List<Ref> testbranch1 = git.branchList().setContains("testbranch1").call();

        Repository repository = git.getRepository();
        //Map<String, Ref> allRefs = repository.getAllRefs();
        //allRefs.forEach((s,r)->{
        //    System.out.println(s + " -> " + r);
        //});

        Ref ref = repository.getRef("refs/heads/main");

        MergeResult call = git.merge().include(ref).call();
        System.out.println(call);



    }
}
