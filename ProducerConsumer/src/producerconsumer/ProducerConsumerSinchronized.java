package producerconsumer;

public class ProducerConsumerSinchronized {
    
    

    public static void main(String[] args) {

        System.out.println("App start");
        TaskContainer c = new TaskContainer(); // shared object
        DatabaseProducer dp = new DatabaseProducer(c); // thread producer
        DatabaseConsumer dc = new DatabaseConsumer(c); // thread consumer 
        
        dp.start(); // start thread 1
        dc.start(); // start thread 2
        
        while (dp.isAlive() || dc.isAlive()); // live until both are alive 
        
        System.out.println("App end");
    }

    /**
     *  object shared among threads ( all threads use it ) 
     */
    static class TaskContainer {

        private String sqlToExecute = "";
        private boolean isAvailable = false;

        public synchronized void setSql(String newSt) {
            
            while(isAvailable == true){
                try {
                    wait();
                } catch (InterruptedException ex) { }
            }
            
            this.sqlToExecute = newSt;
            isAvailable = true;
            notify();
        }

        public synchronized String getSql() {
            
            while(isAvailable == false){
                try {
                    wait();
                } catch (InterruptedException ex) { }
            }
            
            isAvailable = false;
            notify();
            //String task = this.sqlToExecute;
            return this.sqlToExecute; // = "";
            //return task;
        }
    }

    static class DatabaseConsumer extends Thread {
        private final TaskContainer task;
        public DatabaseConsumer(TaskContainer task) {
            this.task = task;
        }
        public void run(){
            String toExec;
            do{
                try {
                    sleep((int)(Math.random()*100));
                }catch (InterruptedException ex) {}
                toExec = task.getSql();
                save(toExec);
            }while( !toExec.equals("KONEC") );
        }
        private void save(String sql){
            System.out.println(" Saving: " + sql);
        }
    }

    static class DatabaseProducer extends Thread {
        // internal taskQueue; Should come from file or something
        private final String[] taskQueue = {
            "insert into user values (1,2,3)",
            "insert into user values (11,22,33)",
            "insert into user values (111,222,333)",
            "insert into user values (1111,2222,3333)",
            "KONEC"
        };
        
        private final TaskContainer task; //shared object
        
        public DatabaseProducer(TaskContainer task) {
            this.task = task;
        }
        public void run(){
            for (String it : taskQueue) { //empty queue one by one
                task.setSql(it);
                System.out.println(" Adding new task " + it);
                try {
                    sleep((int)(Math.random()*100));
                }catch (InterruptedException ex) {}
            }
        }
    }

}
