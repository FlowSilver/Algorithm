/**
 * The IStayable interface defines the contract for objects that participate in
 * the cart transportation system within the system.
 *
 * This interface enables coordination between the Elevator, Engines, and Stations
 * by standardizing their interaction for cart transfers and concurrency control.
 *
 * @author zuoweit@student.unimelb.edu.au
 * @date 20 March 2025
 */
public interface IStayable {

    /**
     * Loads a cart from the implementing object (e.g., Elevator or Station).
     * Implementations should:
     * - Block until a cart is available for loading
     * - Return the loaded cart to the caller
     * - Handle thread synchronization
     *
     * @return The cart that was loaded
     */
    Cart loadCart();

    /**
     * Delivers a cart to the implementing object.
     * Implementations should:
     * - Block until the object is ready to receive a cart
     * - Accept the delivered cart
     * - Handle thread synchronization
     *
     * @param arrivedCart The cart to be delivered
     */
    void deliverCart(Cart arrivedCart);

    /**
     * Causes the current thread to wait until notified.
     * Used for synchronization between system components.
     */
    void iWait();

    /**
     * Wakes up all threads waiting on this object.
     * Used to notify waiting components when state changes occur.
     */
    void iNotifyAll();
}
