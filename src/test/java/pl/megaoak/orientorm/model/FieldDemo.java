package pl.megaoak.orientorm.model;

import com.orientechnologies.orient.core.id.ORecordId;
import pl.megaoak.orientorm.annotation.ID;
import pl.megaoak.orientorm.annotation.OrientClass;
import pl.megaoak.orientorm.annotation.OrientIgnore;
import pl.megaoak.orientorm.annotation.Version;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@OrientClass
public class FieldDemo {
	@ID
	private String id;

	@Version
	private int version;

	private byte[] binaryField;
	private boolean booleanField;
	private byte byteField;
	private Date dateField;
	private Date dateTimeField;
	private BigDecimal decimalField;
	private double doubleField;
	private FieldDemoData embeddedField;
	private List<FieldDemoData> embeddedListField;
	private Map<String, FieldDemoData> embeddedMapField;
	private Set<FieldDemoData> embeddedSetField;
	private float floatField;
	private int integerField;
	private ORecordId linkField;
	private List<ORecordId> linkListField;
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

	public byte[] getBinaryField() {
		return binaryField;
	}

	public void setBinaryField(byte[] binaryField) {
		this.binaryField = binaryField;
	}

	public boolean isBooleanField() {
		return booleanField;
	}

	public void setBooleanField(boolean booleanField) {
		this.booleanField = booleanField;
	}

	public byte getByteField() {
		return byteField;
	}

	public void setByteField(byte byteField) {
		this.byteField = byteField;
	}

	public Date getDateField() {
		return dateField;
	}

	public void setDateField(Date dateField) {
		this.dateField = dateField;
	}

	public Date getDateTimeField() {
		return dateTimeField;
	}

	public void setDateTimeField(Date dateTimeField) {
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

	public FieldDemoData getEmbeddedField() {
		return embeddedField;
	}

	public void setEmbeddedField(FieldDemoData embeddedField) {
		this.embeddedField = embeddedField;
	}

	public List<FieldDemoData> getEmbeddedListField() {
		return embeddedListField;
	}

	public void setEmbeddedListField(List<FieldDemoData> embeddedListField) {
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

	public ORecordId getLinkField() {
		return linkField;
	}

	public void setLinkField(ORecordId linkField) {
		this.linkField = linkField;
	}

	public List<ORecordId> getLinkListField() {
		return linkListField;
	}

	public void setLinkListField(List<ORecordId> linkListField) {
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
