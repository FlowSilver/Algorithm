import java.util.ArrayList;
import java.util.List;

/**
 * The elevator class is responsible for disposing of carts once they
 * have completed their visit to the mines.
 *
 * @author zuoweit@student.unimelb.edu.au
 * @date 20 March 2025
 */
public class Elevator extends Thread implements IStayable {

    protected List<Cart> bufferOfArrivedCart = new ArrayList<Cart>();
    protected List<Cart> bufferOfDepartedCart = new ArrayList<Cart>();

    private static int bufferSize = 20;

    Cart carrriedCart = null;

    boolean atBottom = false;

    public Elevator() {
    }


    public synchronized Cart depart() {
        while (bufferOfDepartedCart.size() == 0) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }

        // remove the first cart in queue
        Cart cart = bufferOfDepartedCart.remove(0);
        notify();
        return cart;
    }

    public synchronized void arrive(Cart cart) {
        while (bufferOfArrivedCart.size() >= Elevator.bufferSize) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }
        bufferOfArrivedCart.add(cart);
        notify();
    }

    private void descend() {
        // Descend
        try {
            sleep(Params.ELEVATOR_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Arrive at bottom
        atBottom = true;
    }

    private void ascend() {
        // Ascend
        try {
            sleep(Params.ELEVATOR_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Arrive at top
        atBottom = false;
    }

    @Override
    public Cart getCart() {
        while (!atBottom || carrriedCart == null) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }

        Cart cart = carrriedCart;
        carrriedCart = null;
        notifyAll();

        return cart;
    }

    @Override
    public void cartArrived(Cart arrivedCart) {
        while (!atBottom || carrriedCart != null) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }

        carrriedCart = arrivedCart;
        notifyAll();
    }

    @Override
    public void run() {
        while (true) {
            // At bottom
            if (atBottom) {
                notifyAll();

                // The elevator waits while it's empty
                if (bufferOfArrivedCart.size() == 0) {
                    try {
                        sleep(Params.operatorPause());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                // Ascend
                ascend();
            }

            // At top
            else {
                // Add cart to Departure Cart Buffer
                if (carrriedCart != null) {
                    bufferOfDepartedCart.add(carrriedCart);
                    carrriedCart = null;
                }

                // The elevator waits while it's empty
                if (bufferOfArrivedCart.size() == 0) {
                    try {
                        sleep(Params.operatorPause());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                // Get cart from waiting queue
                if (bufferOfArrivedCart.size() != 0)
                carrriedCart = bufferOfArrivedCart.remove(0);

                // Descend
                descend();
            }
        }
    }
}
