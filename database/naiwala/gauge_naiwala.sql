PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "user" (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL,
	`username`	TEXT NOT NULL,
	`password`	TEXT NOT NULL
);
INSERT INTO user VALUES(1,'Admin','admin','secret');
INSERT INTO user VALUES(2,'Erantha','erantha','3492');
INSERT INTO user VALUES(3,'Deepal','deepal','3272');
INSERT INTO user VALUES(4,'Sameera','sameera','2604');
INSERT INTO user VALUES(5,'Chaminda','chaminda','1762');
INSERT INTO user VALUES(6,'Ranjana','ranjana','1763');
INSERT INTO user VALUES(7,'Ravindra','ravindra','2869');
INSERT INTO user VALUES(8,'Charith','charith','3359');
CREATE TABLE IF NOT EXISTS "domain" (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`title`	TEXT NOT NULL,
	`description`	TEXT NOT NULL,
	`first_step`	INTEGER NOT NULL,
	`report_description`	texT
);
INSERT INTO domain VALUES(1,'GENERATOR ROOM','Measurements of generator room',1,'GENERATOR ROOM');
CREATE TABLE IF NOT EXISTS "transaction" (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`domain_id`	INTEGER NOT NULL,
	`domain_name`	TEXT NOT NULL,
	`report_description`	teXT,
	`current_step_id`	INTEGER,
	`date`	teXT,
	`start_time`	TEXT,
	`end_time`	BLOB,
	`user_name`	TEXT NOT NULL,
	`user_id`	INTEGER NOT NULL,
	`status`	TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS "transaction_detail" (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`transaction`	INTEGER NOT NULL,
	`main_group`	text,
	`sub_group`	text,
	`domain_step`	INTEGER NOT NULL,
	`number`	text,
	`description`	TEXT NOT NULL,
	`value`	TEXT NOT NULL,
	`unit`	texT,
	`default_value`	text,
	`comment`	TEXT,
	`warning`	INTEGER NOT NULL,
	`time`	text
);
CREATE TABLE [domain_step] (
[id] INTEGER  NULL,
[domain_id] INTEGER  NOT NULL,
[number] TEXT  NULL,
[title] TEXT  NOT NULL,
[main_group] teXT  NULL,
[sub_group] teXT  NULL,
[visual_type] TEXT  NOT NULL,
[visual_link] TEXT  NULL,
[visual_description] TEXT  NOT NULL,
[next_step] INTEGER  NULL,
[show_map] INTEGER  NOT NULL,
[last_step] INTEGER  NOT NULL,
[map_from_previous_link] TEXT  NULL,
[enable_skip] INTEGER  NOT NULL,
[skip_to_step] INTEGER  NOT NULL,
[map_from_beginning_link] TEXT  NULL,
[value_type] TEXT  NOT NULL,
[value_hint] TEXT  NULL,
[unit] TEXT  NULL,
[default_value] TEXT  NULL,
[default_from_value] TEXT  NULL,
[default_to_value] TEXT  NULL,
[report_description] TEXT  NULL
);
INSERT INTO domain_step VALUES(1,1,'','WELCOME','GENERATOR ROOM','SAFETY ITEMS','NONE','','Welcome to the Generator Room. Please sure wear all required PPEs (personal protection equipments)',2,0,0,'',0,'','','NONE','','','','','','');
INSERT INTO domain_step VALUES(2,1,'','EAR PROTECTION','GENERATOR ROOM','SAFETY ITEMS','IMAGE','36','Please wear the ear protections.',3,1,0,'01',0,'','','NONE','','','','','','');
INSERT INTO domain_step VALUES(3,1,'','HELMET WITH LIGHT','GENERATOR ROOM','SAFETY ITEMS','IMAGE','37','Please wear the safety helmet.',4,0,0,'',0,'','','NONE','','','','','','');
INSERT INTO domain_step VALUES(4,1,'','SWITCH ON LIGHT','GENERATOR ROOM','SAFETY ITEMS','NONE','','Please switch on lights.',5,0,0,'',0,'','','NONE','','','','','','');
INSERT INTO domain_step VALUES(5,1,'','OIL LEVEL','GENERATOR ROOM','GENARAL','IMAGE','2.1','Ensure the Lube oil level with in the allowable limit (Must be at normal range, see the tag)',6,1,0,'02',0,'','','BOOLEAN','','','','','','Oil Level');
INSERT INTO domain_step VALUES(6,1,'','OIL LEAKS','GENERATOR ROOM','GENARAL','IMAGE','3','Inspect to verify NO any oil leaks on or around the engine body.',7,1,0,'03',0,'','','BOOLEAN','','','','','','Oil Leaks');
INSERT INTO domain_step VALUES(7,1,'','TRACO FILTER','GENERATOR ROOM','FUEL LEAKS','IMAGE','4','Inspect to verify NO any fuel leaks on the Traco filters.',8,1,0,'04',0,'','','BOOLEAN','','','','','','Track Filter fuel leaks');
INSERT INTO domain_step VALUES(8,1,'','PIPE LINE','GENERATOR ROOM','FUEL LEAKS','IMAGE','4','Inspect to verify NO any fuel leaks on the floor diesel path ways.',9,0,0,'',0,'','','BOOLEAN','','','','','','Pipeline fuel leaks');
INSERT INTO domain_step VALUES(9,1,'','DAY TANK DEISEL LEVEL','GENERATOR ROOM','DIESAL TANK','IMAGE','5','Check and log day tank Diesal level.',10,1,0,'05',0,'','','DECIMAL','','L','','1800','2300','Day tank diesal level');
INSERT INTO domain_step VALUES(10,1,'','MAIN TANK DEISEL LEVEL','GENERATOR ROOM','DIESAL TANK','IMAGE','34','Check the availability level of diesel level in Main Storage',11,0,0,'',0,'','','DECIMAL','','L','','7500','99999','Main tank diesal level');
INSERT INTO domain_step VALUES(11,1,'','BATTERY VOLTAGE','GENERATOR ROOM','CONTROL PANEL','VIDEO','6','Check and log the battery voltage.',12,1,0,'06',0,'','','DECIMAL','','V','','24','28','Control panel Battery Voltage');
INSERT INTO domain_step VALUES(12,1,'','kWh','GENERATOR ROOM','CONTROL PANEL','VIDEO','7','Check and log the total kWh.',13,0,0,'',0,'','','DECIMAL','','kWh','','','','Control panel kWh');
INSERT INTO domain_step VALUES(13,1,'','RUNNING HOURS','GENERATOR ROOM','CONTROL PANEL','VIDEO','8','Check and log the total running hours.',14,0,0,'',0,'','','DECIMAL','','h','','','','Control panel Running hours');
INSERT INTO domain_step VALUES(14,1,'','HEATER COIL','GENERATOR ROOM','ALTERNATOR','IMAGE','9','Check the heater coil condition(Touch and feel the warm condition of the alternator housing)',15,1,0,'09',0,'','','BOOLEAN','','','','','','Alternator Heater Oil');
INSERT INTO domain_step VALUES(15,1,'','AIR RESTRICTION GUAGE','GENERATOR ROOM','AIR FILTER','IMAGE','10','Check the indicator of AIR RESTRICTION GAUGE (Indicator color - None is OK)',16,1,0,'10',0,'','','BOOLEAN','','Green','','','','Air restriction gauge');
INSERT INTO domain_step VALUES(16,1,'','BATTERY CHARGER INDICATOR','GENERATOR ROOM','BATTERY CHARGER','IMAGE','11','Make sure the battery charger is ON mode(Check the indicator bulb is Lightup)',17,1,0,'11',0,'','','BOOLEAN','','','','','','Battery Charger Indicator');
INSERT INTO domain_step VALUES(17,1,'','BATTERY WATER LEVEL','GENERATOR ROOM','BATTERY CONDITION','IMAGE','12.1','Check the battery water level.',18,0,0,'',0,'','','BOOLEAN','','','','','','Battery water level');
INSERT INTO domain_step VALUES(18,1,'','WIRE TERMINALS','GENERATOR ROOM','BATTERY CONDITION','IMAGE','31','Check the battery wire terminal conditions.',19,0,0,'',0,'','','BOOLEAN','','','','','','Wire terminals');
INSERT INTO domain_step VALUES(19,1,'','TENSION','GENERATOR ROOM','RADIATOR FAN','IMAGE','13','Check the radiator fan belt tension.',20,1,0,'13',0,'','','BOOLEAN','','','','','','Radiator fan tension');
INSERT INTO domain_step VALUES(20,1,'','PHYSICAL DAMAGES','GENERATOR ROOM','RADIATOR FAN','IMAGE','30','Check the radiator fan belt physical damages.',21,0,0,'',0,'','','BOOLEAN','','','','','','Radiator fan damages');
INSERT INTO domain_step VALUES(21,1,'','COOLENT LEVEL','GENERATOR ROOM','RADIATOR COOLENT','IMAGE','14','Check the radiator coolant at the required level through the side glass.',22,1,0,'14',0,'','','BOOLEAN','','','','','','Radiator Coolent Level');
INSERT INTO domain_step VALUES(22,1,'','SELECTOR SWITCH','GENERATOR ROOM','SELECTOR SWITCH','VIDEO','15','Switch on the Generator manually (select the rotator switch to "Manual" mode and press "Manual Run/Stop" button.',23,1,0,'15',0,'','','NONE','','','','','','');
INSERT INTO domain_step VALUES(23,1,'','LINE VOLTAGE','GENERATOR ROOM','CONTROL PANEL','VIDEO','16','Check and log the line voltage.',24,0,0,'',0,'','','DECIMAL','','V','','395','411','Control panel Line voltage');
INSERT INTO domain_step VALUES(24,1,'','FREQUENCY','GENERATOR ROOM','CONTROL PANEL','VIDEO','17','Check and log the frequency.',25,0,0,'',0,'','','DECIMAL','','Hz','','49.5','50.5','Control panel Frequency');
INSERT INTO domain_step VALUES(25,1,'','AMPHERE','GENERATOR ROOM','CONTROL PANEL','VIDEO','18','Check the Amphere. (Only the Generator in running with the load)',26,0,0,'',1,26,'','DECIMAL','','A','','','','Control panel Amphere');
INSERT INTO domain_step VALUES(26,1,'','OIL PRESSURE','GENERATOR ROOM','CONTROL PANEL','VIDEO','19','Check and log the oil pressure.',27,0,0,'',0,'','','DECIMAL','','kPa','','520','532','Control panel Oil Pressure');
INSERT INTO domain_step VALUES(27,1,'','RPM','GENERATOR ROOM','CONTROL PANEL','VIDEO','20','Check and log the RPM',28,0,0,'',0,'','','DECIMAL','','rpm','1500','','','Control panel RPM');
INSERT INTO domain_step VALUES(28,1,'','SYSTEM ABNORMALITIES','GENERATOR ROOM','EXHAUST SYSTEM','IMAGE','21','Check the exhaust system abnormalities (Color, Sound, Vibration, and etc.)',29,1,0,'21',0,'','','BOOLEAN','','','','','','System Abnormalities');
INSERT INTO domain_step VALUES(29,1,'','OIL HORSES','GENERATOR ROOM','OIL LEAKS','IMAGE','33','Inspect to verify NO oil leaks in oil horses',30,1,0,'22',0,'','','BOOLEAN','','','','','','Oil Horses oil leaks');
INSERT INTO domain_step VALUES(30,1,'','ENGINE','GENERATOR ROOM','OIL LEAKS','IMAGE','22','Inspect to verify NO oil leaks in engine',31,0,0,'',0,'','','BOOLEAN','','','','','','Engine oil leaks');
INSERT INTO domain_step VALUES(31,1,'','FILTERS','GENERATOR ROOM','OIL LEAKS','IMAGE','32','Inspect to verify NO oil leaks in filters',1,0,1,'',0,'','','BOOLEAN','','','','','','Filter oil leaks');
DELETE FROM sqlite_sequence;
INSERT INTO sqlite_sequence VALUES('user',8);
INSERT INTO sqlite_sequence VALUES('domain',6);
INSERT INTO sqlite_sequence VALUES('transaction',0);
INSERT INTO sqlite_sequence VALUES('transaction_detail',0);
COMMIT;
