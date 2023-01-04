package com.test.kopnuspos.exceptions;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectExecutionHandlerImpl implements RejectedExecutionHandler{

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		
		try {
            executor.getQueue().put(r);
        }
        catch (InterruptedException e) {
        	throw new RejectedExecutionException("Unexpected InterruptedException while waiting to add Runnable to ThreadPoolExecutor queue...", e);
        }
		
	}

}
