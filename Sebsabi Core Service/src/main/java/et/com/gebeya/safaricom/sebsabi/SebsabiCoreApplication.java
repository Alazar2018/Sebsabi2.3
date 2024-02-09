package et.com.gebeya.safaricom.sebsabi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SebsabiCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(SebsabiCoreApplication.class,args);
    }
}