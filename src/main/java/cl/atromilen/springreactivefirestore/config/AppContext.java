package cl.atromilen.springreactivefirestore.config;

import com.google.cloud.spring.data.firestore.repository.config.EnableReactiveFirestoreRepositories;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableReactiveFirestoreRepositories("cl.atromilen.springreactivefirestore.repository")
@EnableTransactionManagement
public class AppContext {
}
