package scovmod.model.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.util.concurrent.Futures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BufferedIterator<E> implements Iterator<E> {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());    

    private Iterator<E> innerIt;
    private Integer chunkSize;

    private List<Queue<E>> buffers = new ArrayList<>();

    private AtomicBoolean readyToFlip = new AtomicBoolean(false);
    private AtomicInteger readBuffer = new AtomicInteger(0);
    private Future<?> loadFuture = Futures.immediateFuture(null);

    private static ExecutorService executor;

    public static <E> BufferedIterator<E> createWithSharedExecutor(Iterator<E> it, int chunkSize) {
        if (executor == null) {
            executor = Executors.newSingleThreadExecutor((Runnable r) -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            });
        }

        return new BufferedIterator<>(it, chunkSize, executor);
    }

    public BufferedIterator(Iterator<E> innerIt, Integer chunkSize, ExecutorService executor0) {
        this.innerIt = innerIt;
        this.chunkSize = chunkSize;

        buffers.add(new LinkedList<>());
        buffers.add(new LinkedList<>());

        executor = executor0;
        
        readBuffer.set(0);
        backgroundLoad();
    }

    @Override
    public boolean hasNext() {
        //log.debug("In BufferedIterator hasNext()");
        return moreInCurrentBuffer() || readyToFlip();
    }

    @Override
    public E next() {
		//Not thread safe
        //log.debug("In BufferedIterator next()");		
        if (moreInCurrentBuffer()) {
            return nextFromBuffer();
        } else if (readyToFlip()) {
            flipBuffers();
            return next();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private void flipBuffers() {
        readyToFlip.set(false);
        int oldReadBufferId = readBuffer.get();
        int newReadBufferId = oldReadBufferId == 0 ? 1 : 0;
        readBuffer.compareAndSet(oldReadBufferId, newReadBufferId);
        backgroundLoad();
    }

    private void backgroundLoad() {
        assert (loadFuture.isDone());

        loadFuture = executor.submit(() -> {
            int writeBufferIdx = readBuffer.get() == 0 ? 1 : 0;
            Queue<E> writeBuffer = buffers.get(writeBufferIdx);
            assert (writeBuffer.size() == 0);
            
            for (int i = 0; i < chunkSize; i++) {
                boolean hasNext = innerIt.hasNext();
                if (!hasNext) {
                    break;
                }
                writeBuffer.add(innerIt.next());
            }
            if (writeBuffer.size() > 0) {
                readyToFlip.set(true);
            }
        });
    }

    private boolean moreInCurrentBuffer() {
        return buffers.get(readBuffer.get()).size() > 0;
    }

    private E nextFromBuffer() {
        return buffers.get(readBuffer.get()).remove();
    }

    private boolean readyToFlip() {
        try {
            loadFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Buffering future thew exception", e);
        }
        return readyToFlip.get();
    }
}
