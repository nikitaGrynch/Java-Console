package step.learning.ioc;

import step.learning.ioc.services.hash.HashService;
import step.learning.ioc.services.random.RandomService;

import javax.inject.Inject;
import javax.inject.Named;

public class IoCDemo2 {
    private final HashService digestHashService;
    private final HashService signatureHashService;
    private final RandomService randomService;

//    @Inject
//    public IoCDemo2(@Named("Digest-hash") HashService digestHashService,
//                    @Named("Signature-hash") HashService signatureHashService, RandomService randomService) {
//        this.digestHashService = digestHashService;
//        this.signatureHashService = signatureHashService;
//        this.randomService = randomService;
//    }

    @Inject
    public IoCDemo2(@Named("Digest-hash") HashService digestHashService,
                    @Named("Signature-hash") HashService signatureHashService, RandomService randomService) {
        this.digestHashService = digestHashService;
        this.signatureHashService = signatureHashService;
        this.randomService = randomService;
    }

    @Inject @Named("Digest-hash")
    private HashService digestHashService2;

    public void run() {
//        System.out.println("IoC Demo");
//        System.out.println(digestHashService.hash("IoC Demo"));
//        System.out.println(signatureHashService.hash("IoC Demo"));
//        System.out.println(digestHashService2.hash("IoC Demo"));
//        System.out.println(digestHashService.hashCode() + " " + digestHashService2.hashCode());

        System.out.println(randomService.randomHex(6));
    }
}
