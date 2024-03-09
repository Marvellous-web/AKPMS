/**
 *
 */
package argus.util;

import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * @author bhupender.s
 *
 *         read property : emailMessages_en.properties
 *
 */
@Service
public class EmailUtil {
	private static final Logger LOGGER = Logger.getLogger(EmailUtil.class);

	@Autowired
	private MessageSource messageSource;

	private String content;

	private String subject;

	private String type = "plain";// html or plain

	private List<String> to;

	private List<String> cc;

	private List<String> bcc;

	public void sendEmail() throws Exception {
		LOGGER.info("in EmailUtil: sendEmail");

		Properties props = new Properties();

		props.put(
				"mail.transport.protocol",
				messageSource.getMessage("mail.transport.protocol", null,
						Locale.ENGLISH).trim());
		props.put("mail.smtp.host",
				messageSource
						.getMessage("mail.smtp.host", null, Locale.ENGLISH)
						.trim());
		props.put("mail.smtp.auth",
				messageSource
						.getMessage("mail.smtp.auth", null, Locale.ENGLISH)
						.trim());
		props.put("mail.smtp.port",
				messageSource
						.getMessage("mail.smtp.port", null, Locale.ENGLISH)
						.trim());
		props.put(
				"mail.smtp.starttls.enable",
				messageSource.getMessage("mail.smtp.starttls.enable", null,
						Locale.ENGLISH).trim());

		/*
		 * props.put("mail.transport.protocol", "smtp");
		 * props.put("mail.smtp.host", "smtp.argusmso.net");
		 * props.put("mail.smtp.auth", true); props.put("mail.smtp.port", 25);
		 * props.put("mail.smtp.starttls.enable", true);
		 */

		Authenticator auth = new SMTPAuthenticator();
		Session mailSession = Session.getInstance(props, auth);

		// for debugging infos to stdout
		// mailSession.setDebug(true);

		Transport transport = mailSession.getTransport();

		MimeMessage message = new MimeMessage(mailSession);

		message.setContent(content, "text/" + type);

		message.setSubject(subject);

		message.setFrom(new InternetAddress(messageSource.getMessage(
				"email.username", null, Locale.ENGLISH).trim()));

		// message.addRecipient(Message.RecipientType.TO, new InternetAddress(
		// "balwinder.singh@idsil.com"));

		if (null != to && to.size() > 0) {
			LOGGER.info("to.size() ::" + to.size());

			InternetAddress[] internetAddressTo = new InternetAddress[to.size()];

			for (int i = 0; i < to.size(); i++) {
				LOGGER.info("to :: " + to.get(i));

				internetAddressTo[i] = new InternetAddress(to.get(i));
			}

			message.addRecipients(Message.RecipientType.TO, internetAddressTo);
		}

		if (null != cc && cc.size() > 0) {
			LOGGER.info("cc.size() :: " + cc.size());

			InternetAddress[] internetAddressCC = new InternetAddress[cc.size()];

			for (int i = 0; i < cc.size(); i++) {
				LOGGER.info("cc :: " + cc.get(i));

				internetAddressCC[i] = new InternetAddress(cc.get(i));
			}

			message.addRecipients(Message.RecipientType.CC, internetAddressCC);
		}

		if (null != bcc && bcc.size() > 0) {
			LOGGER.info("bcc.size() :: " + bcc.size());

			InternetAddress[] internetAddressBCC = new InternetAddress[bcc
					.size()];

			for (int i = 0; i < bcc.size(); i++) {
				LOGGER.info("bcc :: " + bcc.get(i));

				internetAddressBCC[i] = new InternetAddress(bcc.get(i));
			}

			message.addRecipients(Message.RecipientType.TO, internetAddressBCC);
		}

		transport.connect();
		transport.sendMessage(message,
				message.getRecipients(Message.RecipientType.TO));
		transport.close();

		LOGGER.info("out EmailUtil: sendEmail");
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {

		public javax.mail.PasswordAuthentication getPasswordAuthentication() {

			String username = messageSource.getMessage("email.username", null,
					Locale.ENGLISH).trim();
			String password = messageSource.getMessage("email.password", null,
					Locale.ENGLISH).trim();

			return new javax.mail.PasswordAuthentication(username, password);
		}
	}

	/********** setter and getters *****************/

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the to
	 */
	public List<String> getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(List<String> to) {
		this.to = to;
	}

	/**
	 * @return the cc
	 */
	public List<String> getCc() {
		return cc;
	}

	/**
	 * @param cc
	 *            the cc to set
	 */
	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	/**
	 * @return the bcc
	 */
	public List<String> getBcc() {
		return bcc;
	}

	/**
	 * @param bcc
	 *            the bcc to set
	 */
	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}

}
