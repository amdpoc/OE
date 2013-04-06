package ilink.utils;

import java.util.List;

public interface ValueListIterator {

  public int getSize() throws IteratorException;

  public List getPreviousElements(int count) throws IteratorException;

  public List getNextElements(int count) throws IteratorException;

  public void resetIndex()throws IteratorException;

}
