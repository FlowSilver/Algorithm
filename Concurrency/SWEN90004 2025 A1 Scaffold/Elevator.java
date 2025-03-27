import java.util.ArrayList;
import java.util.List;

/**
 * The elevator class is responsible for disposing of carts once they
 * have completed their visit to the mines.
 *
 * @author zuoweit@student.unimelb.edu.au
 * @date 20 March 2025
 */
public class Elevator implements IStayable {

    protected List<Cart> bufferOfArrivedCart = new ArrayList<Cart>();
    protected List<Cart> bufferOfDepartedCart = new ArrayList<Cart>();

    private static int bufferSize = 20;

    private Cart carriedCart = null;

    private boolean atBottom = false;

    public Elevator() {
    }

    public boolean getStatus() { return atBottom; }

    public Cart getCarriedCart() {
        return carriedCart;
    }

    public void setCarriedCart(Cart carriedCart) {
        this.carriedCart = carriedCart;
    }

    public void setAtBottom(boolean atBottom) {
        this.atBottom = atBottom;
    }

    public List<Cart> getBufferOfArrivedCart() {
        return bufferOfArrivedCart;
    }

    public List<Cart> getBufferOfDepartedCart() {
        return bufferOfDepartedCart;
    }

    @Override
    public synchronized void iWait() {
        try {
            wait();
        }
        catch (InterruptedException e) {}
    }

    @Override
    public synchronized void iNotify() {
        notify();
    }

    @Override
    public synchronized void iNotifyAll() {
        notifyAll();
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

    @Override
    public synchronized Cart loadCart() {
        while (!atBottom || carriedCart == null) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }

        Cart cart = carriedCart;
        carriedCart = null;
        notifyAll();

        return cart;
    }

    @Override
    public synchronized void deliverCart(Cart arrivedCart) {
        while (!atBottom || carriedCart != null) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }

        carriedCart = arrivedCart;
        notifyAll();
    }

//    @Override
//    public void run() {
//        while (true) {
//            // At bottom
//            if (atBottom) {
//                // notify all IStayable instances
//                notifyAll();
//                try {
//                    wait();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//
//                if (carriedCart == null) {
//
//                    // notify all IStayable instances
//                    notifyAll();
//                    try {
//                        wait();
//                    }
//                    catch (InterruptedException e) {}
//                }
//
//                if (atBottom && carriedCart != null) {
//                    // Ascend
//                    ascend();
//                }
//            }
//
//            // At top
//            else {
//                // Add cart to Departure Cart Buffer
//                if (carriedCart != null) {
//                    bufferOfDepartedCart.add(carriedCart);
//                    carriedCart = null;
//                }
//
//                // The elevator waits while it's empty
//                if (bufferOfArrivedCart.size() == 0) {
//                    try {
//                        wait();
//                    }
//                    catch (InterruptedException e) {}
//                }
//
//                // Get cart from waiting queue when at top
//                if (!atBottom && bufferOfArrivedCart.size() != 0) {
//                    carriedCart = bufferOfArrivedCart.get(0);
//                    bufferOfArrivedCart.remove(0);
//
//                    // Descend
//                    descend();
//                }
//            }
//        }
//    }

    @Override
    public String toString() {
        return "elevator";
    }
}
