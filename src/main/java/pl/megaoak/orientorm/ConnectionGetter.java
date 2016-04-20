package pl.megaoak.orientorm;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;

public class ConnectionGetter {

    static public ODatabaseDocument get() {
        return (ODatabaseDocument) ODatabaseRecordThreadLocal.INSTANCE.get();
    }
}
