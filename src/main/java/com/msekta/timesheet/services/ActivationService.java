package com.msekta.timesheet.services;

import com.msekta.timesheet.models.User;
import com.msekta.timesheet.repo.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ActivationService {

    private JavaMailSenderImpl mailSender;
    private UserDao userDao;

    @Autowired
    public ActivationService(JavaMailSenderImpl mailSender, UserDao userDao) {
        this.mailSender = mailSender;
        this.userDao = userDao;
    }

    public void sendMail(User user) throws Exception {
        Date date = Calendar.getInstance().getTime();
        MimeMessage mailMessage = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
        helper.setTo(user.getEmail());
        helper.setSubject("Your Timesheet account");
        helper.setText(getActivationMessage(user));
        helper.setFrom("studintinc@gmail.com");
        helper.setSentDate(date);
        mailSender.send(mailMessage);
    }

    private String getActivationMessage(User user) {
        return "Your Timesheet account credentials \n" + " Your login: " + user.getUsername() + "\n" + "Your password: " + user.getPassword() + "\n" + "Your time matters";
    }
    
    public void changePassword(Long id) throws Exception {
    	User user = userDao.findById(id).orElseThrow(NoSuchElementException::new);
    	String newPassword = UUID.randomUUID().toString();
    	user.setPassword(newPassword);
    	userDao.save(user);
    	this.sendMail(user);
    	
    	
    }

}
