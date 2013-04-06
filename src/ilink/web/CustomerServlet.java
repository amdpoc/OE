package ilink.web;

import ilink.domain.CustListItem;
import ilink.service.CustListManager;
import ilink.utils.iLinkUtils;
import ilink.utils.MessageLoader;
import ilink.utils.PageContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.springframework.web.context.ContextLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CustomerServlet extends HttpServlet {
    protected final Log logger = LogFactory.getLog(getClass());

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Hello, world");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");

        String custName = req.getParameter("cstQueryString");
        String cstPageInd = req.getParameter("cstPageInd");

        String custText = "";
        try {
            if (custName != null) {
                CustListManager custManager = (CustListManager) ContextLoader.getCurrentWebApplicationContext().getBean("custListManager");

            PageContext pageCtx = (PageContext) req.getSession().getAttribute("NEW_CUST_LIST_PAGE_CTX");

            if (pageCtx == null) {
                pageCtx = new PageContext(iLinkUtils.CUST_LIST_PAGE_SIZE, iLinkUtils.CUST_LIST_ROW_NUM);
                req.getSession().setAttribute("NEW_CUST_LIST_PAGE_CTX", pageCtx);
            }
                String result = custManager.getCustListByNameToStr(custName, cstPageInd, pageCtx);
                resp.getWriter().println(result);
            }
        } catch (Exception ie) {
            custText = MessageLoader.getMessage("ERROR_LOADING_CUSTOMERS", null);
            logger.error("################ CustomerServlet.customerList ----> " + iLinkUtils.getExceptionMessage(ie));
        } finally {
            resp.getWriter().println(custText);
        }

    }
}
