package pl.megaoak.orientorm.mapping;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import junit.framework.Assert;
import org.testng.annotations.Test;
import pl.megaoak.orientorm.model.*;
import pl.megaoak.orientorm.util.DocumentContentComparator;
import pl.megaoak.orientorm.util.Resources;

import java.math.BigDecimal;

import static org.testng.Assert.*;

public class OrientMapperTest {

    OrientMapper mapper = new OrientMapper(WorldMap.class.getPackage());

    @Test
    public void shouldParseWorldMap() {
        ODocument doc = Resources.documentFromFile("world-map.json");

        WorldMap worldMap = mapper.toObject(doc, WorldMap.class);

        assertEquals(worldMap.getId().toString(), "#12:0");
        assertEquals(worldMap.getWidth(), 3);
        assertEquals(worldMap.getHeight(), 2);
        assertEquals(worldMap.getName(), "test");
        assertEquals(worldMap.getFields().size(), 6);
        assertEquals(worldMap.getFields().get(0).getTerrainId(), "FOREST1");
        assertEquals(worldMap.getFields().get(1).getTerrainId(), "HILLS");
        assertEquals(worldMap.getFields().get(2).getTerrainId(), "PLAINS0");
        assertEquals(worldMap.getFields().get(3).getTerrainId(), "SEA0");
        assertEquals(worldMap.getFields().get(4).getTerrainId(), "PLAINS0");
        assertEquals(worldMap.getFields().get(5).getTerrainId(), "PLAINS0");
    }

    @Test
    public void shouldParseFieldDemo() {
        ODocument doc = Resources.documentFromFile("field-demo.json");

        FieldDemo fieldDemo = mapper.toObject(doc, FieldDemo.class);

        assertEquals(fieldDemo.getId(), "#14:10");
        assertEquals(fieldDemo.getVersion(), 210);

        assertEquals(fieldDemo.getBinaryField(), "majoneza".getBytes());
        assertEquals(fieldDemo.isBooleanField(), true);
        assertEquals(fieldDemo.getByteField(), 123);
        assertNotNull(fieldDemo.getDateField());
        assertNotNull(fieldDemo.getDateTimeField());
        assertEquals(fieldDemo.getDecimalField(), new BigDecimal("15.32"));
        assertEquals(fieldDemo.getDoubleField(), 16.33);
        assertEquals(fieldDemo.getEmbeddedField().getName(), "embedded");

        assertEquals(fieldDemo.getEmbeddedListField().size(),2);
        assertEquals(fieldDemo.getEmbeddedListField().get(0).getName(), "embeddedList0");
        assertEquals(fieldDemo.getEmbeddedListField().get(1).getName(), "embeddedList1");

        assertEquals(fieldDemo.getEmbeddedMapField().size(), 2);
        assertEquals(fieldDemo.getEmbeddedMapField().get("key0").getName(), "embeddedList0");
        assertEquals(fieldDemo.getEmbeddedMapField().get("key1").getName(), "embeddedList1");

        assertEquals(fieldDemo.getEmbeddedSetField().size(),2);
        assertTrue(fieldDemo.getEmbeddedSetField().contains(new FieldDemoData("embeddedList0")));
        assertTrue(fieldDemo.getEmbeddedSetField().contains(new FieldDemoData("embeddedList1")));

        assertEquals(fieldDemo.getFloatField(), 12.53f);
        assertEquals(fieldDemo.getIntegerField(), 453);

        assertEquals(fieldDemo.getLinkField().toString(), "#15:1");

        assertEquals(fieldDemo.getLinkListField().size(), 3);
        assertEquals(fieldDemo.getLinkListField().get(0).toString(), "#15:1");
        assertEquals(fieldDemo.getLinkListField().get(1).toString(), "#15:2");
        assertEquals(fieldDemo.getLinkListField().get(2).toString(), "#15:3");

        assertEquals(fieldDemo.getLinkMapField().size(), 3);
        assertEquals(fieldDemo.getLinkMapField().get("key0").toString(), "#15:1");
        assertEquals(fieldDemo.getLinkMapField().get("key1").toString(), "#15:2");
        assertEquals(fieldDemo.getLinkMapField().get("key2").toString(), "#15:3");

        assertEquals(fieldDemo.getLinkSetField().size(), 3);
        assertTrue(fieldDemo.getLinkSetField().contains(new ORecordId("#15:1")));
        assertTrue(fieldDemo.getLinkSetField().contains(new ORecordId("#15:2")));
        assertTrue(fieldDemo.getLinkSetField().contains(new ORecordId("#15:3")));

        assertEquals(fieldDemo.getLongField(), 234232342342323L);
        assertEquals(fieldDemo.getShortField(), 23423);
        assertEquals(fieldDemo.getStringField(), "stringstring");
    }

    @Test
    public void shouldIgnoreIgnoredFields() {
        ODocument doc = Resources.documentFromFile("ignored.json");

        IgnoredData data = mapper.toObject(doc, IgnoredData.class);

        assertEquals(data.getSomeField(), "test");
        Assert.assertNull(data.getIgnoredField());
        Assert.assertNull(data.getIgnoredField2());
    }

    @Test
    public void shouldUseValidOrientName() {
        ODocument doc = Resources.documentFromFile("renamed.json");

        RenamedEntity data = mapper.toObject(doc, RenamedEntity.class);

        assertEquals(data.getName(), "test");
    }

    @Test
    public void shouldUseValidPropertyNames() {
        ODocument doc = Resources.documentFromFile("renamed-fields.json");

        RenamedFields data = mapper.toObject(doc, RenamedFields.class);

        assertEquals(data.getName(), "test");
        assertEquals(data.getRenamedFieldXXX(), "asd");
        assertEquals(data.getRenamedField2(), "zxc");
    }

    @Test
    public void shouldReadAndWriteFieldDemo() {
        //given
        ODocument doc = Resources.documentFromFile("field-demo.json");

        //when
        FieldDemo fieldDemo = mapper.toObject(doc, FieldDemo.class);
        ODocument doc2 = mapper.toDocument(fieldDemo);

        //then
        assertTrue(DocumentContentComparator.equals(doc2, doc));
    }
}
