package dev.zaeem.NotificationService.twilio;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.rest.lookups.v1.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Scanner;

@RestController
public class SmsController {
    @GetMapping(value = "/sendSMS")
    public ResponseEntity<String> sendSMS(String fromNumber, String toNumber, String message) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter twilio account sid");
        String twilioAccountSid = sc.nextLine();
        System.out.println("Enter twilio auth token");
        String twilioAuthToken = sc.nextLine();
        Twilio.init((twilioAccountSid), System.getenv(twilioAuthToken));
//        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));

        Message.creator(new PhoneNumber(toNumber),
                new PhoneNumber(fromNumber), message).create();

//        Message.creator(new PhoneNumber("<TO number - ie your cellphone>"),
//                new PhoneNumber("<FROM number - ie your Twilio number"), "Hello from Twilio 📞").create();

        return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
    }
}
