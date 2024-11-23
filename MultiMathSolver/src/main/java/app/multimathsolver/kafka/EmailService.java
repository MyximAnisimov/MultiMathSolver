package app.multimathsolver.kafka;

public interface EmailService {
    public void sendSimpleMessage(String toAddress, String subject, String message);
}
