package ilink.domain;

import java.util.List;

public class MultiPagesContainer {
    List currentPageList;
    int  totalPageNumber = 0;
    int  currentPageNumber = 0;

    public MultiPagesContainer(){}
    public MultiPagesContainer(int totalPageNumber, int currentPageNumber, List currentPageList){
        this.totalPageNumber = totalPageNumber;
        this.currentPageNumber = currentPageNumber;
        this.currentPageList = currentPageList;
    }
    public int getTotalPageNumber() {
        return totalPageNumber;
    }

    public void setTotalPageNumber(int totalPageNumber) {
        this.totalPageNumber = totalPageNumber;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public List getCurrentPageList() {
        return currentPageList;
    }

    public void setCurrentPageList(List currentPageList) {
        this.currentPageList = currentPageList;
    }
}
