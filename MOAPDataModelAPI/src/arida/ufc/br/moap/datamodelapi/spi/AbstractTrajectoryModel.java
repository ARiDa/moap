
package arida.ufc.br.moap.datamodelapi.spi;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author igobrilhante
 */
public abstract class AbstractTrajectoryModel<S, T> implements ITrajectoryModel {
    protected final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void readLock() {
        this.readWriteLock.readLock().lock();
    }


    public void readUnLock() {
        this.readWriteLock.readLock().unlock();
    }


    public void readUnlockAll() {
        ReentrantReadWriteLock lock = readWriteLock;
        final int nReadLocks = lock.getReadHoldCount();
        for (int n = 0; n < nReadLocks; n++) {
            lock.readLock().unlock();
        }
    }


    public void writeLock() {
        if (readWriteLock.getReadHoldCount() > 0) {
            throw new IllegalMonitorStateException("Impossible to acquire a write lock when currently holding a read lock. Use toArray() methods on NodeIterable and EdgeIterable to avoid holding a readLock.");
        }

        readWriteLock.writeLock().lock();
    }

    /**
     *
     */
    public void writeUnlock() {

        readWriteLock.writeLock().unlock();
    }
}
