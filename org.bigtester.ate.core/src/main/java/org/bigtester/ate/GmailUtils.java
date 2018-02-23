/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2018, Montreal PROT, or individual contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Montreal PROT.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.bigtester.ate;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.SearchTerm;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc
/**
 * This class GmailUtils defines ....
 * 
 * @author Peidong Hu
 *
 */
public class GmailUtils {
//	private boolean textIsHtml = false;

//	/** The link. */
//	public String link;
	
//	/** The from. */
//	public static String from = null;

	/**
	 * Return the primary text content of the message.
	 *
	 * @param p the p
	 * @return the text
	 * @throws MessagingException the messaging exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static String getText(Part p) throws MessagingException, IOException {
		if (p.isMimeType("text/*")) {
			String s = (String) p.getContent();
//			textIsHtml = p.isMimeType("text/html");
			return s;
		}
		if (p.isMimeType("multipart/alternative")) {
			// prefer html text over plain text
			Multipart mp = (Multipart) p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null) {
						text = getText(bp);
					}
					continue;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null) {
						return s;
					}
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null) {
					return s;
				}
			}
		}
		return null;
	}

	public static Optional<String> searchEmail(String userName, String password,
			final String subjectKeyword, final String fromEmail,
			final String bodySearchText) throws IOException {
		Optional<String> link = Optional.empty();
		Properties properties = new Properties();
		//boolean val = false;
		// server setting
		properties.put("mail.imap.host", "imap.gmail.com");
		properties.put("mail.imap.port", 993);
		// SSL setting
		properties.setProperty("mail.imap.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.port",
				String.valueOf(993));
		Session session = Session.getDefaultInstance(properties);
		try {
			// connects to the message store
			Store store = session.getStore("imap");
			store.connect(userName, password);
			System.out.println("Connected to Email server….");
			// opens the inbox folder
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_ONLY);
			// create a search term for all "unseen" messages
			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, true);
			// create a search term for all recent messages
			Flags recent = new Flags(Flags.Flag.RECENT);
			FlagTerm recentFlagTerm = new FlagTerm(recent, false);
			SearchTerm searchTerm = new OrTerm(unseenFlagTerm, recentFlagTerm);
			// performs search through the folder
			Message[] foundMessages = folderInbox.search(searchTerm);
			System.out.println("Total Messages Found :" + foundMessages.length);
			for (int i = foundMessages.length - 1; i >= foundMessages.length - 10; i--) {
				Message message = foundMessages[i];
				Address[] froms = message.getFrom();
				String email = froms == null ? null
						: ((InternetAddress) froms[0]).getAddress();
				if (message.getSubject() == null) {
					continue;
				}
				Date date = new Date();// Getting Present date from the system
				long diff = date.getTime()
						- message.getReceivedDate().getTime();// Get The
																// difference
																// between two
																// dates
				long diffMinutes = diff / (60 * 1000) % 60; // Fetching the
															// difference of
															// minute

				System.out
						.println("Difference in Minutes b/w present time & Email Recieved time :"
								+ diffMinutes);
				try {
					if (message.getSubject().contains(subjectKeyword)
							&& email.equals(fromEmail)
							&& getText(message).contains(bodySearchText)
							&& diffMinutes <= 3) {
						String subject = message.getSubject();

						System.out.println("Found message #" + i + ": ");
						System.out.println("At " + i + " :" + "Subject:"
								+ subject);
						System.out.println("From: " + email + " on : "
								+ message.getReceivedDate());
						if (getText(message).contains(bodySearchText)) {
							System.out
									.println("Message contains the search text "
											+ bodySearchText);
							link = Optional.of(getConfirmationURL(message));
						} 
						break;
					}
				} catch (NullPointerException expected) {
					expected.printStackTrace();
				}
				System.out.println("Searching…." + "At " + i);
			}

			// disconnect
			folderInbox.close(false);
			store.close();
		} catch (NoSuchProviderException ex) {
			System.out.println("No provider.");
			ex.printStackTrace();
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store.");
			ex.printStackTrace();
		}
		return link;
	}

	public static String getConfirmLink(String userName, String password,
			final String subjectKeyword, final String fromEmail,
			final String bodySearchText) throws IOException {
		Properties properties = new Properties();
		// server setting
		properties.put("mail.imap.host", "imap.gmail.com");
		properties.put("mail.imap.port", 993);
		// SSL setting
		properties.setProperty("mail.imap.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.port",
				String.valueOf(993));
		Session session = Session.getDefaultInstance(properties);
		try {
			// connects to the message store
			Store store = session.getStore("imap");
			store.connect(userName, password);
			System.out.println("Connected to Email server….");
			// opens the inbox folder
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_ONLY);
			// create a search term for all "unseen" messages
			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, true);
			// create a search term for all recent messages
			Flags recent = new Flags(Flags.Flag.RECENT);
			FlagTerm recentFlagTerm = new FlagTerm(recent, false);
			SearchTerm searchTerm = new OrTerm(unseenFlagTerm, recentFlagTerm);
			// performs search through the folder
			Message[] foundMessages = folderInbox.search(searchTerm);
			System.out.println("Total Messages Found :" + foundMessages.length);
			for (int i = foundMessages.length - 1; i >= foundMessages.length - 10; i--) {
				Message message = foundMessages[i];
				Address[] froms = message.getFrom();
				String email = froms == null ? null
						: ((InternetAddress) froms[0]).getAddress();
				if (message.getSubject() == null) {
					continue;
				}
				Date date = new Date();// Getting Present date from the system
				long diff = date.getTime()
						- message.getReceivedDate().getTime();// Get The
																// difference
																// between two
																// dates
				long diffMinutes = diff / (60 * 1000) % 60; // Fetching the
															// difference of
															// minute

				System.out
						.println("Difference in Minutes b/w present time & Email Recieved time :"
								+ diffMinutes);
				try {
					if (message.getSubject().contains(subjectKeyword)
							&& email.equals(fromEmail)
							&& getText(message).contains(bodySearchText)
							&& diffMinutes <= 3) {
						String subject = message.getSubject();

						System.out.println("Found message #" + i + ": ");
						System.out.println("At " + i + " :" + "Subject:"
								+ subject);
						System.out.println("From: " + email + " on : "
								+ message.getReceivedDate());
						if (getText(message).contains(bodySearchText) == true) {
							System.out
									.println("Message contains the search text "
											+ bodySearchText);

						} else {

						}
						return getConfirmationURL(message);

					}
				} catch (NullPointerException expected) {
					expected.printStackTrace();
				}
				System.out.println("Searching…." + "At " + i);
			}

			// disconnect
			folderInbox.close(false);
			store.close();
		} catch (NoSuchProviderException ex) {
			System.out.println("No provider.");
			ex.printStackTrace();
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store.");
			ex.printStackTrace();
		}
		return null;
	}

	public static String getAutomationTask(String userName, String password)
			throws IOException {
		Properties properties = new Properties();
		String subject = null;

		// server setting
		properties.put("mail.imap.host", "imap.gmail.com");
		properties.put("mail.imap.port", 993);
		// SSL setting
		properties.setProperty("mail.imap.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.port",
				String.valueOf(993));
		Session session = Session.getDefaultInstance(properties);
		try {
			// connects to the message store
			Store store = session.getStore("imap");
			store.connect(userName, password);
			System.out.println("Connected to Email server….");
			// opens the inbox folder
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_WRITE);
			// create a search term for all "unseen" messages
			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			// create a search term for all recent messages
			Flags recent = new Flags(Flags.Flag.RECENT);
			FlagTerm recentFlagTerm = new FlagTerm(recent, true);
			SearchTerm searchTerm = new OrTerm(unseenFlagTerm, recentFlagTerm);
			// performs search through the folder
			Message[] foundMessages = folderInbox.search(searchTerm);

			if (foundMessages.length > 0) {
				Message message = foundMessages[0];
				message.setFlag(Flags.Flag.SEEN, true);
				subject = message.getSubject();

				if (subject.toLowerCase().contains("sms from")) {
					subject = (String) message.getContent();
				}
				Address[] froms = message.getFrom();
				String email = froms == null ? null
						: ((InternetAddress) froms[0]).getAddress();
				//from = email;
			}

			// disconnect
			folderInbox.close(false);
			store.close();

		} catch (NoSuchProviderException ex) {
			System.out.println("No provider.");
			ex.printStackTrace();
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store.");
			ex.printStackTrace();
		}
		return subject;
	}

	public static String getConfirmationURL(Message message) {
		String html = null;
		try {
			html = getText(message);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		Pattern p = Pattern.compile("<a href=\"(.*?)\">");
		Matcher m = p.matcher(html);
		while (m.find()) {
			// System.out.println("********************"+m.group(0));
			// System.out.println("--------------------"+m.group(1));
			return m.group(1);
		}
		return null;
	}

	public static void sendEmailBack(String from, String to, String subject,
			String mailBody) {

		// Assuming you are sending email from localhost
		String host = "localhost";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setText(mailBody);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}

	public static void sendEmail(String from, String to, String subject,
			String mailBody, String smtpUser, String smtpPassword) {

		
		final String username = smtpUser;
		
		final String password = smtpPassword;
		
		
		

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(mailBody);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
	
	public static void sendEmail(String from, String to, String subject,
			String mailBody, String attachmentFilePathName, String smtpUser, String smtpPassword) {
		if (StringUtils.isEmpty(attachmentFilePathName)) {
			sendEmail(from, to, subject, mailBody, smtpUser, smtpPassword);
			return;
		}
		
		final String username = smtpUser;
		final String password = smtpPassword;
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subject);
			
			// Set the email msg text.
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText(mailBody);

            // Set the email attachment file
            FileDataSource fileDataSource = new FileDataSource(attachmentFilePathName);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setDataHandler(new DataHandler(fileDataSource));
            attachmentPart.setFileName(fileDataSource.getName());

            // Create Multipart E-Mail.
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messagePart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}
