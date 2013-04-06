package ilink.utils;

import ilink.domain.ExtendedMap;

import java.util.*;

public class PageContext {

    // Special value for initial state
    private static final int NO_TOTAL_COUNT = -1;
    private static final String DEFAULT_KEY = "KEY";

    // The total number of records
    private int totalRecordsNum = NO_TOTAL_COUNT;
    protected int pageSize = 0;
    private int currentPageNum = 0;
    protected int totalPagesNum = 0;
    protected int currentIndex = 0;
    protected int maxRecordsNumber = 0;

    private HashMap lastCriteria = new ExtendedMap();
    private Object[] resultList;

    public void reset() {
        lastCriteria.clear();
        resultList = null;
        currentIndex = 0;
        currentPageNum = 0;
        totalPagesNum = 0;
        totalRecordsNum = NO_TOTAL_COUNT;
    }
    public PageContext(int pageSize, int maxRecordsNumber ){
        this.pageSize = pageSize;
        this.maxRecordsNumber = maxRecordsNumber;
    }
    public void init(String criteria) {
        reset();
        setLastCriteria(criteria);
    }
     public void init(HashMap criteria) {
        reset();
        setLastCriteria(criteria);
    }

    public int getMaxRecordsNumber() {
        return maxRecordsNumber;
    }

    public void setMaxRecordsNumber(int maxRecordsNumber) {
        this.maxRecordsNumber = maxRecordsNumber;
    }

    public int getTotalRecordsNum() {
        return totalRecordsNum;
    }

    public void setTotalRecordsNum(int totalRecordsNum) {
        this.totalRecordsNum = totalRecordsNum;
    }

    public int getTotalPagesNum() {
        return totalPagesNum;
    }

    public void setTotalPagesNum(int totalPagesNum) {
        this.totalPagesNum = totalPagesNum;
    }

    public HashMap getLastCriteria() {
        return lastCriteria;
    }

    public void setLastCriteria(HashMap lastCriteria) {
        this.lastCriteria = lastCriteria;
    }

    public void setLastCriteria(String lastCriteria) {
        this.lastCriteria.put(DEFAULT_KEY,lastCriteria);
    }
    
    public void setLastCriteriaByKey(String key, String lastCriteria) {
        this.lastCriteria.put(key,lastCriteria);
    }

    public String getLastCriteriaStr(){
       return ((ExtendedMap)this.lastCriteria).toStringStr();
    }

    public Object[] getResultList() {
        return resultList;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotlaPagesNum() {
        return totalPagesNum;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public boolean isCriteriaChanged(HashMap criteria) {
        boolean status = true;
        if (lastCriteria != null && criteria != null &&
                (lastCriteria.size() == criteria.size()) && lastCriteria.size() > 0 && lastCriteria.equals(criteria)) {
            status = false;
        }
        return status;
    }

    public boolean isCriteriaChanged(String criteria) {
        boolean status = true;
        if (lastCriteria != null && criteria != null &&
                lastCriteria.size() > 0 && (((String)lastCriteria.get(DEFAULT_KEY)).equals(criteria))) {
            status = false;
        }
        return status;
    }

    @Override
    public String toString() {
        String delimeter = "";
        String resultListStr = "[";
        for (Object it : Arrays.asList(resultList)) {
            resultListStr += delimeter + it.toString();
            delimeter = ",";
        }
        resultListStr += "]";
        return "{" +
                "totalRecordsNum:" + totalRecordsNum +
                ", pageSize:" + pageSize +
                ", currentPageNum:" + currentPageNum +
                ", totalPagesNum:" + totalPagesNum +
                ", currentIndex:" + currentIndex +
                ", maxRecordsNumber:" + maxRecordsNumber +
                ", resultList:" + resultListStr +
                "}";
    }

    protected String toStringResult(String arrayStr) {
        return "{" +
                "totalRecordsNum:" + totalRecordsNum +
                ", pageSize:" + pageSize +
                ", currentPageNum:" + currentPageNum +
                ", totalPagesNum:" + totalPagesNum +
                ", currentIndex:" + currentIndex +
                ", maxRecordsNumber:" + maxRecordsNumber +
                ", resultList:" + arrayStr +
                "}";
    }

    public void setResultList(List list) throws IteratorException {
        if (list != null) {
            this.resultList = list.toArray();
            currentIndex = 0;
            totalRecordsNum = this.resultList.length;
            Double dTotalPagesNum = ((double)totalRecordsNum/(double)this.pageSize) + 0.9;
            totalPagesNum = dTotalPagesNum.intValue();
            currentPageNum = 1;
        } else
            throw new IteratorException("List is empty");
    }

    public List getCurrentPage() throws IteratorException {

        return getElements(pageSize, currentIndex);
    }

    public String getCurrentPageStr() throws IteratorException {

        return toStringResult(getElementsStr(pageSize, currentIndex));
    }

    public List getElements(int count, int index) throws IteratorException {
        LinkedList list = new LinkedList();
        for (int i = 0; i < count && index < totalRecordsNum; i++, index++) {
            list.add(this.resultList[index]);
        }
        return list;
    }

    public String getElementsStr(int count, int index) throws IteratorException {
        String delimeter = "";
        String resultListStr = "[";
        for (int i = 0; i < count && index < totalRecordsNum; i++, index++) {
            resultListStr += delimeter + this.resultList[index].toString();
            delimeter = ",";
        }
        resultListStr += "]";
        return resultListStr;
    }

    public List getPreviousPage() throws IteratorException {
        currentIndex = ((currentIndex - pageSize) < 0) ? 0 : (currentIndex - pageSize);
        currentPageNum = ((currentPageNum - 1) < 0) ? 0 : (currentPageNum - 1);
        return getElements(pageSize, currentIndex);
    }

    public String getPreviousPageStr() throws IteratorException {
        currentIndex = ((currentIndex - pageSize) < 0) ? 0 : (currentIndex - pageSize);
        currentPageNum = ((currentPageNum - 1) < 0) ? 0 : (currentPageNum - 1);
        return toStringResult(getElementsStr(pageSize, currentIndex));
    }

    public List getNextPage() throws IteratorException {
        if (currentIndex + pageSize <= totalRecordsNum) {
            currentIndex = currentIndex + pageSize;
            currentPageNum += 1;
        }
        return getElements(pageSize, currentIndex);
    }

    public String getNextPageStr() throws IteratorException {
        if (currentIndex + pageSize <= totalRecordsNum) {
            currentIndex = currentIndex + pageSize;
            currentPageNum += 1;
        }
        return toStringResult(getElementsStr(pageSize, currentIndex));
    }
}
