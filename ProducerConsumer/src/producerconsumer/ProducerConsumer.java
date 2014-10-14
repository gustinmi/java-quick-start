package producerconsumer;

public class ProducerConsumer {

    public static void main(String[] args) {

        System.out.println("Zagon aplikacije");
        TaskContainer c = new TaskContainer(); // deljeno stanje
        DatabaseProducer dp = new DatabaseProducer(c); // nit izdelovalec
        DatabaseConsumer dc = new DatabaseConsumer(c); // nit potrošnik
        
        dp.start(); // start nit 1
        dc.start(); // start nit 2
        
        while (dp.isAlive() || dc.isAlive()); // živi dokler je vsaj 1 živa
        
        System.out.println("Konec programa ");
    }

    static class TaskContainer {

        private String sqlToExecute = "";

        public void setSql(String newSt) {
            this.sqlToExecute = newSt;
        }

        public String getSql() {
            String task = this.sqlToExecute;
            this.sqlToExecute = "";
            return task;
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
            }while(toExec.equalsIgnoreCase(""));
        }
        private void save(String sql){
            System.out.println("Saving:" + sql);
        }
    }

    static class DatabaseProducer extends Thread {
        private final String[] taskQueue = {
            "insert into user values (1,2,3)",
            "insert into user values (11,22,33)",
            "insert into user values (111,222,333)",
            "insert into user values (1111,2222,3333)"
        };
        
        private final TaskContainer task;
        
        public DatabaseProducer(TaskContainer task) {
            this.task = task;
        }
        public void run(){
            for (String it : taskQueue) {
                task.setSql(it);
                System.out.println("Adding new task" + it);
                try {
                    sleep((int)(Math.random()*100));
                }catch (InterruptedException ex) {}
            }
        }
    }

}
