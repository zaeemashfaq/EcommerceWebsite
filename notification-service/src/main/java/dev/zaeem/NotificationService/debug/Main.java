package dev.zaeem.NotificationService.debug;

public class Main {
    public static void main(String[] args) {
        String twilioNumber = "TWILIO_NUMBER";
        String accountSid = "TWILIO_ACCOUNT_SID";
//        String secretKey = System.getenv(keyName);
        System.out.println(System.getenv(twilioNumber));
        System.out.println(System.getenv(accountSid));
        class env{
            String number;
            String sid;
        }
    }
}
