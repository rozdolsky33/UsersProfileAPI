package com.arwest.developer.profile_app_api.unitility;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.arwest.developer.profile_app_api.io.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class EmailConstructor {


    private Environment env;
    private TemplateEngine templateEngine;

    @Autowired
    public EmailConstructor(Environment env,TemplateEngine templateEngine ) {
        this.env = env;
        this.templateEngine = templateEngine;
    }

    public MimeMessagePreparator constructNewUserEmail(AppUser user, String password) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("password", password);
        String text = templateEngine.process("newUserEmailText", context);
        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setPriority(1);
                email.setTo(user.getEmail());
                email.setSubject("Welcome");
                email.setText(text, true);
                email.setFrom(new InternetAddress(env.getProperty("support.email")));
            }
        };

        return messagePreparator;
    }

    public MimeMessagePreparator constructResetPasswordEmail(AppUser user, String password) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("password", password);
        String text = templateEngine.process("resetPasswordEmailTemplate", context);
        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setPriority(1);
                email.setTo(user.getEmail());
                email.setSubject("New Password");
                email.setText(text, true);
                email.setFrom(new InternetAddress(env.getProperty("support.email")));
            }
        };
        return messagePreparator;
    }

    public MimeMessagePreparator constructUpdateUserProfileEmail(AppUser user) {
        Context context = new Context();
        context.setVariable("user", user);
        String text = templateEngine.process("updateUserProfileEmailTemplate", context);
        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setPriority(1);
                email.setTo(user.getEmail());
                email.setSubject("Profile Update");
                email.setText(text, true);
                email.setFrom(new InternetAddress(env.getProperty("support.email")));
            }
        };
        return messagePreparator;
    }
}
