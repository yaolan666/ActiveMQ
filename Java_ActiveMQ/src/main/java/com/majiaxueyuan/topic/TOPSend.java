package com.majiaxueyuan.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TOPSend {
    private static String BROCKURL = "tcp://127.0.0.1:61616";
    private static String TOPIC = "mjxy-topic";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD, BROCKURL);
        Connection connection = activeMQConnectionFactory.createConnection();
        //创建JMS连接
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(null);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        send(producer, session, "发布订阅，码家学院22222");
        System.out.println("发送成功");
        connection.close();
    }
    public static void send(MessageProducer producer,Session session,String msg)throws  JMSException{
        TextMessage textMessage = session.createTextMessage("我是消息："+msg);
        Destination destination = session.createTopic(TOPIC);
        producer.send(destination,textMessage);
    }
}
