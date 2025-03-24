public interface IStayable {
    Cart getCart();

    void cartArrived();

    void cartArrived(Cart arrivedCart);
}
