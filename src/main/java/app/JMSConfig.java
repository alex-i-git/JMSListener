package App;

import com.tibco.tibjms.TibjmsConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;

@Configuration
@EnableJms
public class JMSConfig {

    private String broker = "tcp://localhost:7222";
    private String user = "admin";
    private String password = "";

    @Bean
    public TibjmsConnectionFactory connectionFactory() throws JMSException {
        TibjmsConnectionFactory factory = new TibjmsConnectionFactory();
        factory.setServerUrl(broker);
        factory.setUserName(user);
        factory.setUserPassword(password);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() throws JMSException {
        JmsTemplate jms = new JmsTemplate();
        jms.setConnectionFactory(connectionFactory());
        return jms;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListener() throws JMSException {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        return factory;
    }

}
