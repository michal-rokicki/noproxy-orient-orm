package pl.megaoak.orientorm;

import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

public class TransactionManager {

    private OPartitionedDatabasePool pool;
    public boolean commitOnSuccessFlag=false;

    public TransactionManager() {
    }

    public TransactionManager(OPartitionedDatabasePool pool) {
        this.pool = pool;
    }

    public <T>T execute(TransactionCallback<T> callback) {
        ODatabaseDocumentTx connection=null;
        try {
            connection = pool.acquire();
            connection.begin();

            T result = callback.call();

            if (commitOnSuccessFlag) {
                connection.commit();
            }
            else {
                connection.rollback();
            }

            return result;
        }
        catch (Throwable t) {
            if (connection!=null) {
                connection.rollback();
            }

            throw new TransactionException("Exception in transaction: ",t);
        }
        finally {
            if (connection!=null) {
                connection.close();
            }
        }
    }

    public void execute(final TransactionTask task) {
        execute(new TransactionCallback<Void>() {
            @Override
            public Void call() throws Exception {
                task.run();
                return null;
            }
        });
    }

    public void commitOnSuccess(boolean commit) {
        commitOnSuccessFlag = commit;
    }

    public void setPool(OPartitionedDatabasePool pool) {
        this.pool = pool;
    }
}
