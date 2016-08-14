package sd_activeMQ;


import chatejemplo.Chat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.JList;
import sun.java2d.SurfaceData;


public class Receiver implements MessageListener{

    String nombre = null;
    String selector = null;
    private QueueConnection queueConn = null;    
    private QueueSession queueSession = null;
    private Queue queue = null;
    
    public Receiver(String nombre) throws Exception  {
        
        this.nombre = nombre;
        this.selector = "destinatario = '";
        this.selector.concat(nombre);
        this.selector.concat("' ");
        
        Properties env = new Properties();					   				env.put(Context.INITIAL_CONTEXT_FACTORY,
                        "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        env.put(Context.PROVIDER_URL, "tcp://localhost:61616");
        env.put("queue.queueSampleQueue","Queue");
        // get the initial context
        InitialContext ctx = new InitialContext(env);

        // lookup the queue object
        queue = (Queue) ctx.lookup("queueSampleQueue");

        // lookup the queue connection factory
        QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup("QueueConnectionFactory");

        // create a queue connection
        queueConn = connFactory.createQueueConnection();

        // create a queue session
        queueSession = queueConn.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);

        // create a queue receiver
        //QueueReceiver queueReceiver = queueSession.createReceiver(queue, selector);
        QueueReceiver queueReceiver = queueSession.createReceiver(queue, "destinatario = 'Angely' ");

        // start the connection
        queueConn.start();

         // Create the message listener
        queueReceiver.setMessageListener(this);

		
	}
    @Override
    public void onMessage(Message message) {
    
    try {
        
         // Get the data from the message
         TextMessage msg = (TextMessage)message;
         // print the message
	System.out.println("Mensaje Recibido: " + msg.getText());
        Chat c = new Chat("CHAT");
        c.addChatToList(msg.getText());
        c.setVisible(true);
        
     } catch (JMSException jmse) {
         jmse.printStackTrace(); 
         System.exit(1);
      } catch (Exception jmse) {
         jmse.printStackTrace(); 
         System.exit(1);
      }
    }
}