/**
 * The engine class is responsible for disposing of carts once they
 * have completed their visit to the mines.
 *
 * @author zuoweit@student.unimelb.edu.au
 * @date 20 March 2025
 */
public class Engine extends Thread{
    private IStayable startStation;
    private IStayable endStation;
    public Engine(IStayable startStation, IStayable endStation) {
        this.startStation = startStation;
        this.endStation = endStation;
    }

    @Override
    public void run() {
        super.run();
    }
}
