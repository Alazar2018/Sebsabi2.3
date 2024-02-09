package et.com.gebeya.safaricom.sebsabi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableEurekaServer
public class ServiceDiscoveryApplication {
  public static void main(String[] args) {
    SpringApplication.run(ServiceDiscoveryApplication.class,args);
  }
}