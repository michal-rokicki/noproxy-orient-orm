package pl.megaoak.orientorm.mapping;

import com.google.common.base.Optional;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.joda.time.DateTime;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pl.megaoak.orientorm.converter.DateTimeConverter;
import pl.megaoak.orientorm.converter.GuavaOptionalConverter;
import pl.megaoak.orientorm.model.FieldDemoData;
import pl.megaoak.orientorm.model.FieldDemoOptional;
import pl.megaoak.orientorm.model.WorldMap;
import pl.megaoak.orientorm.util.DocumentContentComparator;
import pl.megaoak.orientorm.util.Resources;

import java.math.BigDecimal;
import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class OrientTypePluginTest {

    OrientMapper mapper;

    @BeforeTest(alwaysRun = true)
    public void setUp() {
        mapper = new OrientMapper(WorldMap.class.getPackage());
        mapper.addTypeConverter(Optional.class, new GuavaOptionalConverter());
        mapper.addTypeConverter(DateTime.class, new DateTimeConverter());
    }

    @Test
    public void shouldParseOptional() {
        ODocument doc = Resources.documentFromFile("field-demo.json");

        FieldDemoOptional fieldDemo = mapper.toObject(doc, FieldDemoOptional.class);

        assertEquals(fieldDemo.getId(), "#14:10");
        assertEquals(fieldDemo.getVersion(), 210);

        assertEquals(fieldDemo.getBinaryField().get(), "majoneza".getBytes());
        assertEquals(fieldDemo.getBooleanField().get().booleanValue(), true);
        assertEquals(fieldDemo.getByteField().get().byteValue(), 123);
        assertEquals(fieldDemo.getDateField().get().getYear(), 1992);
        assertEquals(fieldDemo.getDateTimeField().getYear(), 1992);
        assertEquals(fieldDemo.getDecimalField(), new BigDecimal("15.32"));
        assertEquals(fieldDemo.getDoubleField(), 16.33);
        assertEquals(fieldDemo.getEmbeddedField().get().getName(), "embedded");

        assertEquals(fieldDemo.getEmbeddedListField().get().size(), 2);
        assertEquals(fieldDemo.getEmbeddedListField().get().get(0).getName(), "embeddedList0");
        assertEquals(fieldDemo.getEmbeddedListField().get().get(1).getName(), "embeddedList1");

        assertEquals(fieldDemo.getEmbeddedMapField().size(), 2);
        assertEquals(fieldDemo.getEmbeddedMapField().get("key0").getName(), "embeddedList0");
        assertEquals(fieldDemo.getEmbeddedMapField().get("key1").getName(), "embeddedList1");

        assertEquals(fieldDemo.getEmbeddedSetField().size(), 2);
        assertTrue(fieldDemo.getEmbeddedSetField().contains(new FieldDemoData("embeddedList0")));
        assertTrue(fieldDemo.getEmbeddedSetField().contains(new FieldDemoData("embeddedList1")));

        assertEquals(fieldDemo.getFloatField(), 12.53f);
        assertEquals(fieldDemo.getIntegerField(), 453);

        assertEquals(fieldDemo.getLinkField().get().toString(), "#15:1");

        assertEquals(fieldDemo.getLinkListField().get().size(), 3);
        assertEquals(fieldDemo.getLinkListField().get().get(0).toString(), "#15:1");
        assertEquals(fieldDemo.getLinkListField().get().get(1).toString(), "#15:2");
        assertEquals(fieldDemo.getLinkListField().get().get(2).toString(), "#15:3");

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
    public void shouldReadAndWriteWithPlugins() {
        //given
        ODocument doc = Resources.documentFromFile("field-demo.json");

        //when
        FieldDemoOptional fieldDemo = mapper.toObject(doc, FieldDemoOptional.class);
        ODocument doc2 = mapper.toDocument(fieldDemo);

        //then
        assertEquals(doc2.field("dateField").getClass(), Date.class);
        assertNotNull(doc2.field("dateField"));

        assertEquals(((ODocument)doc2.field("embeddedField")).field("name"), "embedded");

        assertTrue(DocumentContentComparator.equals(doc2, doc));
    }
}
