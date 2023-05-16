# SimpleTune
Subaru ROM Tuning and Logging Software  

* Requires JRE 1.6
* You'll need a usb-serial adapter for RS232 access to the UTEC. BAUD / Parity bits need to be set as in UTEC manual. You can test with telnet.
* OBD2 logging is requires an OpenPort 1.3* adapater. Not tested with later OpenPort 2.* and above.
* SMS OBD2 logging is supported. Remember that more data points logged, the slower the refresh rate! Take only what you need.
* ROM / ECU XML support is based on the ECU flash XML specification. As this is an older tool, updates may need to be made to support newer XML schemas.
* Logged data is saved as CSV and can data in these files can be overlaid on ROM tables using CSV params as table dimensions.

![ST_1](https://github.com/tgui9660/SimpleTune/assets/31426897/aa88e639-bcd4-4c42-be88-fec1286db4df)
![ST_2](https://github.com/tgui9660/SimpleTune/assets/31426897/5aeae797-673c-4acb-9b5b-fbf346cf8123)
