/**
 * The Elevator class is responsible for transporting carts between the surface and the underground mine.
 * It manages two buffers (arrival and departure) to queue carts waiting to enter the mine or depart from the mine,
 *
 * Synchronization is enforced via wait/notifyAll to handle concurrent access.
 *
 * Implements the IStayable interface to coordinate cart loading/delivery with engines.

 * @author zuoweit@student.unimelb.edu.au
 * @date 20 March 2025
 */
import java.util.ArrayList;
import java.util.List;

public class Elevator implements IStayable {
    /** Buffer for carts arriving at the surface, waiting to descend into the mine. */
    protected List<Cart> bufferOfArrivedCart = new ArrayList<Cart>();

    /** Buffer for carts departing the mine. */
    protected List<Cart> bufferOfDepartedCart = new ArrayList<Cart>();

    /** Maximum capacity of the arrival/departure buffers. */
    private static int bufferSize = 10;

    /** The cart carried by the elevator (null if empty). */
    private Cart carriedCart = null;

    /** Flag indicating whether the elevator is at the bottom of the mine. */
    private boolean atBottom = false;

    /**
     * Constructs an Elevator with empty buffers and no carried cart.
     */
    public Elevator() {
    }

    /**
     * Sets the cart to be carried by the elevator.
     * @param carriedCart The cart to be loaded.
     */
    public void setCarriedCart(Cart carriedCart) {
        this.carriedCart = carriedCart;
    }

    /**
     * Updates the elevator's position status.
     * @param atBottom true if the elevator is at the bottom, false otherwise.
     */
    public void setAtBottom(boolean atBottom) {
        this.atBottom = atBottom;
    }

    /**
     * Returns the elevator's current position status.
     * @return true if the elevator is at the bottom of the mine, false otherwise.
     */
    public boolean getStatus() { return atBottom; }

    /**
     * Gets the cart currently carried by the elevator.
     * @return The carried cart, or null if the elevator is empty.
     */
    public Cart getCarriedCart() {
        return carriedCart;
    }

    /**
     * Gets the buffer of carts waiting to descend into the mine.
     * @return A list of carts in the arrival buffer.
     */
    public List<Cart> getBufferOfArrivedCart() {
        return bufferOfArrivedCart;
    }

    /**
     * Gets the buffer of carts waiting to depart from the mine
     * @return A list of carts in the departure buffer.
     */
    public List<Cart> getBufferOfDepartedCart() {
        return bufferOfDepartedCart;
    }

    /**
     * Add carriedCart to departure buffer
     * Blocks if the buffer is full until it becomes available.
     */
    public synchronized void addDepartingCart() {
        while (bufferOfDepartedCart.size() >= Elevator.bufferSize) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }
        bufferOfDepartedCart.add(carriedCart);
        setCarriedCart(null);
        notifyAll();
    }

    /**
     * Load cart from the arrival buffer to the elevator
     */
    public synchronized void getArrivedCart() {
        setCarriedCart(bufferOfArrivedCart.get(0));
        bufferOfArrivedCart.remove(0);
        notifyAll();
    }

    /**
     * Removes and returns the next cart from the departure buffer.
     * Blocks if the buffer is empty until a cart arrives.
     * @return The departing cart.
     */
    public synchronized Cart depart() {
        while (bufferOfDepartedCart.size() == 0) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }

        // remove the first cart in queue
        Cart cart = bufferOfDepartedCart.remove(0);
        notifyAll();
        return cart;
    }

    /**
     * Adds a cart to the arrival buffer (waiting to descend into the mine).
     * Blocks if the buffer is full until it becomes available.
     * @param cart The cart to be added.
     */
    public synchronized void arrive(Cart cart) {
        while (bufferOfArrivedCart.size() >= Elevator.bufferSize) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }
        bufferOfArrivedCart.add(cart);
        notifyAll();
    }

    /**
     * Causes the current thread to wait until notified (implementing IStayable interface).
     */
    @Override
    public synchronized void iWait() {
        try {
            wait();
        }
        catch (InterruptedException e) {}
    }

    /**
     * Wakes up all waiting threads (implementing IStayable interface).
     */
    @Override
    public synchronized void iNotifyAll() {
        notifyAll();
    }

    /**
     * Loads a cart from the elevator (for engine pickup).
     * Blocks until:
     * - The elevator is at the bottom.
     * - The elevator is carrying an empty cart (gems == 0).
     * @return The unloaded cart.
     */
    @Override
    public synchronized Cart loadCart() {

        // Engine only loads a cart when there is an empty cart at bottom
        while (!atBottom || carriedCart == null || carriedCart.gems > 0) {
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

    /**
     * Delivers a cart from the mine to the elevator (after completing a circuit).
     * Blocks until:
     * - The elevator is at the bottom.
     * - The elevator is empty (no carried cart).
     * @param arrivedCart The cart to be loaded onto the elevator.
     */
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

    /**
     * Returns a string representation of the elevator.
     * @return "elevator".
     */
    @Override
    public String toString() {
        return "elevator";
    }
}
