package et.com.gebeya.notificationservice.service;

import et.com.gebeya.notificationservice.kafka.ClientCreatedEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmailNotification(String recipient, String fullname) {
        MimeMessage message = emailSender.createMimeMessage();

        // Set multipart flag to true
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(recipient);
            helper.setSubject("Welcome To Sebsabi System");
            helper.setText("Dear " + fullname + ",\n\n"
                    + "We are thrilled to welcome you to the Sebsabi System, your comprehensive platform for efficient data collection and gig economy management. As a valued member of our community, you now have access to a range of powerful tools and features designed to streamline your workflows and enhance your productivity.\n\n"
                    + "To get started, simply click on the following link to log in to your account:\n"
                    + "[Login to Your Account](http://localhost:8080/auth/login)\n\n"
                    + "Once logged in, you'll be able to post questionnaires, visualize completed jobs, communicate with service providers, process payments through Mpesa, generate reports, vet gig workers, and manage your profile effortlessly.\n\n"
                    + "Should you have any questions or require assistance at any point, our dedicated support team is here to help. Feel free to reach out to us at [support@sebsabi.com](mailto:support@sebsabi.com) and we'll be more than happy to assist you.\n\n"
                    + "Thank you for choosing Sebsabi System. We look forward to empowering you with meaningful insights and a significant competitive edge.\n\n"
                    + "Best regards,\n\n"
                    + "Sebsabi Team\n"
                    + "Sebsabi System");
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle exception properly
            return;
        }

        emailSender.send(message);
    }
}
