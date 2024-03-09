package argus.util;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author sumit.v
 * @version 1.0 This class will be used as utility to send email
 *
 */
public final class SendMailUtil {

	private static final Log LOGGER = LogFactory.getLog(SendMailUtil.class);

	private SendMailUtil(){

	}
	/**
	 *
	 */
	/**
	 * @param from
	 * @param to
	 * @param host
	 * @param username
	 * @param password
	 * @param subject
	 * @param text
	 * @param attachment
	 * @param ccArray
	 * @param bccArray
	 * @throws MessagingException
	 * This function is used to send the mail
	 */
	public static void sendMail(String from, String to[], String host,
			String username, String password, String subject, String text,
			String attachment, String ccArray[], String bccArray[])
			throws MessagingException {
		LOGGER.info("[send]: from = " + from + ", to = " + to.toString()
				+ ", host = " + host + ", username = " + username
				+ ", password = " + password + ", subject = " + subject
				+ ", text = " + text );
		Properties prop = new Properties();
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.port", "25");
		prop.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(prop, null);
		Store store = session.getStore("pop3s");
		store.connect(host, username, password);
		boolean debug = true;

		session.setDebug(debug);
		Message mgs = new MimeMessage(session);
		InternetAddress aFrom = new InternetAddress(from);
		mgs.setFrom(aFrom);
		InternetAddress aTo[] = new InternetAddress[to.length];
		for (int i = 0; i < to.length; i++) {
			aTo[i] = new InternetAddress(to[i]);
		}
		mgs.setRecipients(Message.RecipientType.TO, aTo);
		if (ccArray != null) {
			InternetAddress cc[] = new InternetAddress[ccArray.length];
			for (int i = 0; i < ccArray.length; i++) {
				cc[i] = new InternetAddress(ccArray[i]);
			}
			mgs.setRecipients(Message.RecipientType.CC, cc);
		}
		if (bccArray != null) {
			InternetAddress bcc[] = new InternetAddress[bccArray.length];
			for (int i = 0; i < bccArray.length; i++) {
				bcc[i] = new InternetAddress(bccArray[i]);
			}
			mgs.setRecipients(Message.RecipientType.BCC, bcc);
		}

		mgs.setSubject(subject);
		mgs.setContent(text, "text/html");
		// set the message content here
		Transport t = session.getTransport("smtps");
		try {
			t.connect(host, username, password);
			if (attachment != null) {
				MimeBodyPart messagePart = new MimeBodyPart();
				messagePart.setText(text);
				File file = new File(attachment);
				DataSource ds = new FileDataSource(file) {
				};
				MimeBodyPart mbp = new MimeBodyPart();
				mbp.setDataHandler(new DataHandler(ds));
				mbp.setFileName(file.getName());
				mbp.setDisposition(Part.ATTACHMENT);
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messagePart);
				multipart.addBodyPart(mbp);
				mgs.setContent(multipart);
			}
			t.sendMessage(mgs, mgs.getAllRecipients());
		} finally {
			t.close();
		}

	}

}
