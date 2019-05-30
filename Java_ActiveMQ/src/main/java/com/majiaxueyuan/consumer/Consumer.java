package com.majiaxueyuan.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {
    public static void main(String[] args) throws JMSException {
        //ConnectionFactory ： 连接工厂，JMS用它创建连接
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://127.0.0.1:61616");
        //JMS客户端到JMS Provider的连接
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //Session：一个发送或接收消息的线程
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //session签收
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        //Destination ：消息的目的地;消息发送给谁。
        //获取session注意参数值xingbo。xu。queue是一个服务器的queue，添在ActiveMQ的console配置
        Destination destination = session.createQueue("mjxy-queue");
        //消费者，消息接收者
        MessageConsumer consumer = session.createConsumer(destination);
        while (true) {
            TextMessage message = (TextMessage) consumer.receive();
            if (null != message) {
                System.out.println("收到消息：" + message.getText());
                session.commit();//session签收需要添加的
//                session.commit();
//     message.acknowledge();//代表手动签收成功
            } else
                break;
        }

        session.close();
        connection.close();
    }
}
