package common.repository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


//http://codecouple.pl/2016/09/30/8-spring-boot-email-szablon-i-wysylanie/
//http://docs.spring.io/spring/docs/4.3.9.RELEASE/spring-framework-reference/htmlsingle/#mail
@Component
public class EmailManager {

	private JavaMailSender mailSender;
	private MimeMessage mimeMessage;
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public MimeMessage prepeareMimeMessage(String from, String[] to, String subject, String content) throws MessagingException {
		MimeMessage mime = this.mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mime, true);
		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content, true);
		return mime;
		//this.mailSender.send(mime);

	}
	
	public void send(){
		if(mimeMessage != null )
		this.mailSender.send(mimeMessage);
	}

/*	public void placeOrder() {
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		try {
			this.mailSender.send(msg);
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
		}
	}*/

	public void prepeareMessage(String from, String[] to, String subject, String content) {
		try {
			this.mimeMessage = prepeareMimeMessage(from, to, subject, content);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
