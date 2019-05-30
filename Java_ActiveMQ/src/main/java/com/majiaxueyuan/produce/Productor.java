package com.majiaxueyuan.produce;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Productor {
    public static void main(String[] args) throws JMSException {
        //ConnectionFactory:连接工厂，JMS用它创建连接
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, "tcp://127.0.0.1:61616");
        //JMS 客户端到JMS Provider的连接
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //参数，覆盖开启事务模式，签收模式
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//自动签收
        //session签收
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        //Destination：消息的目的地；消息发送给谁
        //获取session注意参数值my-queue是Query的名字
        Destination destination = session.createQueue("mjxy-queue");
        //MessageProducer：消息生成者
        MessageProducer producer = session.createProducer(destination);
        //设置持久化
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //发送一条消息
        sendMsg(session, producer, "欢迎来到码家学院来学习0000");
        session.commit();//session签收需要添加的
        connection.close();

    }

    public static void sendMsg(Session session, MessageProducer producer, String msg) throws JMSException {
        //创建一类文本消息
        TextMessage message = session.createTextMessage("Hello ActiveMQ MSG =" + msg);
        //通过消息生产者发出消息
        producer.send(message);
    }
}
