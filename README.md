# SimpleTune
Subaru / Mitsubishi ROM Tuning and Logging Software With UTEC integration 

This is an automotive tuning platform that supports ROM editing, logging, data overlays, and statistics. Includes best fit curve 1D/2D data smoothing.

* Requires JRE 1.6
* Various AFR sensors supported though mostly tested against UTEC AFR sensor. Wideband data can be combined in line with SSM logged OBD2 parameters.
* You'll need a usb-serial adapter for RS232 access to the UTEC. BAUD / Parity bits need to be set as in UTEC manual. You can test with telnet.
* UTEC piggyback ECU tuning entity to support direct tuning of UTEC through SimpleTune.
* New tuning entities can be created against the tuning entity interfaces in code. ROM / UTEC currently only supported. Same with logging.
* OBD2 logging is requires an OpenPort 1.3* adapter. Not tested with later OpenPort 2.* and above.
* SSM (Subaru extensions) OBD2 logging is supported. Remember that more data points logged, the slower the refresh rate! Take only what you need.
* ROM / ECU XML support is based on the ECU flash XML specification. As this is an older tool, updates may need to be made to support newer XML schemas.
* Logged data is saved as CSV. Data in these files can be overlaid on ROM tables using CSV params as table dimensions.
* Prelim work done with memory resident relational table (saved to disk on close) which was to host all data collected for better historical data retrieval. Much todo!
* Multiple ROMs can be opened at same time.
* Each session keeps an entire history of all changes made to a ROM for quick undo/redo.
* Comparison GUI tool included to review changes between different ROMs. See how other tunes compare to yours!



![ST_1](https://github.com/tgui9660/SimpleTune/assets/31426897/aa88e639-bcd4-4c42-be88-fec1286db4df)

Complete OBD2 Subaru SSM protocol based logging.
![ST_2](https://github.com/tgui9660/SimpleTune/assets/31426897/5aeae797-673c-4acb-9b5b-fbf346cf8123)

Best fit curve data smoothing.
![image](https://github.com/tgui9660/SimpleTune/assets/31426897/a8b26350-23e7-4891-8e12-ff6548d45903)

ROM comparison tool to find changes in dimensionally equivalent tables.
![image](https://github.com/tgui9660/SimpleTune/assets/31426897/c6046c2d-0b73-4c5f-a27c-4bdc37a058a4)

CSV data overlay.</br>
![image](https://github.com/tgui9660/SimpleTune/assets/31426897/57a47980-4557-4436-9fe2-02b445ce39cb)

