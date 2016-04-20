package pl.megaoak.orientorm;

import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.megaoak.orientorm.mapping.OrientMapper;
import pl.megaoak.orientorm.model.FieldDemo;
import pl.megaoak.orientorm.model.FieldDemoFull;
import pl.megaoak.orientorm.model.FieldDemoStringRids;
import pl.megaoak.orientorm.model.WorldMap;

import java.io.IOException;
import java.util.List;

public class TransactionManagerIntegrationTest {

    private TransactionManager transactionManager;
    private OPartitionedDatabasePool oPartitionedDatabasePool;
    OrientMapper mapper = new OrientMapper(WorldMap.class.getPackage());

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        //oPartitionedDatabasePool = new OPartitionedDatabasePool("remote:localhost/mongols", "root", "ultralisk", 10);
        oPartitionedDatabasePool = new OPartitionedDatabasePool("remote:localhost/testing", "root", "ultralisk");
        transactionManager = new TransactionManager(oPartitionedDatabasePool);
        transactionManager.commitOnSuccess(false);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        oPartitionedDatabasePool.close();
    }

    @Test(enabled=false)
    public void shouldInsertUpdateWithMapping() throws IOException {
        long insertedCount = transactionManager.execute(new TransactionCallback<Long>() {
            @Override
            public Long call() throws Exception {
                String rid = "#13:0";
                ODatabaseDocument connection = ConnectionGetter.get();
                //noinspection unchecked
                List<ODocument> result = connection.query(
                        new OSQLSynchQuery<ODocument>("select * from FieldDemo where @rid=?")
                                .setFetchPlan("*:0"), rid);

                ODocument doc = result.get(0);

                FieldDemo obj = mapper.toObject(doc, FieldDemo.class);

                obj.setStringField(""+Math.random());

                long before = connection.countClass("FieldDemo");

                ODocument doc2 = mapper.toDocument(obj);
                doc2.save();

                obj.setId(null);
                ODocument doc3 = mapper.toDocument(obj);
                doc3.save();

                long after = connection.countClass("FieldDemo");

                return after-before;
            }
        });

        Assert.assertEquals(insertedCount, 1);
    }

    @Test(enabled=false)
    public void shouldSelectFull() throws IOException {
        transactionManager.execute(new TransactionTask() {
            @Override
            public void run() throws Exception {
                ODatabaseDocument connection = ConnectionGetter.get();
                //noinspection unchecked
                List<ODocument> result = connection.query(
                        new OSQLSynchQuery<ODocument>("select * from FieldDemo")
                                .setFetchPlan("*:0"));

                ODocument doc = result.get(0);
                FieldDemoFull obj = mapper.toObject(doc, FieldDemoFull.class);
            }
        });
    }

    @Test(enabled=false)
    public void shouldSelectStrings() throws IOException {
        transactionManager.execute(new TransactionCallback<FieldDemoStringRids>() {
            @Override
            public FieldDemoStringRids call() throws Exception {
                ODatabaseDocument connection = ConnectionGetter.get();
                //noinspection unchecked
                List<ODocument> result = connection.query(
                        new OSQLSynchQuery<ODocument>("select * from FieldDemo")
                                .setFetchPlan("*:0"));

                ODocument doc = result.get(0);
                return mapper.toObject(doc, FieldDemoStringRids.class);
            }
        });
    }
}
