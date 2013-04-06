package ilink.utils;

public class PaginationContext {

	// Special value for initial state
	private static final int NO_TOTAL_COUNT = -1;

	// Start on first page
	private int skipResults = 0;

	// Display 10 results per page
	private int maxResults = 10;

	// The total number of records
	private int totalCount = NO_TOTAL_COUNT;

    private int currentPageNumber = 0;


    public void reset(){
      skipResults = 0;
      maxResults = 10;
      totalCount = NO_TOTAL_COUNT;
      currentPageNumber = 0;
    }

    public int getTotalPagesNumber(){
     Double dTotalPagesNum = (new Double(totalCount))/ maxResults + 0.9;
     return dTotalPagesNum.intValue();  
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }
	public int getSkipResults() {
		return skipResults;
	}

	public void setSkipResults(int skipResults) {
		assert skipResults >= 0;
		this.skipResults = skipResults;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		assert maxResults >= 0;
		this.maxResults = maxResults;
	}

	public void updateTotalCount(int totalCount) {
		assert totalCount >= 0;
		this.totalCount = totalCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public boolean hasMorePages() {
		if (!hasTotalCount())
			// No totalCount yet, so display first page
			return true;
		if (skipResults + maxResults > totalCount)
			return false;
		return true;
	}

	public void nextPage() {
        if(hasMorePages() && currentPageNumber > 0){
		   skipResults += maxResults;
        }
        currentPageNumber++;
	}
    public void prevPage() {
		skipResults -= maxResults;
        if(skipResults  < 0){
           skipResults = 0;
        }
        currentPageNumber--;
         if(currentPageNumber  < 0){
            currentPageNumber = 0;
         }
	}
   	public boolean hasTotalCount() {
		return totalCount != NO_TOTAL_COUNT;
	}

}
