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

create PROPERTY FieldDemo.decimalField DECIMAL;

create PROPERTY FieldDemo.doubleField DOUBLE;

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

select createDBJS();