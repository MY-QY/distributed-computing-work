import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ASyncConsumer {

    public static void main(String[] args) throws JMSException {
		String brokerURL = "tcp://localhost:61616";
		ConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		MessageConsumer messageConsumer = null;
		MyListener listener = null;
		
        
		try {
			factory = new ActiveMQConnectionFactory(brokerURL);
			connection = factory.createConnection();
			connection.start();
			
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("TEST");
			messageConsumer = session.createConsumer(destination);
			
			listener = new MyListener();
			
			messageConsumer.setMessageListener(listener);
			
			System.in.read();   // Pause
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
	
}
