/**
 * The Station class represents a mining station in the mine.
 *
 * Key Responsibilities:
 * - Managing gem deposition by miners
 * - Handling cart arrivals and departures
 * - Loading gems onto waiting carts
 * - Coordinating with engines via IStayable interface
 *
 * Synchronization:
 * - All public methods are synchronized for thread safety
 * - Uses wait/notifyAll for thread coordination
 * - Maintains strict mutual exclusion for cart and gem access through monitor
 *
 * @author zuoweit@student.unimelb.edu.au
 * @date 20 March 2025
 */
public class Station implements IStayable {

    /** The current cart present at this station (null if empty) */
    Cart cart = null;

    /** Unique identifier for this station */
    private int id;

    /** Flag indicating whether this station currently holds a gem */
    private boolean hasGem = false;

    /**
     * Constructs a new Station with the specified identifier.
     * @param id The unique numeric identifier for this station
     */
    public Station(int id) {
        this.id = id;
    }

    /**
     * Deposits a gem at this station (called by Miner threads).
     * Blocks until:
     * - The station has no existing gem (hasGem == false)
     * Then:
     * - Sets hasGem to true
     * - Notifies all waiting threads
     */
    public synchronized void depositGem() {
        // wait for station to be empty
        while (hasGem) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }

        // mines and deposit gem
        hasGem = true;
        notifyAll();
    }

    /**
     * Internal method to load a gem onto the current cart.
     * Blocks until:
     * - A gem is available (hasGem == true)
     * Then:
     * - Increments the cart's gem count
     * - Clears the station's gem flag
     * - Notifies all waiting threads
     * - Prints loading notification
     */
    private synchronized void loadGem() {
        // wait for gem
        while (!hasGem) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }

        // Get gem
        this.cart.gems++;
        hasGem = false;
        notifyAll();

        // Print statement
        System.out.println(this.cart + " loaded with a gem");
    }

    /**
     * Causes the current thread to wait until notified.
     * Part of IStayable interface implementation.
     */
    @Override
    public synchronized void iWait() {
        try {
            wait();
        }
        catch (InterruptedException e) {}
    }


    /**
     * Wakes up all threads waiting on this station.
     * Part of IStayable interface implementation.
     */
    @Override
    public synchronized void iNotifyAll() {
        notifyAll();
    }

    /**
     * Loads and removes the current cart from this station.
     * Blocks until:
     * - A cart is present (cart != null)
     * Then:
     * - Loads a gem onto the cart (via loadGem())
     * - Removes the cart from the station
     * - Notifies all waiting threads
     * @return The cart that was loaded
     */
    @Override
    public synchronized Cart loadCart() {
        // wait for cart
        while (cart == null) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }

        // load a gem
        loadGem();

        // Cart moves
        Cart removedCart = this.cart;
        this.cart = null;
        notifyAll();

        return removedCart;
    }

    /**
     * Accepts delivery of a new cart to this station.
     * Blocks until:
     * - The station is empty (cart == null)
     * Then:
     * - Sets the current cart
     * - Notifies all waiting threads
     * @param arrivedCart The cart being delivered
     */
    @Override
    public synchronized void deliverCart(Cart arrivedCart) {
        // Wait for station to be empty
        while (cart != null) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }

        // Cart arrives
        this.cart = arrivedCart;
        notifyAll();
    }

    /**
     * Returns a string representation of this station.
     * @return String in format "station [id]"
     */
    @Override
    public String toString() {
        return "station " + id;
    }
}
