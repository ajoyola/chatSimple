package sd_activeMQ;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.QueueSender;
import javax.jms.DeliveryMode;
import javax.jms.QueueSession;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;

public class Sender {

    public String origen = null;
    
    public Sender(String origen) {
        this.origen = origen;
    }

	public void sendMessage(String msg, String destinatario) throws Exception {
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		env.put(Context.PROVIDER_URL, "tcp://localhost:61616");
		env.put("queue.queueSampleQueue", "Queue");
		// get the initial context
		InitialContext ctx = new InitialContext(env);
		// lookup the queue object
		Queue queue = (Queue) ctx.lookup("queueSampleQueue");
		// lookup the queue connection factory
		QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup("QueueConnectionFactory");
		// create a queue connection
		QueueConnection queueConn = connFactory.createQueueConnection();
		// create a queue session
		QueueSession queueSession = queueConn.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
		// create a queue sender
		QueueSender queueSender = queueSession.createSender(queue);
		queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		// create a simple message to say "Hello"
		TextMessage message = queueSession.createTextMessage(msg);
                //Se añade un selector de mensaje para especificar destinatario que consumirá el mensaje
                message.setStringProperty("destinatario",destinatario);
		// send the message
		queueSender.send(message);
		System.out.println("sent: " + message.getText());
		queueConn.close();
	}
}