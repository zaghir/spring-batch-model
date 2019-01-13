package com.zaghir.batch.batchspringmodel.service.mail;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface SendMailService {
	
	public void sendMail(String mailFrom , String mailTo, String mailSubject, String content ,Map<String, InputStream> listInputfiles  );
	public void sendMail(String mailFrom , String mailsTo, String mailSubject, String templateMail  ,Map<String, InputStream> listInputfiles , Map model);
	public void sendMailToList(String mailFrom , List<String> mailsTo, String mailSubject, String content ,Map<String, InputStream> listInputfiles  );
	public void sendMailToList(String mailFrom , List<String> mailsTo, String mailSubject, String content ,Map<String, InputStream> listInputfiles  ,Map model);


}
