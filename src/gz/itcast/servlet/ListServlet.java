package gz.itcast.servlet;

import gz.itcast.dao.ProductDao;
import gz.itcast.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 查询所有商品的servlet
 *
 */
public class ListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        //1.读取数据库，查询商品列表
        ProductDao dao = new ProductDao();
        List<Product> list = dao.findAll();

        //2.把商品显示到浏览器
        PrintWriter writer = response.getWriter();
        String html = "";

        html += "<html>";
        html += "<head>";
        html += "<title>显示商品列表</title>";
        html += "</head>";
        html += "<body>";
        html += "<table border='1' align='center' width='600px'>";
        html += "<tr>";
        html += "<th>编号</th><th>商品名称</th><th>商品型号</th><th>商品价格</th>";
        html += "</tr>";
        //遍历商品
        if (list!=null){
            for (Product p: list) {
                html += "<tr>";
                // /day11_hist/DetailServlet?id=1 访问DetailSErvlet的servlet程序，同时传递 名为id，值为1 的参数
                html += "<td>"+p.getId()+"</td><td><a href='"+request.getContextPath()+"/DetailServlet?id="+p.getId()+"'>"+p.getProName()+"</a></td><td>"+p.getProType()+"</td><td>"+p.getPrice()+"</td>";
                html += "<tr>";
            }
        }
        html += "</table>";
        /**
         * 显示浏览过的商品
         */
        html += "最近浏览过的商品：<br/>";
        //取出prodHist的cookie
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("prodHist")){
                    String prodHist = cookie.getValue();//3,2,1
                    String[] ids = prodHist.split(",");
                    //遍历浏览过的商品id
                    for (String id : ids) {
                        //查询数据库，查询对应的商品
                        Product p = dao.findById(id);
                        //显示到浏览器
                        html += ""+p.getId()+"&nbsp;"+p.getProName()+"&nbsp;"+p.getPrice()+"<br/>";
                    }
                }
            }
        }
        html += "</body>";
        html += "</html>";

        writer.write(html);
    }
}
