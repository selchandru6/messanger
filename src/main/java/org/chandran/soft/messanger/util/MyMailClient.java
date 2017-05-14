package org.chandran.soft.messanger.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class MyMailClient {

	private static Logger logger = Logger.getLogger(MyMailClient.class);
	
	static String[] to = { "XXXXXXXXXX@txt.att.net" };
	static String[] cc = { "<inputemailToGettheEmail>@gmail.com" };
	static String[] bcc = { "<inputemailToGettheEmail>@gmail.com" };
	
	public static void main(String[] args) {
		String[] to = { "XXXXXXXXXX@me.com" };
		String[] cc = { "XXXXXXXXXX@gmail.com" };
		String[] bcc = { "XXXXXXXXXX@gmail.com" };
		// This is for google
		MyMailClient.sendMail1("<emailwhichcanbeused to send email>@gmail.com", "<emailPassword>",
				"smtp.gmail.com", "465", "true", "true", false,
				"javax.net.ssl.SSLSocketFactory", "false", to, cc, bcc,
				"Test Email from Java Program... (MyMailClient.Java)",
				"Test E-mail");
	}

	public synchronized static boolean sendMail(String userName,
			String passWord, String host, String port, String starttls,
			String auth, boolean debug, String socketFactoryClass,
			String fallback, String[] to, String[] cc, String[] bcc,
			String subject, String text) {
		Properties props = new Properties();
		// Properties props=System.getProperties();
		props.put("mail.smtp.user", userName);
		props.put("mail.smtp.host", host);
		
		logger.info("Message Send for INR Change");
		
		if (!"".equals(port))
			props.put("mail.smtp.port", port);
		if (!"".equals(starttls))
			props.put("mail.smtp.starttls.enable", starttls);
		props.put("mail.smtp.auth", auth);
		if (debug) {
			props.put("mail.smtp.debug", "true");
		} else {
			props.put("mail.smtp.debug", "false");
		}
		if (!"".equals(port))
			props.put("mail.smtp.socketFactory.port", port);
		if (!"".equals(socketFactoryClass))
			props.put("mail.smtp.socketFactory.class", socketFactoryClass);
		if (!"".equals(fallback))
			props.put("mail.smtp.socketFactory.fallback", fallback);
		try {
			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(debug);
			MimeMessage msg = new MimeMessage(session);
			/* Set the Body text */
		//	InternetReader.getWeather();
		//	FileInputStream fstream = new FileInputStream("out.txt");
			// Get the object of DataInputStream
		//	DataInputStream in = new DataInputStream(fstream);
		//	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	   //		String strLine = null;
		//	String bodyText = "";
			/*strLine = br.readLine();
			while ((strLine = br.readLine()) != null) {
				if (strLine.trim().length() > 0)
					bodyText += strLine;
			}
			bodyText=bodyText+"\n"+"<B>http://icert.doleta.gov/</B>";*/
			//bodyText =text;

			//msg.setText(bodyText, "text/html");
			msg.setContent(text,"text/html");
			msg.setSubject(subject);
			msg.setFrom(new InternetAddress("test@gmail.com"));
			for (int i = 0; i < to.length; i++) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
				logger.info("Message Send for INR Change to :"+to[i]);
			}
			for (int i = 0; i < cc.length; i++) {
				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
						cc[i]));
			}
			for (int i = 0; i < bcc.length; i++) {
				msg.addRecipient(Message.RecipientType.BCC,
						new InternetAddress(bcc[i]));
			}
			//****************************************
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = "out.txt";
			try{
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
			}catch (Exception ex){
				System.out.println(" Exception in file Attachment"+ex.initCause(ex));
			}
			// Send the complete message parts
			msg.setContent(multipart);
			msg.setContent(text,"text/html"); 
			 //**********************************************/
			msg.saveChanges();
			Transport transport = session.getTransport("smtp");
			transport.connect(host, userName, passWord);
			transport.sendMessage(msg, msg.getAllRecipients());
			logger.info("Sent message successfully from INR....");
			transport.close();

			return true;
		} catch (Exception mex) {
			mex.printStackTrace();
			return false;
		}
		}
		
		public synchronized static boolean sendMail1(String userName,
				String passWord, String host, String port, String starttls,
				String auth, boolean debug, String socketFactoryClass,
				String fallback, String[] to, String[] cc, String[] bcc,
				String subject, String text) {
			Properties props = new Properties();
			// Properties props=System.getProperties();
			props.put("mail.smtp.user", userName);
			props.put("mail.smtp.host", host);
			if (!"".equals(port))
				props.put("mail.smtp.port", port);
			if (!"".equals(starttls))
				props.put("mail.smtp.starttls.enable", starttls);
			props.put("mail.smtp.auth", auth);
			if (debug) {
				props.put("mail.smtp.debug", "true");
			} else {
				props.put("mail.smtp.debug", "false");
			}
			if (!"".equals(port))
				props.put("mail.smtp.socketFactory.port", port);
			if (!"".equals(socketFactoryClass))
				props.put("mail.smtp.socketFactory.class", socketFactoryClass);
			if (!"".equals(fallback))
				props.put("mail.smtp.socketFactory.fallback", fallback);
			try {
				Session session = Session.getDefaultInstance(props, null);
				session.setDebug(debug);
				MimeMessage msg = new MimeMessage(session);
				/* Set the Body text */
			//	InternetReader.getWeather();
				FileInputStream fstream = new FileInputStream("C:\\Users\\Sanjith\\IdeaProjects\\MyJavaBuddy\\out\\datafiles\\out.txt");
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine = null;
				String bodyText = "";
				strLine = br.readLine();
				while ((strLine = br.readLine()) != null) {
					if (strLine.trim().length() > 0)
						bodyText += strLine;
				}
				System.out.println("bodyText "+bodyText);
				bodyText=bodyText+"\n"+"<B>This Message from Auto Mailer</B>";

				//msg.setText(bodyText, "text/html");
				msg.setContent(bodyText,"text/html");
				msg.setSubject(subject);
				msg.setFrom(new InternetAddress("test@gmail.com"));
				for (int i = 0; i < to.length; i++) {
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
							to[i]));
				}
				for (int i = 0; i < cc.length; i++) {
					msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
							cc[i]));
				}
				for (int i = 0; i < bcc.length; i++) {
					msg.addRecipient(Message.RecipientType.BCC,
							new InternetAddress(bcc[i]));
				}
				//****************************************
				// Create the message part
				BodyPart messageBodyPart = new MimeBodyPart();

				// Create a multipar message
				Multipart multipart = new MimeMultipart();

				// Set text message part
				multipart.addBodyPart(messageBodyPart);

				// Part two is attachment
				messageBodyPart = new MimeBodyPart();
				String filename = "PermTime.txt";
				try{
				DataSource source = new FileDataSource(filename);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(filename);
				multipart.addBodyPart(messageBodyPart);
				}catch (Exception ex){
					System.out.println(" Exception in file Attachment"+ex.initCause(ex));
				}
				// Send the complete message parts
				msg.setContent(multipart);
				msg.setContent(bodyText,"text/html"); 
				 //**********************************************/
				msg.saveChanges();
				Transport transport = session.getTransport("smtp");
				transport.connect(host, userName, passWord);
				transport.sendMessage(msg, msg.getAllRecipients());
				System.out.println("Sent message successfully....");
				transport.close();

				return true;
			} catch (Exception mex) {
				mex.printStackTrace();
				return false;
			}
		}
		
		public static boolean sentMessage(String subject, String message){			
			return sendMail("<email can be used to send email>", "<password....>", "smtp.gmail.com",
					"465", "true", "true", true, "javax.net.ssl.SSLSocketFactory", "false",
					to, cc, bcc,subject, message);			
		}
}