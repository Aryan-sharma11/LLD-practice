import java.sql.Array;
import java.util.ArrayList;

interface Iobservable{
    void addObserver(Iobserver ob);
    void removeObserver(Iobserver ob);
    void notifyUser();
}
interface Iobserver{
    void update();
}
class logger implements Iobserver {
    @Override
    public void update() {

    }
}

interface INotification{
    String getContent();
}
class SimpleNotificatio implements INotification {
    String text;
    SimpleNotificatio(String msg){
        text = msg;
    }
    @Override
    public String getContent() {
        return text;
    }
}
abstract class NotificationDecorator implements INotification {
    INotification notification = null;
    NotificationDecorator(INotification notification){
        this.notification = notification;
    }
}
class TimeStampDecorator extends NotificationDecorator{
    TimeStampDecorator(INotification noti){
        super(noti);
    }

    @Override
    public String getContent() {
        return notification.getContent()+" Added timeStamp";
    }
}
class SignatureDEcorator extends NotificationDecorator{
    SignatureDEcorator(INotification noti){
        super(noti);
    }
    @Override
    public String getContent() {
        return notification.getContent() +" addingSignatureToNotification";
    }
}


class NotificationObservable implements Iobservable{
    ArrayList<Iobserver> subscribers = new ArrayList<>();
    INotification currentNotification;
    NotificationObservable(){
        subscribers = new ArrayList<>();

    }
    @Override
    public void addObserver(Iobserver ob) {
        subscribers.add(ob);
    }

    @Override
    public void removeObserver(Iobserver ob) {
        subscribers.remove(ob);

    }

    @Override
    public void notifyUser() {
        for(Iobserver o : subscribers){
            o.update();
        }
    }
    public void setNotification(INotification notification){
        currentNotification = notification;
        notifyUser();
    }
    public INotification getNotification(){
        return currentNotification;
    }
    public String getNotificationContent(){
        return currentNotification.getContent();
    }
}
class Logger implements Iobserver{
    NotificationObservable n ;
    INotification noti;
    Logger(NotificationObservable notiObservable){
        n = notiObservable;
    }
    @Override
    public void update() {
        String notificationString = n.getNotificationContent();
        System.out.println("Logging notification: "+notificationString);
    }
}
interface  NotificationStrategy {
    void sendNotification(String content);
}
class EmailStrategy implements NotificationStrategy{
    private String emailId;
    EmailStrategy(String email) {
        this.emailId = email;
    }

    @Override
    public void sendNotification(String content) {
        System.out.println("Sending email to " + emailId + " " + content);
    }
}
class SmsStrategy implements NotificationStrategy {
    private String number;

    SmsStrategy(String num) {
        this.number = num;
    }

    @Override
    public void sendNotification(String content) {
        System.out.println("Sending notification to: " + number +" " + content);
    }
}
class PopupStrategy implements NotificationStrategy {
    @Override
    public void sendNotification(String content) {
        System.out.println("Sending popup" + content);
    }
}
class NotificationEngine implements Iobserver {
    ArrayList<NotificationStrategy> notitypes = new ArrayList<>();
    NotificationObservable n ;
    NotificationEngine(NotificationObservable n ) {
        this.n = n;
    }

    @Override
    public void update() {
        String notiString = n.getNotificationContent();
        for(NotificationStrategy x : notitypes){
            x.sendNotification(notiString);
        }
    }
    public void addStrategy(NotificationStrategy s){
        notitypes.add(s);
    }
}
class NotificationService{
    private NotificationObservable observable;
    private static NotificationService instance;
    private ArrayList<INotification> notifications = new ArrayList<>();
    private NotificationService(){
        observable = new NotificationObservable();
    }
    public static NotificationService getInstance(){
        if (instance == null){
            instance = new NotificationService();
        }
        return instance;
    }

    public NotificationObservable getObservable() {
        return observable;
    }
    public void setNotification(INotification notification){
        notifications.add(notification);
        observable.setNotification(notification);
    }
}

public class NotificationSystem {
    public static void main(String[] args) {
        NotificationService service = NotificationService.getInstance();
        NotificationObservable observable = service.getObservable();
        Logger logger = new Logger(observable);
        NotificationEngine notificationEngine = new NotificationEngine(observable);

        notificationEngine.addStrategy(new EmailStrategy("aryan.com"));
        notificationEngine.addStrategy(new PopupStrategy());
        notificationEngine.addStrategy(new SmsStrategy("123123"));

        observable.addObserver(logger);
        observable.addObserver(notificationEngine);
        INotification notification = new SimpleNotificatio("Shipping order ");
        notification = new TimeStampDecorator(notification);
        notification = new SignatureDEcorator(notification);

        service.setNotification(notification);

    }
}
