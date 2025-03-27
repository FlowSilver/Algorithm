import java.util.List;
/**
 * The operator class is responsible for disposing of carts once they
 * have completed their visit to the mines.
 *
 * @author zuoweit@student.unimelb.edu.au
 * @date 20 March 2025
 */
public class Operator extends Thread{
    private Elevator elevator;

    private boolean atBottom;

    public Operator(Elevator elevator) {
        this.elevator = elevator;
    }

    public void ascend() {
        // Ascend
        try {
            sleep(Params.ELEVATOR_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Arrive at top
        elevator.setAtBottom(false);

        // Print statement
        System.out.println("elevator ascends " + (elevator.getCarriedCart() == null? "(empty)" : "with " + elevator.getCarriedCart()));
    }

    public void descend() {
        // Descend
        try {
            sleep(Params.ELEVATOR_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Arrive at bottom
        elevator.setAtBottom(true);

        // Print statement
        System.out.println("elevator descends " + (elevator.getCarriedCart() == null? "(empty)" : "with " + elevator.getCarriedCart()));
    }

    @Override
    public void run() {
        while (true) {

            atBottom = elevator.getStatus();

            // At bottom
            if (atBottom) {
                // notify all IStayable instances
                elevator.iNotifyAll();
                elevator.iWait();

                if (elevator.getCarriedCart() == null) {
                    // notify all IStayable instances
                    elevator.iNotifyAll();
                    elevator.iWait();
                }

                if (atBottom && elevator.getCarriedCart() != null) {
                    // Ascend
                    ascend();
                }
            }

            // At top
            else {
                List<Cart> arrivedCarts = elevator.getBufferOfArrivedCart();

                // Add cart to Departure Cart Buffer
                if (elevator.getCarriedCart() != null) {
                    arrivedCarts.add(elevator.getCarriedCart());
                    elevator.setCarriedCart(null);
                }

                // The elevator waits while it's empty
                if (arrivedCarts.size() == 0) {
                    elevator.iWait();
                }

                // Get cart from waiting queue when at top
                if (!atBottom && arrivedCarts.size() != 0) {
                    elevator.setCarriedCart(arrivedCarts.get(0));
                    arrivedCarts.remove(0);

                    // Descend
                    descend();
                }
            }
        }

//        atBottom = elevator.getStatus();
//
//        // wait
//        try {
//            sleep(Params.operatorPause());
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        // if the elevator stays
//        if(atBottom == elevator.getStatus()) {
//            // if at bottom
//            if(atBottom) {
//                elevator.ascend();
//            }
//            else {
//                elevator.descend();
//            }
//        }
    }
}
