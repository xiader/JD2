package com.gmail.sasha.dao;

import com.gmail.sasha.dao.impl.ItemDaoImpl;
import com.gmail.sasha.dao.impl.OrderDaoImpl;
import com.gmail.sasha.dao.impl.UserDaoImpl;
import com.gmail.sasha.model.Item;
import com.gmail.sasha.model.Order;
import com.gmail.sasha.model.User;
import com.gmail.sasha.model.UserItemId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class OrderTest {

    private static final Logger logger = LogManager.getLogger(OrderTest.class);


    private UserDao userDao;
    private OrderDao orderDao;
    private ItemDao itemDao;

    @Before
    public void setUp() {
        userDao = new UserDaoImpl(User.class);
        orderDao = new OrderDaoImpl(Order.class);
        itemDao = new ItemDaoImpl(Item.class);
    }

    @Test
    public void testSaveOrder() {
        //   UserTest ut = new UserTest();
        //  ut.saveUserWithAssignedRole();

        Order order = new Order();
        order.setCreated(LocalDateTime.now());
        order.setQuantity(12);
        Transaction tx = null;
        try (Session session = orderDao.getCurrentSession()) {
            tx = session.beginTransaction();
            User user = userDao.findById(1L);
            Item item = itemDao.findById(1L);
            order.setId(new UserItemId(item.getId(), user.getId()));
            order.setItem(item);
            order.setUser(user);

            orderDao.create(order);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error(e.getMessage(), e);
        }

    }
}