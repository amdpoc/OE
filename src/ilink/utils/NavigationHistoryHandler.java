package ilink.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;

public class NavigationHistoryHandler {

    private String homePage;
    private StateHistory historyStack;
    private String currentState;

    public NavigationHistoryHandler() {
        homePage = iLinkPropertiesLoader.getApplicationProperty("homePage");
        historyStack = new StateHistory();
        currentState = homePage;
    }

    public String getHomePage() {
        return homePage;
    }

    public String setNavigationState(HttpServletRequest request) throws ServletException {
        String inCurrentState = request.getRequestURI();
        String prevPage = historyStack.getLastState();
        //if current state contains inCurrentState - means it is second request for the same page
        if (!iLinkUtils.isEmpty(currentState) && currentState.startsWith(inCurrentState)) {
            //nothing to do
        } else  //if prev page contains inCurrentState - means "back" was fired - history should go down
            if (!iLinkUtils.isEmpty(prevPage) && prevPage.startsWith(inCurrentState)) {
                currentState = historyStack.popState();
                prevPage = historyStack.getLastState();

            } else //another page was opened - history should go up
            {
                if (!iLinkUtils.isEmpty(currentState) && checkExceptions(currentState, inCurrentState)) {
                    historyStack.pushState(currentState);
                }
                currentState = getCurrentPageWithParams(request);
                prevPage = historyStack.getLastState();
            }

        if (iLinkUtils.isEmpty(prevPage)) {
            prevPage = getHomePage();
        }
        return prevPage;
    }

    // need to implement generic method - for future - case when follow up appt opened from outcome
    protected boolean checkExceptions(String state1, String state2) {
        boolean status = true;
        if (state1.contains("updateMultiOutcome.htm") && state2.contains("newUpdAppt.htm"))
            status = false;
        return status;
    }

    protected String getCurrentPageWithParams(HttpServletRequest request) {
        String currentState = request.getRequestURI();
        String params = request.getQueryString();
        if (!iLinkUtils.isEmpty(params)) {
            int endIndex = params.lastIndexOf("state");
            if (endIndex >= 0) {
                params = params.substring(0, endIndex);
            }
            currentState += "?" + params;
        }

        return currentState;
    }

    public void setNavigationHomePageState() {
        historyStack.clearHistory();
        currentState = getHomePage();
    }
}
