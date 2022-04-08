package com.example.microserviceeureka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@SpringBootApplication
@EnableEurekaServer
public class MicroserviceEurekaApplication implements CommandLineRunner {

	@Autowired
	private JavaMailSender javaMailSender;

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceEurekaApplication.class, args);
		System.out.println("Klk Eureka");
	}

	@Override
	public void run(String... args) {
		System.out.println("Sending Email...");

		try {
			//sendEmail();
			sendEmailWithAttachment();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}

	void sendEmail() {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setText("jeanmelvinlp27@gmail.com");
		msg.setSubject("Creando Usuario");
		msg.setText("Creando Usuario Microservicio");
		javaMailSender.send(msg);
	}

	void sendEmailWithAttachment() throws MessagingException, IOException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		// true = multipart message
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo("moxy04@gmail.com");
		helper.setSubject("Testing from Spring Boot");
		// default = text/plain
		//helper.setText("Check attachment for image!");
		// true = text/html
		helper.setText("<h1>Check attachment for image!</h1>", true);
		// hard coded a file path
		//FileSystemResource file = new FileSystemResource(new File("path/android.png"));
		//helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));
		javaMailSender.send(msg);
	}

}
