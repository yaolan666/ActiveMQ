package com.majiaxueyuan.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TOPReceiver {
    private static String BROCKURL = "tcp://127.0.0.1:61616";
    private static String TOPIC = "mjxy-topic";

    public static void main(String[] args) throws JMSException {
        System.out.println("消费点启动");
        //创建ActiveMQConnectionFactory 会话工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://127.0.0.1:61616");
        //JMS客户端到JMS Provider的连接
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //不开启消息事务，消息主要发送消费者，则表示已经签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建一个队列
        Topic topic = session.createTopic(TOPIC);
        MessageConsumer consumer = session.createConsumer(topic);
        //consumer，setMessageListener（new MsgListener（））；

        while (true) {
            TextMessage textMessage = (TextMessage) consumer.receive();
            if (textMessage != null) {
                System.out.println("收到消息：" + textMessage.getText());
                session.commit();
            } else
                break;
        }
        connection.close();
    }
}
