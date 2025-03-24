/**
 * The operator class is responsible for disposing of carts once they
 * have completed their visit to the mines.
 *
 * @author zuoweit@student.unimelb.edu.au
 * @date 20 March 2025
 */
public class Operator extends Thread{
    private Elevator elevator;
    public Operator(Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void run() {
        super.run();
    }
}
