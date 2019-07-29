package ysi.cloud.aws.keypair;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class Keypair {
    public ClassPathResource getKeypairResourcePath(String tergetName) {
        return new ClassPathResource("/keypair/" + tergetName);
    }
}