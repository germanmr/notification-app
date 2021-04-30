import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ThreadsDeadLockExample {

    public static void main(String args[]) {

//        Object obj1 = "obj1";
//        Object obj2 = "obj2";
//        Thread thread1 = new Thread(null, new MyThread(obj1, obj2), "Thread-1");
//        Thread thread2 = new Thread(null, new MyThread(obj2, obj1), "Thread-2");
//        thread1.start();
//        thread2.start();

        String baseURL = "%s(Date='%s',Rate_Type='" + "exchangeRateErpType.getValue()" + "',From_Currency='USD',To_Currency='ARS')";
        String url = String.format(baseURL, "SAP_SERVICE_EXCHANGE_RATE_CLIENT", LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));

        System.out.println(url);
    }
}
