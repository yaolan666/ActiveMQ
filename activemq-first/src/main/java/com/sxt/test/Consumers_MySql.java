package com.sxt.test;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumers_MySql {
    public static void main(String[] args) throws JMSException {
        // ConnectionFactory 连接工厂 JMS用其他创建连接
        ConnectionFactory connectionFactory =new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,"tcp://127.0.0.1:61616");
        //JMS 客户连到Providerd的连接
        Connection connection= connectionFactory.createConnection();
        connection.start();
        //参数，是否开启事务模式。签收模式。
        Session session= connection.createSession(false,Session.AUTO_ACKNOWLEDGE);//不开启事务 启动自动签收
        //Destination:消息的目的地；消息发送给谁
        //获取参数session 参数 ldd-queue为Query的名字
        Destination destination=session.createQueue("ldd-queue");
        //MessageProducer消息的生产者
       MessageConsumer consumer =session.createConsumer(destination);

       while (true){
           TextMessage message=(TextMessage)consumer. receive();
           if (message!=null){
               System.out.println("收到消息："+message.getText());
           }else
               break;
       }
        session.close();
        connection.close();
    }
}
