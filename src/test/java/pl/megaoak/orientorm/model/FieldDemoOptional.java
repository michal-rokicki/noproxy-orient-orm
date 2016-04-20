package pl.megaoak.orientorm.model;

import com.google.common.base.Optional;
import com.orientechnologies.orient.core.id.ORecordId;
import org.joda.time.DateTime;
import pl.megaoak.orientorm.annotation.ID;
import pl.megaoak.orientorm.annotation.OrientClass;
import pl.megaoak.orientorm.annotation.Version;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@OrientClass("FieldDemo")
public class FieldDemoOptional {
    @ID
    private String id;

    @Version
    private int version;

    private Optional<byte[]> binaryField=Optional.absent();
    private Optional<Boolean> booleanField=Optional.absent();
    private Optional<Byte> byteField=Optional.absent();
    private Optional<DateTime> dateField=Optional.absent();
    private DateTime dateTimeField;
    private BigDecimal decimalField;
    private double doubleField;
    private Optional<FieldDemoData> embeddedField;
    private Optional<List<FieldDemoData>> embeddedListField;
    private Map<String, FieldDemoData> embeddedMapField;
    private Set<FieldDemoData> embeddedSetField;
    private float floatField;
    private int integerField;
    private Optional<ORecordId> linkField;
    private Optional<List<ORecordId>> linkListField;
    private Map<String, ORecordId> linkMapField;
    private Set<ORecordId> linkSetField;
    private long longField;
    private short shortField;
    private String stringField;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Optional<byte[]> getBinaryField() {
        return binaryField;
    }

    public void setBinaryField(Optional<byte[]> binaryField) {
        this.binaryField = binaryField;
    }

    public Optional<Boolean> getBooleanField() {
        return booleanField;
    }

    public void setBooleanField(Optional<Boolean> booleanField) {
        this.booleanField = booleanField;
    }

    public Optional<Byte> getByteField() {
        return byteField;
    }

    public void setByteField(Optional<Byte> byteField) {
        this.byteField = byteField;
    }

    public Optional<DateTime> getDateField() {
        return dateField;
    }

    public void setDateField(Optional<DateTime> dateField) {
        this.dateField = dateField;
    }

    public DateTime getDateTimeField() {
        return dateTimeField;
    }

    public void setDateTimeField(DateTime dateTimeField) {
        this.dateTimeField = dateTimeField;
    }

    public BigDecimal getDecimalField() {
        return decimalField;
    }

    public void setDecimalField(BigDecimal decimalField) {
        this.decimalField = decimalField;
    }

    public double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(double doubleField) {
        this.doubleField = doubleField;
    }

    public Optional<FieldDemoData> getEmbeddedField() {
        return embeddedField;
    }

    public void setEmbeddedField(Optional<FieldDemoData> embeddedField) {
        this.embeddedField = embeddedField;
    }

    public Optional<List<FieldDemoData>> getEmbeddedListField() {
        return embeddedListField;
    }

    public void setEmbeddedListField(Optional<List<FieldDemoData>> embeddedListField) {
        this.embeddedListField = embeddedListField;
    }

    public Map<String, FieldDemoData> getEmbeddedMapField() {
        return embeddedMapField;
    }

    public void setEmbeddedMapField(Map<String, FieldDemoData> embeddedMapField) {
        this.embeddedMapField = embeddedMapField;
    }

    public Set<FieldDemoData> getEmbeddedSetField() {
        return embeddedSetField;
    }

    public void setEmbeddedSetField(Set<FieldDemoData> embeddedSetField) {
        this.embeddedSetField = embeddedSetField;
    }

    public float getFloatField() {
        return floatField;
    }

    public void setFloatField(float floatField) {
        this.floatField = floatField;
    }

    public int getIntegerField() {
        return integerField;
    }

    public void setIntegerField(int integerField) {
        this.integerField = integerField;
    }

    public Optional<ORecordId> getLinkField() {
        return linkField;
    }

    public void setLinkField(Optional<ORecordId> linkField) {
        this.linkField = linkField;
    }

    public Optional<List<ORecordId>> getLinkListField() {
        return linkListField;
    }

    public void setLinkListField(Optional<List<ORecordId>> linkListField) {
        this.linkListField = linkListField;
    }

    public Map<String, ORecordId> getLinkMapField() {
        return linkMapField;
    }

    public void setLinkMapField(Map<String, ORecordId> linkMapField) {
        this.linkMapField = linkMapField;
    }

    public Set<ORecordId> getLinkSetField() {
        return linkSetField;
    }

    public void setLinkSetField(Set<ORecordId> linkSetField) {
        this.linkSetField = linkSetField;
    }

    public long getLongField() {
        return longField;
    }

    public void setLongField(long longField) {
        this.longField = longField;
    }

    public short getShortField() {
        return shortField;
    }

    public void setShortField(short shortField) {
        this.shortField = shortField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }
}
