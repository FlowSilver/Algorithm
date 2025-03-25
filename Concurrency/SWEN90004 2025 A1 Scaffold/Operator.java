/**
 * The operator class is responsible for disposing of carts once they
 * have completed their visit to the mines.
 *
 * @author zuoweit@student.unimelb.edu.au
 * @date 20 March 2025
 */
public class Operator extends Thread{
    private Elevator elevator;

    private boolean elevatorStatus;

    public Operator(Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void run() {
        while (true) {
            elevatorStatus = elevator.getStatus();

            // wait
            try {
                sleep(Params.operatorPause());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // if the elevator stays
            if(elevatorStatus == elevator.getStatus()) {
                // if at bottom
                if(elevatorStatus) {
                    elevator.ascend();
                }
                else {
                    elevator.descend();
                }
            }
        }

    }
}
