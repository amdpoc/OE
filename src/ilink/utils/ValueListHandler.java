package ilink.utils;
import java.util.*;

public class ValueListHandler implements ValueListIterator {

    protected Object[] list;
    protected int currentIndex = 0;
    protected int pageSize = 0;
    protected List<String> lastCriteria = null;
    protected int currentPageNum = 0;
    protected int totalPagesNum = 0;


    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotlaPagesNum() {
        return totalPagesNum;
    }

  public ValueListHandler() {
  }

  public ValueListHandler(int pageSize) {
      this.pageSize = pageSize;
  }

  public void setCriteria(List<String> criteria){
      lastCriteria = criteria;
  }
  public boolean checkCriteria(List<String> criteria){
      boolean status = false;
      if(lastCriteria != null && criteria != null &&
         (lastCriteria.size() == criteria.size()) && lastCriteria.size() > 0 && lastCriteria.containsAll(criteria)){       
        status = true;
      }
      return status;
  }
  public boolean checkCriteria(String criteria){
      boolean status = false;
      if(lastCriteria != null && criteria != null &&
         lastCriteria.size() > 0 && lastCriteria.contains(criteria)){       
        status = true;
      }
      return status;
  }
  public void reset(){
      lastCriteria = null;
      list = null;
      currentIndex = 0;
      currentPageNum = 0;
      totalPagesNum = 0;

  }
  public void setList(List list)throws IteratorException {
    this.list = list.toArray();
    if(list != null){
      currentIndex = 0;
      Double dTotalPagesNum = (new Double(list.size()))/ this.pageSize + 0.9;
      totalPagesNum = dTotalPagesNum.intValue();
    }
    else
      throw new IteratorException("List empty");
  }

  public int getSize() throws IteratorException{
    int size = 0;

    if (list != null)
      size = list.length;
    else
      throw new IteratorException("No Data"); //No Data

    return size;
  }

   public List getPreviousPage() throws IteratorException {
      List prevPage = getPreviousElements(pageSize);
      currentPageNum--;
      return prevPage;
  }
  public List getPreviousElements(int count) throws IteratorException {
    Object object;
    LinkedList list = new LinkedList();
    currentIndex -= (this.pageSize * 2);
    currentIndex = (currentIndex < 0)? 0 : currentIndex;
    for(int i = 0; i < count && currentIndex < this.getSize(); i++ ){
        object = this.list[currentIndex++];
        list.add(object);
    }// end if
    return list;
  }
  public List getNextPage() throws IteratorException {
      List nextPage = getNextElements(pageSize);
      currentPageNum++;
      return  nextPage;
  }
  public List getNextElements(int count) throws IteratorException {
    Object object;
    LinkedList list = new LinkedList();
    for(int i = 0; i < count && currentIndex < this.getSize(); i++ ){
      object = this.list[currentIndex++];
      list.add(object);
    }
    return list;
  }
  public List getCurrentPage() throws IteratorException {
      List currPage = getCurrentElements(pageSize);
      return  currPage;
  }
  public List getCurrentElements(int count) throws IteratorException {
    Object object;
    LinkedList list = new LinkedList();
    currentIndex -= this.pageSize;
    currentIndex = (currentIndex < 0)? 0 : currentIndex;
    for(int i = 0; i < count && currentIndex < this.getSize(); i++ ){
        object = this.list[currentIndex++];
        list.add(object);
    }// end if
    return list;
  }

  public void resetIndex() throws IteratorException{
     this.currentIndex = 0;
     currentPageNum = 0;
  }
}
