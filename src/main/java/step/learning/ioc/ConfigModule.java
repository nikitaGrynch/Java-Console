package step.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import step.learning.ioc.services.hash.HashService;
import step.learning.ioc.services.hash.Md5HashService;
import step.learning.ioc.services.hash.Md5OldHashService;
import step.learning.ioc.services.hash.Sha1HashService;
import step.learning.ioc.services.random.RandomService;
import step.learning.ioc.services.random.RandomServiceV1;
import step.learning.ioc.services.random.RandomServiceV2;

public class ConfigModule extends AbstractModule {


    @Override
    protected void configure() {
        // bind(HashService.class).to(Md5HashService.class);
//        // bind(HashService.class).to(Sha1HashService.class);
//        bind(HashService.class).annotatedWith(Names.named("Digest-hash")).to(Md5HashService.class);
//        bind(HashService.class).annotatedWith(Names.named("Signature-hash")).to(Sha1HashService.class);
    }

    @Provides
    @Named("Digest-hash")
    private HashService injectDigestHashService(){
        return new Md5OldHashService();
    }

    @Provides
    @Named("Signature-hash")
    private HashService injectSignatureHashService(){
        return new Sha1HashService();
    }

    private RandomService randomService;

//    @Provides
//    private RandomService injectRandomService() {
//        if (randomService == null) {
//            randomService = new RandomServiceV1();
//            randomService.seed("initial");
//        }
//        return randomService;
//    }

    @Provides
    private RandomService injectRandomV2Service() {
        if (randomService == null) {
            randomService = new RandomServiceV2();
            randomService.seed("initial");
        }
        return randomService;
    }

}
