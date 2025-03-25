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

    private Cart carrriedCart = null;

    private boolean atBottom = false;

    public Elevator() {
    }

    public boolean getStatus() { return atBottom; }

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

    public void descend() {
        // Descend
        try {
            sleep(Params.ELEVATOR_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Arrive at bottom
        atBottom = true;
    }

    public void ascend() {
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
    public synchronized Cart getCart() {
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
    public synchronized void cartArrived(Cart arrivedCart) {
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
                // notify all IStayable instances
                notifyAll();
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (carrriedCart == null) {

                    // notify all IStayable instances
                    notifyAll();
                    try {
                        wait();
                    }
                    catch (InterruptedException e) {}
                }

                if (atBottom && carrriedCart != null) {
                    // Ascend
                    ascend();
                }
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
                        wait();
                    }
                    catch (InterruptedException e) {}
                }

                // Get cart from waiting queue when at top
                if (!atBottom && bufferOfArrivedCart.size() != 0) {
                    carrriedCart = bufferOfArrivedCart.remove(0);
                    // Descend
                    descend();
                }
            }
        }
    }
}
