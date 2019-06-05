package test1;



import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;



public class SendMailService {


    public void sendMail(String from, String to, String subject, String content) throws JMSException {
        if (to.indexOf(";") != -1) {
            String[] tos = to.split(";");
            for (String t : tos) {
                if (null == t || "".equals(t.trim())) {
                    continue;
                }

                final MailContent mail = transfer2Mail(from, t, subject, content);

                this.sendMail(mail);
            }
        }
        else {
            final MailContent mail = transfer2Mail(from, to, subject, content);
            System.out.println(mail + " --- ");
            this.sendMail(mail);
        }
    }


    private MailContent transfer2Mail(String from, String to, String subject, String content) {
        MailContent mail = new MailContent();
        mail.setFrom(from);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setContent(content);
        System.out.println(mail + " --- ");

        return mail;
    }

    private void sendMail(final MailContent mail) throws JMSException {
        System.out.println(mail);

        ActiveMQPlugin p = new ActiveMQPlugin("failover://(tcp://127.0.0.1:61616)?initialReconnectDelay=1000");
        p.start();
        String subject = "mail-message";
        ActiveMQ.addSender(new JmsSender("testSender1", ActiveMQ.getConnection(), Destination.Queue, subject));//定义发送者
        System.out.println(mail);

        try {
            JmsSender sq1 = ActiveMQ.getSender("testSender1");
            ObjectMessage msg = sq1.getSession().createObjectMessage(mail);
            sq1.sendMessage(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

}
