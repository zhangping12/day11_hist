package gz.itcast.dao;

import gz.itcast.entity.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类中存放对Prodcut对象的CRUD方法
 *
 */
public class ProductDao {
    //模拟"数据库",存放所有商品数据
    private static List<Product> data = new ArrayList<Product>();

    /**
     * 初始化商品数据
     */
    static{
        //只执行一次
        for(int i=1;i<=10;i++){
            data.add(new Product(""+i,"笔记本"+i,"LN00"+i,34.0+i));
        }
    }

    /**
     * 提供查询所有商品的方法
     */
    public List<Product> findAll(){
        return data;
    }

    /**
     * 提供根据编号查询商品的方法
     */
    public Product findById(String id){
        for (Product p : data) {
            if(p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }
}
