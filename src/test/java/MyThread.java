
class MyThread implements Runnable {

    private Object obj1;
    private Object obj2;

    MyThread(Object obj1, Object obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    @Override
    public void run() {
        System.out.println("Acquiring locks");
        synchronized (obj1) {
            System.out.println("Acquired 1st lock");
            synchronized (obj2) {
                System.out.println("Acquired 2nd lock");
            }
            System.out.println("Released 2nd lock");
        }
        System.out.println("Released 1st lock");
    }
}