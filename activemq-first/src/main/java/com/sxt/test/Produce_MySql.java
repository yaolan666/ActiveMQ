package com.sxt.test;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Produce_MySql {
    private static String DEFAULTURL="tcp://127.0.0.1:61616";

    public static void main(String[] args) throws JMSException {
        // ConnectionFactory 连接工厂 JMS用其他创建连接
        ConnectionFactory connectionFactory =new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,DEFAULTURL);
        //JMS 客户连到Providerd的连接
        Connection connection= connectionFactory.createConnection();
        connection.start();
        //参数，是否开启事务模式。签收模式
        Session session= connection.createSession(false,Session.AUTO_ACKNOWLEDGE);//不开启事务 启动自动签收
        //Destination:消息的目的地；消息发送给谁
        //获取参数session 参数 ldd-queue为Query的名字
        Destination destination=session.createQueue("ldd-queue");
        //MessageProducer消息的生产者
        MessageProducer producer =session.createProducer(destination);
        // 设置不持久化
       // producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //发送一条消息
        sendMsg(session,producer,"06-03欢迎学习ActiveMQ4!",4);
        sendMsg(session,producer,"06-03欢迎学习ActiveMQ!9",9);
        sendMsg(session,producer,"06-03欢迎学习ActiveMQ!2",2);
        sendMsg(session,producer,"06-03欢迎学习ActiveMQ!7",7);

        //sendMsg(session,producer,"03欢迎学习ActiveMQ!");
        connection.close();
    }

    public static  void sendMsg(Session session,MessageProducer producer,String msg, int priority ) throws JMSException {
        //创建一条文本消息
        TextMessage message=session.createTextMessage("Hello ActiveMQ="+msg);

        //通过消息生产者发出消息
        producer.send(message,DeliveryMode.PERSISTENT,priority,0);// 第二参数为是否做数据持久化（1-不持久化 2-持久化） i1为优先级0-9数字越大优先级越高
        // l为生存时间单位毫秒 过时会失效
    }
}
