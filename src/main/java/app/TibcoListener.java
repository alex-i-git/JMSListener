package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import javax.jms.TextMessage;

@Component
public class TibcoListener{

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "ToOtherBank")
    public void receiveMessage(Message message) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        String corId=textMessage.getJMSCorrelationID();
        System.out.println("Correlation ID: " + corId);

        jmsTemplate.send("FromOtherBank", new MessageCreator(){
            @Override
            public Message createMessage(Session session) throws JMSException {
                String ans = "<accountTransferRs>\n" +
                        "    <description>Description</description>\n" +
                        "    <statusCode>1</statusCode>\n" +
                        "</accountTransferRs>";
                Message message1 = session.createTextMessage(ans);
                message1.setJMSCorrelationID(textMessage.getJMSCorrelationID());

                return message1;
            }
        });
    }

}
