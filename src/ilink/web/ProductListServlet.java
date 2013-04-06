package ilink.web;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import ilink.utils.iLinkUtils;
import ilink.utils.ReferenceTableLoader;
import ilink.utils.MessageLoader;
import ilink.domain.ProductDataType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ProductListServlet extends HttpServlet {
    protected final Log logger = LogFactory.getLog(getClass());

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Hello, world");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/plain");
        String productName = req.getParameter("pdrQueryString");
        String errorString = "";

        List<ProductDataType> prodList = null;
        String prdText = "";
        try {
            if (!iLinkUtils.isEmpty(productName)) {
                errorString = "ERROR_LOADING_PRODUCTS";
                prodList = ReferenceTableLoader.getProducts(productName);
            }
            ProductDataType currentPrd;

            ArrayList<String> result = new ArrayList<String>();
            if (prodList != null) {
                for (int i = 0; i < prodList.size(); i++) {
                    currentPrd = prodList.get(i);
                    result.add(currentPrd.toString());
                }
            }
            resp.getWriter().println(result);
        } catch (Exception ie) {
            prdText = MessageLoader.getMessage(errorString, null);
            logger.error("################ Error ProductListServlet.doPost ----> " + iLinkUtils.getExceptionMessage(ie));
        } finally {
            resp.getWriter().println(prdText);
        }
    }
}
