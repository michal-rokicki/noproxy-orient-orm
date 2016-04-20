var db = orient.getDatabase();

var first = db.executeCommand("INSERT INTO FieldDemoData SET name = 'first'").getRecord().field('@rid');
var second = db.executeCommand("INSERT INTO FieldDemoData SET name = 'second'").getRecord().field('@rid');
var third = db.executeCommand("INSERT INTO FieldDemoData SET name = 'third'").getRecord().field('@rid');

var fieldDemo=db.executeCommand(
  'insert into FieldDemo content {\
  "binaryField":"binary",\
  "booleanField":"false",\
  "byteField":"123",\
  "dateField":"1992-06-22",\
  "dateTimeField":"1992-06-22 11:32:11",\
  "decimalField":"15.32",\
  "doubleField":"16.33",\
  "embeddedField": {"@type":"d", "@class":"FieldDemoData", "name":"embedded"},\
  "embeddedListField": [{"@type":"d", "@class":"FieldDemoData", "name":"embeddedList0"},{"@type":"d", "@class":"FieldDemoData", "name":"embeddedList1"}],\
  "embeddedMapField": {\
    "key0": {"@type":"d", "@class":"FieldDemoData", "name":"embeddedList0"},\
    "key1": {"@type":"d", "@class":"FieldDemoData", "name":"embeddedList1"}\
  },\
  "embeddedSetField": [{"@type":"d", "@class":"FieldDemoData", "name":"embeddedList0"},{"@type":"d", "@class":"FieldDemoData", "name":"embeddedList1"}],\
  "floatField":"12.53",\
  "integerField":"453",\
  "linkField":"'+first+'",\
  "linkListField":["'+first+'","'+second+'","'+third+'"],\
  "linkMapField":{"key0":"'+first+'","key1":"'+second+'","key2":"'+third+'"},\
  "linkSetField":["'+first+'","'+second+'","'+third+'"],\
  "longField":"23423",\
  "shortField":"23423",\
  "stringField":"stringstring"\
}');

print(first.getRecord().field('@rid'));
