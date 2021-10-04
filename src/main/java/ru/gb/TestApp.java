package ru.gb;

public class TestApp {
    public static void main(String[] args) {
        Factory factory = new Factory();
        OrderDAO orderDAO = new OrderDAO(factory);
        System.out.println("Список покупок клиента: " + orderDAO.checkOrders(1));
        System.out.println("Список покупателей продукта: " + orderDAO.checkCustomers(3));
    }
}
