package ilink.utils;

import java.util.Stack;


public class StateHistory implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    // Contains states history. Implemented as a Stack (LIFO) of states
    private Stack historyStack = new Stack();

    public void clearHistory() {
        historyStack.clear();
    }

    //Return history stack top without removing it from the stack
    public String getLastState() {
        String lastState = null;
        if (!historyStack.isEmpty())
            lastState = (String) historyStack.peek();
        return lastState;
    }

    //Remove last (top) state from the history's stack and returns it.
    public String popState() {
        String state = null;
        if (!historyStack.isEmpty())
            state = (String) historyStack.pop();
        return state;
    }

    //Pushes it onto the top of the stack.
    public void pushState(String i_state) {
        historyStack.push(i_state);
    }

    //Returns true iff the history is empty
    public boolean isEmpty() {
        return historyStack.isEmpty();
    }

    public String toString() {
        return historyStack.toString();
    }

    public Stack getHistoryStack() {
        return this.historyStack;
    }

    // Returns a copy of this StateHistory
    public StateHistory copy() {
        StateHistory newStateHistory = new StateHistory();
        Object[] entries = this.historyStack.toArray();
        for (int i = 0; i < entries.length; i++) {
            newStateHistory.pushState((String) entries[i]);
        }
        return newStateHistory;
    }

}