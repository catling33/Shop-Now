package onlineShop.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.entity.Product;

// create object
@Repository
public class ProductDao {

    @Autowired
    private SessionFactory sessionFactory;

    // create session
    // session provides API to use
    public void addProduct(Product product) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            // turn on transaction
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // if error in saving, go back before save
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                // close if not in use
                session.close();
            }
        }
    }

    public void deleteProduct(int productId) {
        // get session
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            // productID -> get from request
            Product product = session.get(Product.class, productId);
            session.delete(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public void updateProduct(Product product) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.saveOrUpdate(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public Product getProductById(int productId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Product.class, productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAllProducts() {
        // get all products from DB
        List<Product> products = new ArrayList<Product>();
        try (Session session = sessionFactory.openSession()) {
            // if have, return all
            products = session.createCriteria(Product.class).list();
        } catch (Exception e) {
            // if don't have return null
            e.printStackTrace();
        }

        return products;
    }
}
