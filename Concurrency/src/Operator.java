/**
 * The Operator class controls the elevator's movement between the surface and the underground mine,
 * coordinating with the Elevator to transport carts based on their availability and state.
 *
 * When the elevator is empty, the operator will send the elevator up or down at random intervals
 * Synchronizes with the Elevator's buffers (arrival/departure) to manage cart transfers.
 * Notifies waiting threads (e.g., engines) when the elevator arrives at the bottom.
 *
 * This class extends Thread to run concurrently with other system components (miners, engines).
 *
 * @author zuoweit@student.unimelb.edu.au
 * @date 20 March 2025
 */
import java.util.List;

public class Operator extends Thread{

    /** The elevator to be operated */
    private Elevator elevator;

    /**
     * Constructs an Operator tied to a specific Elevator.
     * @param elevator The Elevator instance this operator controls.
     */
    public Operator(Elevator elevator) {
        this.elevator = elevator;
    }

    /**
     * Moves the elevator from the bottom to the top (ascend).
     * Introduces a delay to simulate travel time.
     */
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

    /**
     * Moves the elevator from the top to the bottom (descend).
     * Introduces a delay to simulate travel time.
     */
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

    /**
     * Pauses the operator when the elevator is empty.
     * Sleeps for a random time to simulate idle periods.
     */
    private void operatorPause() {
        try {
            // pause while empty
            sleep(Params.operatorPause());
        }
        catch (InterruptedException e) {
        }
    }

    /**
     * Main loop of the Operator thread.
     * 1. Checks the elevator's position (top/bottom).
     * 2. At the bottom:
     *    - Notifies waiting threads (e.g., engines) via elevator.iNotifyAll().
     *    - Ascends if carrying a loaded cart (gems > 0) or pauses if empty.
     * 3. At the top:
     *    - Adds carried carts to the departure buffer (if any).
     *    - Descends with a new cart from the arrival buffer (if available).
     *    - Pauses if no carts are waiting.
     *
     * Synchronized to ensure thread-safe access to the Elevator's state.
     */
    @Override
    public synchronized void run() {
        while (true) {

            // At bottom
            if (elevator.getStatus()) {
                // notify all IStayable instances
                elevator.iNotifyAll();
                elevator.iWait();

                // Ascend when the cart is not empty
                if (elevator.getCarriedCart() != null && elevator.getCarriedCart().gems > 0) {
                    // Ascend
                    ascend();
                }
                else if (elevator.getCarriedCart() == null) {
                    operatorPause();
                    ascend();
                }

            }

            // At top
            else {
                List<Cart> arrivedCarts = elevator.getBufferOfArrivedCart();

                // Add cart to Departure Cart Buffer
                if (elevator.getCarriedCart() != null) {
                    elevator.addDepartingCart();
                }

                // The elevator waits while it's empty
                if (arrivedCarts.size() == 0) {
                    elevator.iWait();
                }

                // Get cart from waiting queue when at top
                if (arrivedCarts.size() != 0) {
                    elevator.getArrivedCart();

                    // Descend
                    descend();
                }
                else {
                    operatorPause();

                    // Descend
                    descend();
                }
            }
        }
    }
}
