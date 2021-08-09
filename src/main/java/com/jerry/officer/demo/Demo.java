package com.jerry.officer.demo;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/8/9 14:51
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        // Create a new repository
        Repository newlyCreatedRepo = FileRepositoryBuilder.create(new File("/tmp/new_repo/.git"));
        newlyCreatedRepo.create();
        // Open an existing repository
        Repository repo = new FileRepositoryBuilder()
                .setGitDir(new File("my_repo/.git"))
                .build();

        // Get a reference
        Ref master = repo.getRef("master");
        // Get the object the reference points to
        ObjectId masterTip = master.getObjectId();
        // Rev-parse
        ObjectId obj = repo.resolve("HEAD^{tree}");
        // Load raw object contents
        ObjectLoader loader = repo.open(masterTip);
        loader.copyTo(System.out);
        // Create a branch
        RefUpdate createBranch1 = repo.updateRef("refs/heads/branch1");
        createBranch1.setNewObjectId(masterTip);
        createBranch1.update();
        // Delete a branch
        RefUpdate deleteBranch1 = repo.updateRef("refs/heads/branch1");
        deleteBranch1.setForceUpdate(true);
        deleteBranch1.delete();
        // Config
        Config cfg = repo.getConfig();
        String name = cfg.getString("user", null, "name");


        // Porcelain
        // construct repo...
        Git git = new Git(repo);
        CredentialsProvider cp = new UsernamePasswordCredentialsProvider("username", "p4ssw0rd");
        Collection<Ref> remoteRefs = git.lsRemote()
                .setCredentialsProvider(cp)
                .setRemote("origin")
                .setTags(true)
                .setHeads(false)
                .call();
        for (Ref ref : remoteRefs) {
            System.out.println(ref.getName() + " -> " + ref.getObjectId().name());
        }
    }
}
