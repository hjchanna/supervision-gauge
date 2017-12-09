PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE "user" (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL,
	`username`	TEXT NOT NULL,
	`password`	TEXT NOT NULL
);
INSERT INTO "user" VALUES(1,'Admin','admin','secret');
CREATE TABLE "domain" (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`title`	TEXT NOT NULL,
	`description`	TEXT NOT NULL,
	`first_step`	INTEGER NOT NULL,
	`report_description`	texT
);
INSERT INTO "domain" VALUES(1,'GENERATOR ROOM','Measurements of generator room',1,NULL);
INSERT INTO "domain" VALUES(2,'COMPRESSOR ROOM','Measurements  of Compressor room',-1,NULL);
INSERT INTO "domain" VALUES(3,'CHILLER ROOM','Measurements of Chiller room',-1,NULL);
INSERT INTO "domain" VALUES(4,'STP ROOM','Measurements of STP room',-1,NULL);
INSERT INTO "domain" VALUES(5,'WTP ROOM','Measurements of WTP room',-1,NULL);
INSERT INTO "domain" VALUES(6,'Electrical System','Measurements of electrical system',-1,NULL);
CREATE TABLE "domain_step" (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`domain_id`	INTEGER NOT NULL,
	`number`	TEXT,
	`title`	TEXT NOT NULL,
	`main_group`	teXT,
	`sub_group`	teXT,
	`visual_type`	TEXT NOT NULL,
	`visual_link`	TEXT,
	`visual_description`	TEXT NOT NULL,
	`next_step`	INTEGER,
	`show_map`	INTEGER NOT NULL,
	`last_step`	INTEGER NOT NULL,
	`map_from_previous_link`	INTEGER NOT NULL,
	`enable_skip`	INTEGER NOT NULL,
	`skip_to_step`	INTEGER,
	`map_from_beginning_link`	INTEGER NOT NULL,
	`value_type`	TEXT NOT NULL,
	`value_hint`	TEXT,
	`unit`	TEXT,
	`default_value`	TEXT,
	`default_from_value`	TEXT,
	`default_to_value`	TEXT,
	`report_description`	TEXT
);
INSERT INTO "domain_step" VALUES(1,1,'','WELCOME','GENERAL CHECKUP','SAFETY ITEMS','IMAGE','1_generator_room','Welcome to the generator room. You are going to inspect the generator room now.',2,0,0,'',0,'','','NONE','','','','','','');
INSERT INTO "domain_step" VALUES(2,1,'','SAFETY - EAR PROTECTION','GENERAL CHECKUP','SAFETY ITEMS','IMAGE','2_ear_protection','Please wear ear protection device before you go inside the generator room.',3,1,0,'2_safety_ear_protection',0,'','','NONE','','','','','','');
INSERT INTO "domain_step" VALUES(3,1,'','SAFETY - HELMET WITH LIGHT','GENERAL CHECKUP','SAFETY ITEMS','IMAGE','3_helmet_with_light','Please wear a safety helmet with light before enter the generator room.',4,0,0,'',0,'','','NONE','','','','','','');
INSERT INTO "domain_step" VALUES(4,1,'','SAFETY - SWITCH ON LIGHT','GENERAL CHECKUP','SAFETY ITEMS','IMAGE','4_switch_on_light','Please switch on  helmet light.',5,0,0,'',0,'','','NONE','','','','','','');
INSERT INTO "domain_step" VALUES(5,1,'','SYNCHRONIZE PANEL','GENERAL CHECKUP','SYNCHRONIZE PANEL','IMAGE','5_synchronizing_panel','Now you are about to inspect the synchronize panel. Be sure you are currently placed in front of the synchronize panel.',6,1,0,'5_synchronizing_panel',1,21,'','NONE','','','','','','');
INSERT INTO "domain_step" VALUES(6,1,'','SYNC. PANEL - EMERGENCY SWITCH','GENERAL CHECKUP','SYNCHRONIZE PANEL','IMAGE','6_emergency_switch','Check whether emergency switch of synchronize panel is working.',7,0,0,'',0,'','','BOOLEAN','','','','','','Emergency Switch');
INSERT INTO "domain_step" VALUES(7,1,'','SYNC. PANEL - SYS. CONTROL SWITCH','GENERAL CHECKUP','SYNCHRONIZE PANEL','IMAGE','7_system_control_switch','Check system control switch',8,0,0,'',0,'','','BOOLEAN','','','','','','System Control Switch');
INSERT INTO "domain_step" VALUES(8,1,'','SYNC. PANEL - LOAD DEMAND SWITCH','GENERAL CHECKUP','SYNCHRONIZE PANEL','IMAGE','8_load_demand_switch','Check load demand switch',9,0,0,'',0,'','','BOOLEAN','','','','','','Load Demant Switch');
INSERT INTO "domain_step" VALUES(9,1,'','SYNC. PANEL - LOAD DEMAND DROPOUT','GENERAL CHECKUP','SYNCHRONIZE PANEL','IMAGE','9_load_demand_dropout','Check demand dropout',10,0,0,'',0,'','','DECIMAL','','','80','','','Load Demand Dropout');
INSERT INTO "domain_step" VALUES(10,1,'','SYNC. PANEL - LOAD DEMAND PICKUP','GENERAL CHECKUP','SYNCHRONIZE PANEL','IMAGE','10_load_demand_pickup','Check demand pickup',11,0,0,'',0,'','','DECIMAL','','','75','','','Load Demand Pickup');
INSERT INTO "domain_step" VALUES(11,1,'','SYNC. PANEL - LOAD UNIT SELECTION','GENERAL CHECKUP','SYNCHRONIZE PANEL','IMAGE','11_load_unit_selection','Check load unit selection',12,0,0,'',0,'','','DECIMAL','','','9','','','Load Unit Selection');
INSERT INTO "domain_step" VALUES(12,1,'','SYNC. PANEL - EARTH FAULT RELAY 01','GENERAL CHECKUP','SYNCHRONIZE PANEL','IMAGE','12_earth_fault_relay_1','Check Earth Fault Relay 1',13,0,0,'',0,'','','BOOLEAN','','','','','','Earth Fault Relay 1');
INSERT INTO "domain_step" VALUES(13,1,'','SYNC. PANEL - EARTH FAULT RELAY 02','GENERAL CHECKUP','SYNCHRONIZE PANEL','IMAGE','13_earth_fault_relay_2','Check Earth Fault Relay 2',14,0,0,'',0,'','','BOOLEAN','','','','','','Earth Fault Relay 2');
INSERT INTO "domain_step" VALUES(14,1,'','SYNC. PANEL - EARTH FAULT RELAY 03','GENERAL CHECKUP','SYNCHRONIZE PANEL','IMAGE','14_earth_fault_relay_3','Check Earth Fault Relay 3',15,0,0,'',0,'','','BOOLEAN','','','','','','Earth Fault Relay 3');
INSERT INTO "domain_step" VALUES(15,1,'','SYNC. PANEL - POWER COMMAND PANEL','GENERAL CHECKUP','SYNCHRONIZE PANEL','IMAGE','15_power_command_panel','Check Power Command Panel',16,0,0,'',0,'','','BOOLEAN','','','','','','Power Command Panel');
INSERT INTO "domain_step" VALUES(16,1,'','SYNC. PANEL - ANALYSER READING','GENERAL CHECKUP','SYNCHRONIZE PANEL','IMAGE','16_analyser_reading','Check Analyzer Reading',17,0,0,'',0,'','','BOOLEAN','','','','','','Analyzer Reading');
INSERT INTO "domain_step" VALUES(17,1,'','SYNC. PANEL - INDICATOR LIGHT 01','GENERAL CHECKUP','SYNCHRONIZE PANEL','IMAGE','17_indicator_light_1','Check Indicator Lights (RED/YELLOW/BLUE) Lighting and report whethe lights are OK or NOT OK.',18,0,0,'',0,'','','BOOLEAN','','','','','','Indicator Light 01');
INSERT INTO "domain_step" VALUES(18,1,'','SYNC. PANEL - INDICATOR LIGHT 02','GENERAL CHECKUP','SYNCHRONIZE PANEL','IMAGE','18_indicator_light_2','Check Indicator Lights (RED/YELLOW/BLUE) Lighting and report whethe lights are OK or NOT OK.',19,0,0,'',0,'','','BOOLEAN','','','','','','Indicator Light 02');
INSERT INTO "domain_step" VALUES(19,1,'','SYNC. PANEL - INDICATOR LIGHT 03','GENERAL CHECKUP','SYNCHRONIZE PANEL','IMAGE','19_indicator_light_3','Check Indicator Lights (RED/YELLOW/BLUE) Lighting and report whethe lights are OK or NOT OK.',20,0,0,'',0,'','','BOOLEAN','','','','','','Indicator Light 03');
INSERT INTO "domain_step" VALUES(20,1,'','SYNC. PANEL - MOTORISED BREAKER','GENERAL CHECKUP','SYNCHRONIZE PANEL','IMAGE','18_motorised_breaker_position','Check Motorized Breaker Position',21,0,0,'',0,'','','BOOLEAN','','','','','','Motorized Breaker Position');
INSERT INTO "domain_step" VALUES(21,1,'','GENERATOR 01 - OFF MODE','GENERATOR 01','OFF MODE','IMAGE','19_generator_01_off_mode','Now you are about to inspect the Generator 01 in off mode. Be sure you are currently placed in front of the Generator 01.',22,1,0,'19_generator_01_off_mode',1,31,'','NONE','','','','','','');
INSERT INTO "domain_step" VALUES(22,1,'','GEN. 01 - PANEL BOARD','GENERATOR 01','OFF MODE','IMAGE','20_generator_01_panel_board','Check generator 01 control panel switch and report whether control panel switch is OK or NOT OK.',23,0,0,'',0,'','','BOOLEAN','','','','','','Control panel switch off');
INSERT INTO "domain_step" VALUES(23,1,'','GEN. 01 - BATTERY PACK','GENERATOR 01','OFF MODE','IMAGE','21_generator_01_battery_pack','Check generator 01 battery water level and report whether water level is OK or NOT OK.',24,1,0,'21_generator_01_battery_pack',0,'','','BOOLEAN','','','','','','Battery water level');
INSERT INTO "domain_step" VALUES(24,1,'','GEN. 01 - ENGINE OIL LEAKS','GENERATOR 01','OFF MODE','IMAGE','22_generator_01_oil_leaks','Check generator 01 body for oil leaks and report OK if oil leaks not available and NOT OK if oil leaks available.',25,0,0,'',0,'','','BOOLEAN','','','','','','Engine oil leaks (visual inspection)');
INSERT INTO "domain_step" VALUES(25,1,'','GEN. 01 - ALTERNATOR BELT TENSION','GENERATOR 01','OFF MODE','IMAGE','23_generator_01_alternator','Check generator 01 alternator for it''s belt tension and report whether belt tension is OK or NOT OK.',26,0,0,'',0,'','','BOOLEAN','','','','','','Alternator belt tension/condition');
INSERT INTO "domain_step" VALUES(26,1,'','GEN. 01 - ENGINE OIL LEVEL','GENERATOR 01','OFF MODE','VIDEO','24_generator_01_engine_oil_level','Check Generator 01 engine oil level and report oil level is OK or NOT OK.',27,1,0,'24_generator_01_engine_oil_level',0,'','','BOOLEAN','','','','','','Engine oil level');
INSERT INTO "domain_step" VALUES(27,1,'','GEN. 01 - AIR FILTER INDICATOR','GENERATOR 01','OFF MODE','IMAGE','25_generator_01_air_filter','Check Generator 01 air filter and report air filter is OK or NOT OK.',28,0,0,'',0,'','','BOOLEAN','','','','','','Air filter condition');
INSERT INTO "domain_step" VALUES(28,1,'','GEN. 01 - RADIATOR FAN TENSION','GENERATOR 01','OFF MODE','VIDEO','26_generator_01_radiator_fan_tension','Check Generator 01 radiator for fan tension and report whether fan tension is OK or NOT OK.',29,0,0,'',0,'','','BOOLEAN','','','','','','Radiator fan tension/condition');
INSERT INTO "domain_step" VALUES(29,1,'','GEN. 01 - RADIATOR COOLEANT','GENERATOR 01','OFF MODE','VIDEO','28_generator_01_radiator_coolent_level','Check Generator 01 radiator coolant level and report whether coolant level is OK or NOT OK.',30,0,0,'',0,'','','BOOLEAN','','','','','','Radiator cooleant level');
INSERT INTO "domain_step" VALUES(30,1,'','GEN. 01 - BATTERY VOLTAGE','GENERATOR 01','OFF MODE','VIDEO','29_generator_01_battery_voltage','Check Generator 01 battery voltage and report battery voltage value.',31,1,0,'29_generator_01_battery_voltage',0,'','','DECIMAL','','V','','24','27','Battery voltage');
INSERT INTO "domain_step" VALUES(31,1,'','GENERATOR 02 - OFF MODE','GENERATOR 02','OFF MODE','IMAGE','19_generator_01_off_mode','Now you are about to inspect the Generator 02 in off mode. Be sure you are currently placed in front of the Generator 02.',32,1,0,'30_generator_02_off_mode',1,41,'','NONE','','','','','','');
INSERT INTO "domain_step" VALUES(32,1,'','GEN. 02 - PANEL BOARD','GENERATOR 02','OFF MODE','IMAGE','20_generator_01_panel_board','Check generator 02 control panel switch and report whether control panel switch is OK or NOT OK.',33,0,0,'',0,'','','BOOLEAN','','','','','','Control panel switch off');
INSERT INTO "domain_step" VALUES(33,1,'','GEN. 02 - BATTERY PACK','GENERATOR 02','OFF MODE','IMAGE','21_generator_01_battery_pack','Check generator 02 battery water level and report whether water level is OK or NOT OK.',34,1,0,'32_generator_02_battery_pack',0,'','','BOOLEAN','','','','','','Battery water level');
INSERT INTO "domain_step" VALUES(34,1,'','GEN. 02 - ENGINE OIL LEAKS','GENERATOR 02','OFF MODE','IMAGE','22_generator_01_oil_leaks','Check generator 02 body for oil leaks and report OK if oil leaks not available and NOT OK if oil leaks available.',35,0,0,'',0,'','','BOOLEAN','','','','','','Engine oil leaks (visual inspection)');
INSERT INTO "domain_step" VALUES(35,1,'','GEN. 02 - ALTERNATOR BELT TENSION','GENERATOR 02','OFF MODE','IMAGE','23_generator_01_alternator','Check generator 02 alternator for it''s belt tension and report whether belt tension is OK or NOT OK.',36,0,0,'',0,'','','BOOLEAN','','','','','','Alternator belt tension/condition');
INSERT INTO "domain_step" VALUES(36,1,'','GEN. 02 - ENGINE OIL LEVEL','GENERATOR 02','OFF MODE','VIDEO','24_generator_01_engine_oil_level','Check Generator 02 engine oil level and report oil level is OK or NOT OK.',37,1,0,'35_generator_02_engine_oil_level',0,'','','BOOLEAN','','','','','','Engine oil level');
INSERT INTO "domain_step" VALUES(37,1,'','GEN. 02 - AIR FILTER INDICATOR','GENERATOR 02','OFF MODE','IMAGE','25_generator_01_air_filter','Check Generator 02 air filter and report air filter is OK or NOT OK.',38,0,0,'',0,'','','BOOLEAN','','','','','','Air filter condition');
INSERT INTO "domain_step" VALUES(38,1,'','GEN. 02 - RADIATOR FAN TENSION','GENERATOR 02','OFF MODE','VIDEO','26_generator_01_radiator_fan_tension','Check Generator 02 radiator for fan tension and report whether fan tension is OK or NOT OK.',39,0,0,'',0,'','','BOOLEAN','','','','','','Radiator fan tension/condition');
INSERT INTO "domain_step" VALUES(39,1,'','GEN. 02 - RADIATOR COOLEANT','GENERATOR 02','OFF MODE','VIDEO','28_generator_01_radiator_coolent_level','Check Generator 02 radiator coolant level and report whether coolant level is OK or NOT OK.',40,0,0,'',0,'','','BOOLEAN','','','','','','Radiator cooleant level');
INSERT INTO "domain_step" VALUES(40,1,'','GEN. 02 - BATTERY VOLTAGE','GENERATOR 02','OFF MODE','VIDEO','29_generator_01_battery_voltage','Check Generator 02 battery voltage and report battery voltage value.',41,1,0,'40_generator_02_battery_voltage',0,'','','DECIMAL','','V','','24','27','Battery voltage');
INSERT INTO "domain_step" VALUES(41,1,'','GENERATOR 03 - OFF MODE','GENERATOR 03','OFF MODE','IMAGE','19_generator_01_off_mode','Now you are about to inspect the Generator 03 in off mode. Be sure you are currently placed in front of the Generator 03.',42,1,0,'41_generator_03_off_mode',1,51,'','NONE','','','','','','');
INSERT INTO "domain_step" VALUES(42,1,'','GEN. 03 - PANEL BOARD','GENERATOR 03','OFF MODE','IMAGE','20_generator_01_panel_board','Check generator 03 control panel switch and report whether control panel switch is OK or NOT OK.',43,0,0,'',0,'','','BOOLEAN','','','','','','Control panel switch off');
INSERT INTO "domain_step" VALUES(43,1,'','GEN. 03 - BATTERY PACK','GENERATOR 03','OFF MODE','IMAGE','21_generator_01_battery_pack','Check generator 03 battery water level and report whether water level is OK or NOT OK.',44,1,0,'43_generator_03_battery_pack',0,'','','BOOLEAN','','','','','','Battery water level');
INSERT INTO "domain_step" VALUES(44,1,'','GEN. 03 - ENGINE OIL LEAKS','GENERATOR 03','OFF MODE','IMAGE','22_generator_01_oil_leaks','Check generator 03 body for oil leaks and report OK if oil leaks not available and NOT OK if oil leaks available.',45,0,0,'',0,'','','BOOLEAN','','','','','','Engine oil leaks (visual inspection)');
INSERT INTO "domain_step" VALUES(45,1,'','GEN. 03 - ALTERNATOR BELT TENSION','GENERATOR 03','OFF MODE','IMAGE','23_generator_01_alternator','Check generator 03 alternator for it''s belt tension and report whether belt tension is OK or NOT OK.',46,0,0,'',0,'','','BOOLEAN','','','','','','Alternator belt tension/condition');
INSERT INTO "domain_step" VALUES(46,1,'','GEN. 03 - ENGINE OIL LEVEL','GENERATOR 03','OFF MODE','VIDEO','24_generator_01_engine_oil_level','Check Generator 03 engine oil level and report oil level is OK or NOT OK.',47,1,0,'46_generator_03_engine_oil_level',0,'','','BOOLEAN','','','','','','Engine oil level');
INSERT INTO "domain_step" VALUES(47,1,'','GEN. 03 - AIR FILTER INDICATOR','GENERATOR 03','OFF MODE','IMAGE','25_generator_01_air_filter','Check Generator 03 air filter and report air filter is OK or NOT OK.',48,0,0,'',0,'','','BOOLEAN','','','','','','Air filter condition');
INSERT INTO "domain_step" VALUES(48,1,'','GEN. 03 - RADIATOR FAN TENSION','GENERATOR 03','OFF MODE','VIDEO','26_generator_01_radiator_fan_tension','Check Generator 03 radiator for fan tension and report whether fan tension is OK or NOT OK.',49,0,0,'',0,'','','BOOLEAN','','','','','','Radiator fan tension/condition');
INSERT INTO "domain_step" VALUES(49,1,'','GEN. 03 - RADIATOR COOLEANT','GENERATOR 03','OFF MODE','IMAGE','50_generator_03_radiator_cooleant','Check Generator 03 radiator coolant level and report whether coolant level is OK or NOT OK.',50,0,0,'',0,'','','BOOLEAN','','','','','','Radiator cooleant level');
INSERT INTO "domain_step" VALUES(50,1,'','GEN. 03 - BATTERY VOLTAGE','GENERATOR 03','OFF MODE','VIDEO','29_generator_01_battery_voltage','Check Generator 03 battery voltage and report battery voltage value.',51,1,0,'51_generator_03_battery_voltage',0,'','','DECIMAL','','V','','24','27','Battery voltage');
INSERT INTO "domain_step" VALUES(51,1,'','DEISAL TANK','GENERAL CHECKUP','SITE INSPECTION','IMAGE','52_deisal_tank','Check Diesel tank',52,1,0,'52_deisal_tank',1,52,'','DECIMAL','','l','','3000','20000','Diesel tank');
INSERT INTO "domain_step" VALUES(52,1,'','GENERATOR 03 - RUN MODE','GENERATOR 03','RUN MODE','IMAGE','19_generator_01_off_mode','Now you are about to inspect the Generator 03 in run mode. Be sure you are currently placed in front of the Generator 03.',53,1,0,'53_generator_03_run_mode',1,63,'','NONE','','','','','','');
INSERT INTO "domain_step" VALUES(53,1,'','GEN. 03 - ERROR MESSAGES','GENERATOR 03','RUN MODE','IMAGE','54_generator_03_error_messages','Check generator 03 for error messages.',54,0,0,'',0,'','','BOOLEAN','','','','','','Error message');
INSERT INTO "domain_step" VALUES(54,1,'','GEN. 03 - OIL PRESSURE','GENERATOR 03','RUN MODE','VIDEO','55_oil_pressure','Check generator 03 for oil pressure.',55,0,0,'',0,'','','DECIMAL','','Pa','','20','80','Oil pressure');
INSERT INTO "domain_step" VALUES(55,1,'','GEN. 03 - OIL TEMPERATURE','GENERATOR 03','RUN MODE','VIDEO','55_oil_pressure','Check generator 03 for oil temperature.',56,0,0,'',0,'','','DECIMAL','','C','','160','210','Oil Temperature');
INSERT INTO "domain_step" VALUES(56,1,'','GEN. 03 - COOLEANT TEMPERATURE','GENERATOR 03','RUN MODE','VIDEO','55_oil_pressure','Check generator 03 for coolant temperature.',57,0,0,'',0,'','','DECIMAL','','C','','170','210','Coolant Temperature');
INSERT INTO "domain_step" VALUES(57,1,'','GEN. 03 - FREQUENCY','GENERATOR 03','RUN MODE','VIDEO','55_oil_pressure','Check generator 03 frequency.',58,0,0,'',0,'','','DECIMAL','','Hz','','49.5','50.5','Frequency');
INSERT INTO "domain_step" VALUES(58,1,'','GEN. 03 - CURRENT','GENERATOR 03','RUN MODE','VIDEO','55_oil_pressure','Check generator 03 current.',59,0,0,'',0,'','','DECIMAL','','A','','','','Current');
INSERT INTO "domain_step" VALUES(59,1,'','GEN. 03 - LINE VOLTAGE','GENERATOR 03','RUN MODE','VIDEO','55_oil_pressure','Check generator 03 line voltage.',60,0,0,'',0,'','','DECIMAL','','V','','380','415','Line voltage');
INSERT INTO "domain_step" VALUES(60,1,'','GEN. 03 - PHASE VOLTAGE','GENERATOR 03','RUN MODE','VIDEO','55_oil_pressure','Check generator 03 phase voltage.',61,0,0,'',0,'','','DECIMAL','','V','','220','240','Phase voltage');
INSERT INTO "domain_step" VALUES(61,1,'','GEN. 03 - NOICE INSPECTION','GENERATOR 03','RUN MODE','NONE','','Inspect generator 03 for noises.',62,0,0,'',0,'','','BOOLEAN','','','','','','Noice inspection');
INSERT INTO "domain_step" VALUES(62,1,'','GEN. 03 - GENERAL INSPECTION','GENERATOR 03','RUN MODE','NONE','','Inspect generator 03 for general abnormalities like steam or smoke.',63,0,0,'',0,'','','BOOLEAN','','','','','','General inspection');
INSERT INTO "domain_step" VALUES(63,1,'','GENERATOR 02 - RUN MODE','GENERATOR 02','RUN MODE','IMAGE','19_generator_01_off_mode','Now you are about to inspect the Generator 02 in run mode. Be sure you are currently placed in front of the Generator 02.',64,1,0,'64_generator_02_run_mode',1,74,'','NONE','','','','','','');
INSERT INTO "domain_step" VALUES(64,1,'','GEN. 02 - ERROR MESSAGES','GENERATOR 02','RUN MODE','IMAGE','54_generator_03_error_messages','Check generator 02 for error messages.',65,0,0,'',0,'','','BOOLEAN','','','','','','Error message');
INSERT INTO "domain_step" VALUES(65,1,'','GEN. 02 - OIL PRESSURE','GENERATOR 02','RUN MODE','VIDEO','55_oil_pressure','Check generator 02 for oil pressure.',66,0,0,'',0,'','','DECIMAL','','Pa','','20','80','Oil pressure');
INSERT INTO "domain_step" VALUES(66,1,'','GEN. 02 - OIL TEMPERATURE','GENERATOR 02','RUN MODE','VIDEO','55_oil_pressure','Check generator 02 for oil temperature.',67,0,0,'',0,'','','DECIMAL','','C','','160','210','Oil Temperature');
INSERT INTO "domain_step" VALUES(67,1,'','GEN. 02 - COOLEANT TEMPERATURE','GENERATOR 02','RUN MODE','VIDEO','55_oil_pressure','Check generator 02 for coolant temperature.',68,0,0,'',0,'','','DECIMAL','','C','','170','210','Coolant Temperature');
INSERT INTO "domain_step" VALUES(68,1,'','GEN. 02 - FREQUENCY','GENERATOR 02','RUN MODE','VIDEO','55_oil_pressure','Check generator 02 frequency.',69,0,0,'',0,'','','DECIMAL','','Hz','','49.5','50.5','Frequency');
INSERT INTO "domain_step" VALUES(69,1,'','GEN. 02 - CURRENT','GENERATOR 02','RUN MODE','VIDEO','55_oil_pressure','Check generator 02 current.',70,0,0,'',0,'','','DECIMAL','','A','','0','2700','Current');
INSERT INTO "domain_step" VALUES(70,1,'','GEN. 02 - LINE VOLTAGE','GENERATOR 02','RUN MODE','VIDEO','55_oil_pressure','Check generator 02 line voltage.',71,0,0,'',0,'','','DECIMAL','','V','','380','415','Line voltage');
INSERT INTO "domain_step" VALUES(71,1,'','GEN. 02 - PHASE VOLTAGE','GENERATOR 02','RUN MODE','VIDEO','55_oil_pressure','Check generator 02 phase voltage.',72,0,0,'',0,'','','DECIMAL','','V','','220','240','Phase voltage');
INSERT INTO "domain_step" VALUES(72,1,'','GEN. 02 - NOICE INSPECTION','GENERATOR 02','RUN MODE','NONE','','Inspect generator 02 for noises.',73,0,0,'',0,'','','BOOLEAN','','','','','','Noice inspection');
INSERT INTO "domain_step" VALUES(73,1,'','GEN. 02 - GENERAL INSPECTION','GENERATOR 02','RUN MODE','NONE','','Inspect generator 02 for general abnormalities like steam or smoke.',74,0,0,'',0,'','','BOOLEAN','','','','','','General inspection');
INSERT INTO "domain_step" VALUES(74,1,'','GENERATOR 01 - RUN MODE','GENERATOR 01','RUN MODE','IMAGE','19_generator_01_off_mode','Now you are about to inspect the Generator 01 in run mode. Be sure you are currently placed in front of the Generator 01.',75,1,0,'75_generator_01_run_mode',1,85,'','NONE','','','','','','');
INSERT INTO "domain_step" VALUES(75,1,'','GEN. 01 - ERROR MESSAGES','GENERATOR 01','RUN MODE','IMAGE','54_generator_03_error_messages','Check generator 01 for error messages.',76,0,0,'',0,'','','BOOLEAN','','','','','','Error message');
INSERT INTO "domain_step" VALUES(76,1,'','GEN. 01 - OIL PRESSURE','GENERATOR 01','RUN MODE','VIDEO','55_oil_pressure','Check generator 01 for oil pressure.',77,0,0,'',0,'','','DECIMAL','','Pa','','20','80','Oil pressure');
INSERT INTO "domain_step" VALUES(77,1,'','GEN. 01 - OIL TEMPERATURE','GENERATOR 01','RUN MODE','VIDEO','55_oil_pressure','Check generator 01 for oil temperature.',78,0,0,'',0,'','','DECIMAL','','C','','160','210','Oil Temperature');
INSERT INTO "domain_step" VALUES(78,1,'','GEN. 01 - COOLEANT TEMPERATURE','GENERATOR 01','RUN MODE','VIDEO','55_oil_pressure','Check generator 01 for coolant temperature.',79,0,0,'',0,'','','DECIMAL','','C','','170','210','Coolant Temperature');
INSERT INTO "domain_step" VALUES(79,1,'','GEN. 01 - FREQUENCY','GENERATOR 01','RUN MODE','VIDEO','55_oil_pressure','Check generator 01 frequency.',80,0,0,'',0,'','','DECIMAL','','Hz','','49.5','50.5','Frequency');
INSERT INTO "domain_step" VALUES(80,1,'','GEN. 01 - CURRENT','GENERATOR 01','RUN MODE','VIDEO','55_oil_pressure','Check generator 01 current.',81,0,0,'',0,'','','DECIMAL','','A','','0','2700','Current');
INSERT INTO "domain_step" VALUES(81,1,'','GEN. 01 - LINE VOLTAGE','GENERATOR 01','RUN MODE','VIDEO','55_oil_pressure','Check generator 01 line voltage.',82,0,0,'',0,'','','DECIMAL','','V','','380','415','Line voltage');
INSERT INTO "domain_step" VALUES(82,1,'','GEN. 01 - PHASE VOLTAGE','GENERATOR 01','RUN MODE','VIDEO','55_oil_pressure','Check generator 01 phase voltage.',83,0,0,'',0,'','','DECIMAL','','V','','220','240','Phase voltage');
INSERT INTO "domain_step" VALUES(83,1,'','GEN. 01 - NOICE INSPECTION','GENERATOR 01','RUN MODE','NONE','','Inspect generator 01 for noises.',84,0,0,'',0,'','','BOOLEAN','','','','','','Noice inspection');
INSERT INTO "domain_step" VALUES(84,1,'','GEN. 01 - GENERAL INSPECTION','GENERATOR 01','RUN MODE','NONE','','Inspect generator 01 for general abnormalities like steam or smoke.',85,0,0,'',0,'','','BOOLEAN','','','','','','General inspection');
INSERT INTO "domain_step" VALUES(85,1,'','SITE  - ATANUATER','GENERAL CHECKUP','SITE INSPECTION','NONE','','Check attenuator front and surround',86,1,0,'86_site_inspection_atanuater',1,89,'','BOOLEAN','','','','','','Attenuator');
INSERT INTO "domain_step" VALUES(86,1,'','SITE  - SEILING','GENERAL CHECKUP','SITE INSPECTION','NONE','','Check ceiling appearance and lights',87,0,0,'',0,'','','BOOLEAN','','','','','','Ceiling');
INSERT INTO "domain_step" VALUES(87,1,'','SITE  - FLOOR','GENERAL CHECKUP','SITE INSPECTION','NONE','','Check floor general appearance',88,0,0,'',0,'','','BOOLEAN','','','','','','Floor');
INSERT INTO "domain_step" VALUES(88,1,'','SITE  - OIL LINES','GENERAL CHECKUP','SITE INSPECTION','NONE','','Check oil lines general appearance',89,0,0,'',0,'','','BOOLEAN','','','','','','Oil lines');
INSERT INTO "domain_step" VALUES(89,1,'','FINISH','GENERAL CHECKUP','SITE INSPECTION','NONE','','Well done. You have finished checking generator room.','',0,1,'',0,'','','NONE','','','','','','Finish');
CREATE TABLE "transaction" (
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
CREATE TABLE "transaction_detail" (
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
DELETE FROM sqlite_sequence;
INSERT INTO "sqlite_sequence" VALUES('user',1);
INSERT INTO "sqlite_sequence" VALUES('domain',6);
INSERT INTO "sqlite_sequence" VALUES('domain_step',89);
INSERT INTO "sqlite_sequence" VALUES('transaction',0);
INSERT INTO "sqlite_sequence" VALUES('transaction_detail',0);
COMMIT;
