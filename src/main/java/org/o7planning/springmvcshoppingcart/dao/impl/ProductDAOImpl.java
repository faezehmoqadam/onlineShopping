package org.o7planning.springmvcshoppingcart.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.o7planning.springmvcshoppingcart.dao.ProductDAO;
import org.o7planning.springmvcshoppingcart.entity.Product;
import org.o7planning.springmvcshoppingcart.model.PaginationResult;
import org.o7planning.springmvcshoppingcart.model.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Transactional
public class ProductDAOImpl implements ProductDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Product findProduct(String code) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Product.class);
        crit.add(Restrictions.eq("code", code));
        return (Product) crit.uniqueResult();
    }

    @Override
    public ProductInfo findProductInfo(String code) {
        Product product = this.findProduct(code);
        if (product == null) {
            return null;
        }
        return new ProductInfo(product.getCode(), product.getName(), product.getPrice(),product.getCategory(),product.getFee() ,product.isExist());

    }
    @Override
    public void save(ProductInfo productInfo) {
        String code = productInfo.getCode();
        Product product = null;

        boolean isNew = false;
        if (code != null) {
            product = this.findProduct(code);

        }
        if (product == null) {
            isNew = true;
            product = new Product();
            product.setCreateDate(new Date());
        }
        product.setCode(code);
        product.setName(productInfo.getName());
        product.setPrice(productInfo.getPrice());
        product.setCategory(productInfo.getCategory());
        product.setFee(productInfo.getFee());
        product.setExist(productInfo.isExist());
//        product.setQuantity(productInfo.getQuantity());

        if (productInfo.getFileData() != null) {
            byte[] image = productInfo.getFileData().getBytes();
            if (image != null && image.length > 0) {
                product.setImage(image);
            }
        }
        if (isNew) {
           this.sessionFactory.getCurrentSession().persist(product);
        }
        this.sessionFactory.getCurrentSession().flush();
    }


    @Override
    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage,
                                                       String likeName, boolean admin) {
        String sql = "Select new " + ProductInfo.class.getName()
                + "(p.code, p.name, p.price, p.category, p.fee, p.exist) " + " from "
                + Product.class.getName() + " p ";

        if(!admin) sql+="   where exist = 1 ";
        if (likeName != null && likeName.length() > 0) {
            sql += " and where lower(p.name) like :likeName ";
        }
        sql += " order by p.createDate desc ";

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery(sql);
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<ProductInfo>(query, page, maxResult, maxNavigationPage);
    }
    @Override
    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage,
                                                       String likeName,String cat, boolean admin) {
        String sql = "Select new " + ProductInfo.class.getName()
                + "(p.code, p.name, p.price, p.category, p.fee, p.exist) " + " from "
                + Product.class.getName() + " p " + " where category ='"+ cat+"'";
        if(!admin) sql+=" and exist = 1";
        if (likeName != null && likeName.length() > 0) {
            sql += " And lower(p.name) like :likeName ";
        }
        sql += "  order by p.createDate desc ";

        Session session =  sessionFactory.getCurrentSession();

        Query query = session.createQuery(sql);
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<ProductInfo>(query, page, maxResult, maxNavigationPage);
    }

    @Override
    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {
        return queryProducts(page, maxResult, maxNavigationPage, null,false);
    }

}