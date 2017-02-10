package com.cjy.code.mail;

import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class Sendmail {

    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.exmail.qq.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");

        //javamail���岽�����ʼ�

        //1.����session
        Session session = Session.getInstance(prop);
        //����debug״̬
        session.setDebug(true);

        //2.ͨ�� Session ��� transport

        Transport ts = session.getTransport();

        //3.��������
        ts.connect("caojiayao.sh@superjia.com", "Violet1234");

        //4.�����ʼ�
        Message message = createSimpleMail(session);
        //5.�����ʼ�
        ts.sendMessage(message, message.getAllRecipients());
        //�ر�����
        ts.close();
    }

    /**
     * 46 * @Method: createSimpleMail 47 * @Description: ����һ��ֻ�����ı����ʼ� 48
     * * @Anthor:�°����� 49 * 50 * @param session 51 * @return 52 * @throws
     * Exception 53
     */

    public static MimeMessage createSimpleMail(Session session) throws Exception {
        //�����ʼ�����
        MimeMessage message = new MimeMessage(session);
        //ָ���ʼ��ķ�����
        message.setFrom(new InternetAddress("caojiayao.sh@superjia.com"));
        //ָ���ʼ����ռ��ˣ����ڷ����˺��ռ�����һ���ģ��Ǿ����Լ����Լ���
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("caojiayao.sh@superjia.com"));
        //�ʼ��ı���
        message.setSubject("ֻ�����ı��ļ��ʼ�");
        //�ʼ����ı�����
        message.setContent("��ð���", "text/html;charset=UTF-8");
        //���ش����õ��ʼ�����
        return message;
    }

    /**
     * @Method: createImageMail
     * @Description: ����һ���ʼ����Ĵ�ͼƬ���ʼ�
     * @Anthor:�°�����
     * @param session
     * @return
     * @throws Exception
     */
    public static MimeMessage createImageMail(Session session) throws Exception {
        //�����ʼ�
        MimeMessage message = new MimeMessage(session);
        // �����ʼ��Ļ�����Ϣ
        //������
        message.setFrom(new InternetAddress("gacl@sohu.com"));
        //�ռ���
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("xdp_gacl@sina.cn"));
        //�ʼ�����
        message.setSubject("��ͼƬ���ʼ�");

        // ׼���ʼ�����
        // ׼���ʼ���������
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("����һ���ʼ����Ĵ�ͼƬ<img src='cid:xxx.jpg'>���ʼ�", "text/html;charset=UTF-8");
        // ׼��ͼƬ����
        MimeBodyPart image = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("src\\1.jpg"));
        image.setDataHandler(dh);
        image.setContentID("xxx.jpg");
        // �������ݹ�ϵ
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(image);
        mm.setSubType("related");

        message.setContent(mm);
        message.saveChanges();
        //�������õ��ʼ�д�뵽E�����ļ�����ʽ���б���
        message.writeTo(new FileOutputStream("E:\\ImageMail.eml"));
        //���ش����õ��ʼ�
        return message;
    }

    /**
     * @Method: createAttachMail
     * @Description: ����һ����������ʼ�
     * @Anthor:�°�����
     * @param session
     * @return
     * @throws Exception
     */
    public static MimeMessage createAttachMail(Session session) throws Exception {
        MimeMessage message = new MimeMessage(session);

        //�����ʼ��Ļ�����Ϣ
        //������
        message.setFrom(new InternetAddress("gacl@sohu.com"));
        //�ռ���
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("xdp_gacl@sina.cn"));
        //�ʼ�����
        message.setSubject("JavaMail�ʼ����Ͳ���");

        //�����ʼ����ģ�Ϊ�˱����ʼ����������������⣬��Ҫʹ��charset=UTF-8ָ���ַ�����
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("ʹ��JavaMail�����Ĵ��������ʼ�", "text/html;charset=UTF-8");

        //�����ʼ�����
        MimeBodyPart attach = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("src\\2.jpg"));
        attach.setDataHandler(dh);
        attach.setFileName(dh.getName()); //

        //���������������ݹ�ϵ
        MimeMultipart mp = new MimeMultipart();
        mp.addBodyPart(text);
        mp.addBodyPart(attach);
        mp.setSubType("mixed");

        message.setContent(mp);
        message.saveChanges();
        //��������Emailд�뵽E�̴洢
        message.writeTo(new FileOutputStream("E:\\attachMail.eml"));
        //�������ɵ��ʼ�
        return message;
    }

    /**
     * @Method: createMixedMail
     * @Description: ����һ��������ʹ�ͼƬ���ʼ�
     * @Anthor:�°�����
     * @param session
     * @return
     * @throws Exception
     */
    public static MimeMessage createMixedMail(Session session) throws Exception {
        //�����ʼ�
        MimeMessage message = new MimeMessage(session);

        //�����ʼ��Ļ�����Ϣ
        message.setFrom(new InternetAddress("gacl@sohu.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("xdp_gacl@sina.cn"));
        message.setSubject("�������ʹ�ͼƬ�ĵ��ʼ�");

        //����
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("xxx����Ů��xxxx<br/><img src='cid:aaa.jpg'>", "text/html;charset=UTF-8");

        //ͼƬ
        MimeBodyPart image = new MimeBodyPart();
        image.setDataHandler(new DataHandler(new FileDataSource("src\\3.jpg")));
        image.setContentID("aaa.jpg");

        //����1
        MimeBodyPart attach = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("src\\4.zip"));
        attach.setDataHandler(dh);
        attach.setFileName(dh.getName());

        //����2
        MimeBodyPart attach2 = new MimeBodyPart();
        DataHandler dh2 = new DataHandler(new FileDataSource("src\\����.zip"));
        attach2.setDataHandler(dh2);
        attach2.setFileName(MimeUtility.encodeText(dh2.getName()));

        //������ϵ:���ĺ�ͼƬ
        MimeMultipart mp1 = new MimeMultipart();
        mp1.addBodyPart(text);
        mp1.addBodyPart(image);
        mp1.setSubType("related");

        //������ϵ:���ĺ͸���
        MimeMultipart mp2 = new MimeMultipart();
        mp2.addBodyPart(attach);
        mp2.addBodyPart(attach2);

        //�������ĵ�bodypart
        MimeBodyPart content = new MimeBodyPart();
        content.setContent(mp1);
        mp2.addBodyPart(content);
        mp2.setSubType("mixed");

        message.setContent(mp2);
        message.saveChanges();

        message.writeTo(new FileOutputStream("E:\\MixedMail.eml"));
        //���ش����õĵ��ʼ�
        return message;
    }

}
