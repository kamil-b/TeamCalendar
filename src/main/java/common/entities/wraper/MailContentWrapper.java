package common.entities.wraper;

public class MailContentWrapper {

	private boolean sendMail;
	private String mailContent = "";

	public MailContentWrapper() {

	}

	public boolean isSendMail() {
		return sendMail;
	}

	public void setSendMail(boolean sendMail) {
		this.sendMail = sendMail;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

}
