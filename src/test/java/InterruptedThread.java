public class InterruptedThread {

    public static void main(String[] args) {
        Thread loop = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (Thread.interrupted()) {
                        System.out.println("Stop");
                        break;
                    }
                    System.out.println("Continue");
                }
            }
        });

        loop.start();
        loop.interrupt();

    }

}
