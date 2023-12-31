package step.learning.ioc;

import step.learning.ioc.services.hash.HashService;
import step.learning.ioc.services.hash.Md5HashService;
import step.learning.ioc.services.hash.Md5OldHashService;
import step.learning.ioc.services.hash.Sha2HashService;

import javax.inject.Inject;

public class IocDemo {

//    @Inject
//    private HashService hashService;
//
//    @Inject
//    private Md5HashService md5HashService;
//    @Inject
//    private Md5OldHashService md5OldHashService;

    @Inject
    private Sha2HashService sha2HashService;

    public void run(){
        String hash = sha2HashService.hash("IoC Demo");
        System.out.println("SHA-2: " + hash);
//        System.out.println("IoC Demo");
//        long t1, t2;
//        String hash;
//
//        t1 = System.nanoTime();
//        hash = md5HashService.hash("IoC Demo");
//        t2 = System.nanoTime();
//        System.out.println(hash + " " + (t2 - t1));
//
//        t1 = System.nanoTime();
//        hash = md5OldHashService.hash("IoC Demo");
//        t2 = System.nanoTime();
//        System.out.println(hash + " " + (t2 - t1));

    }
}
