import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private String template;

    public EmailSender(String template) {
        this.template = template;
    }

    public String fillTemplate(Map<String, String> placeholders) {
        String filledTemplate = template;

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            String value = entry.getValue();
            filledTemplate = filledTemplate.replace(placeholder, value);
        }

        return filledTemplate;
    }

    public void sendEmail(String to, String subject, String content) throws MessagingException {
        
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "test.smtp.org");//Add/check the SMTP server is running on the system
        properties.setProperty("mail.smtp.port", "25");// and ensure that the localhost is free to use by running telnet command on commandprompt

        // Create a Session object
        Session session = Session.getDefaultInstance(properties);

        // Create a MimeMessage object
        MimeMessage message = new MimeMessage(session);

        // Set the sender and recipient addresses
        message.setFrom(new InternetAddress("Example@gmail.com"));//put any example gmail for test and use
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        // Set the subject and content of the email
        message.setSubject(subject);
        message.setText(content);

        
        Transport.send(message);// this is to send the send the email
    }

    public static void main(String[] args) {
        // Example template with placeholders
        String templateContent = "Hello {name},\n\nThank you And any placeholder message as weell Your email is {email}.";

        // Creating an instance of EmailTemplate
        EmailSender emailTemplate = new EmailSender(templateContent);

        // Creating a map of placeholders and their values
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("name", "Name:-Siddhant Gupta");
        placeholders.put("email", "email:-SiddhantG@example.com");

        // Filling the template with actual values
        String filledEmail = emailTemplate.fillTemplate(placeholders);

        // Sending the email
        try {
            emailTemplate.sendEmail("recipient@example.com", "Any Message as a placeholder for the User to see", filledEmail);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            System.out.println("Failed to send email. Error: " + e.getMessage());
        }
    }
}
