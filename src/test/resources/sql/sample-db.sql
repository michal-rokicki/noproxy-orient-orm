drop class FieldDemoData unsafe;
drop class FieldDemo unsafe;

create class FieldDemoData extends V;

create PROPERTY FieldDemoData.name STRING;
ALTER PROPERTY FieldDemoData.name MANDATORY TRUE;
ALTER PROPERTY FieldDemoData.name NOTNULL TRUE;

create class FieldDemo extends V;

create PROPERTY FieldDemo.binaryField binary;

create PROPERTY FieldDemo.booleanField boolean;

create PROPERTY FieldDemo.byteField byte;

create PROPERTY FieldDemo.dateField DATE;

create PROPERTY FieldDemo.dateTimeField DATETIME;

create PROPERTY FieldDemo.decimalField DOUBLE;

create PROPERTY FieldDemo.embeddedField EMBEDDED FieldDemoData;

create PROPERTY FieldDemo.embeddedListField EMBEDDEDLIST FieldDemoData;

create PROPERTY FieldDemo.embeddedMapField EMBEDDEDMAP FieldDemoData;

create PROPERTY FieldDemo.embeddedSetField EMBEDDEDSET FieldDemoData;

create PROPERTY FieldDemo.floatField FLOAT;

create PROPERTY FieldDemo.integerField INTEGER;

create PROPERTY FieldDemo.linkField LINK FieldDemoData;

create PROPERTY FieldDemo.linkListField LINKLIST FieldDemoData;

create PROPERTY FieldDemo.linkMapField LINKMAP FieldDemoData;

create PROPERTY FieldDemo.linkSetField LINKSET FieldDemoData;

create PROPERTY FieldDemo.longField LONG;

create PROPERTY FieldDemo.shortField SHORT;

create PROPERTY FieldDemo.stringField STRING;

let first = INSERT INTO FieldDemoData SET name = 'first';
let second = INSERT INTO FieldDemoData SET name = 'second';
let third = INSERT INTO FieldDemoData SET name = 'third';

insert into FieldDemo SET linkField=$third;

insert into FieldDemo SET linkField=$third, stringField='xxx', linkListField=UNIONALL($first,$second,$third);

insert into FieldDemo SET
    binaryField="bWFqb25lemE=",
	booleanField="false",
	byteField="123",
	dateField="1992-06-22",
	dateTimeField="1992-06-22 11:32:11",
	decimalField="15.32",
	doubleField="16.33",
	embeddedField={"@type":"d", "@class":"FieldDemoData", "name":"embedded"},
	embeddedListField=[{"@type":"d", "@class":"FieldDemoData", "name":"embeddedList0"},{"@type":"d", "@class":"FieldDemoData", "name":"embeddedList1"}],
	embeddedMapField={
		"key0": {"@type":"d", "@class":"FieldDemoData", "name":"embeddedList0"},
		"key1": {"@type":"d", "@class":"FieldDemoData", "name":"embeddedList1"}
	},
	embeddedSetField=[{"@type":"d", "@class":"FieldDemoData", "name":"embeddedList0"},{"@type":"d", "@class":"FieldDemoData", "name":"embeddedList1"}],
	floatField="12.53",
	integerField="453",
	linkField="#15:1",
	linkListField=["#15:1","#15:2","#15:3"],
	linkMapField={"key0":"#15:1","key1":"#15:2","key2":"#15:3"},
	linkSetField=["#15:1","#15:2","#15:3"],
	longField="23423",
	shortField="23423",
	stringField="stringstring";



insert into FieldDemo SET
    binaryField="bWFqb25lemE=",
	booleanField="false",
	byteField="123",
	dateField="1992-06-22",
	dateTimeField="1992-06-22 11:32:11",
	decimalField="15.32",
	doubleField="16.33",
	embeddedField={"@type":"d", "@class":"FieldDemoData", "name":"embedded"},
	embeddedListField=[{"@type":"d", "@class":"FieldDemoData", "name":"embeddedList0"},{"@type":"d", "@class":"FieldDemoData", "name":"embeddedList1"}],
	embeddedMapField={
		"key0": {"@type":"d", "@class":"FieldDemoData", "name":"embeddedList0"},
		"key1": {"@type":"d", "@class":"FieldDemoData", "name":"embeddedList1"}
	},
	embeddedSetField=[{"@type":"d", "@class":"FieldDemoData", "name":"embeddedList0"},{"@type":"d", "@class":"FieldDemoData", "name":"embeddedList1"}],
	floatField="12.53",
	integerField="453",
	linkField="#15:1",
	linkListField=["#15:1","#15:2","#15:3"],
	linkMapField={"key0":"#15:1","key1":"#15:2","key2":"#15:3"},
	linkSetField=["#15:1","#15:2","#15:3"],
	longField="23423",
	shortField="23423",
	stringField="stringstring"
};



insert into FieldDemo content {
    "binaryField":"binary",
	"booleanField":"false",
	"byteField":"123",
	"dateField":"1992-06-22",
	"dateTimeField":"1992-06-22 11:32:11",
	"decimalField":"15.32",
	"doubleField":"16.33",
	"embeddedField": {"@type":"d", "@class":"FieldDemoData", "name":"embedded"},
	"embeddedListField": [{"@type":"d", "@class":"FieldDemoData", "name":"embeddedList0"},{"@type":"d", "@class":"FieldDemoData", "name":"embeddedList1"}],
	"embeddedMapField": {
		"key0": {"@type":"d", "@class":"FieldDemoData", "name":"embeddedList0"},
		"key1": {"@type":"d", "@class":"FieldDemoData", "name":"embeddedList1"}
	},
	"embeddedSetField": [{"@type":"d", "@class":"FieldDemoData", "name":"embeddedList0"},{"@type":"d", "@class":"FieldDemoData", "name":"embeddedList1"}],
	"floatField":"12.53",
	"integerField":"453",
	"linkField":"#15:1",
	"linkListField":["#15:1","#15:2","#15:3"],
	"linkMapField":{"key0":"#15:1","key1":"#15:2","key2":"#15:3"},
	"linkSetField":["#15:1","#15:2","#15:3"],
	"longField":"23423",
	"shortField":"23423",
	"stringField":"stringstring"
};