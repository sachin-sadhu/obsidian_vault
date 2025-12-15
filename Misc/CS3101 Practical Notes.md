entity sets: locations, stations, train, carriages, features, service, schedule

* locations
	- id
	- name
- station
	- id (3 character code)
	- location id (foreign key to locations table)
- train
	- id 
* carriages
	* train_id (train this carriage belongs to)
	* letter
	* feature_id (foreign key to features tables)
- features 
	- id
	- name
	- description? 
- carriage_feature (relationship between carriages and features - many to many)
	- carriage_id
	- feature_id
	- train_id
- service 
	- id
	- start date
	- end date
	- origin station (foreign key to station)
	-  destination (foreign key to station)
- stops entity (relationship between service and location - many to many)
	- service_id
	- arrival_time
	- departure_time
	- platform
	- station_id
* schedule - (primary key could be composite key of station_id and service_id)
	* station_id
	* service_id
	* arrival_time
	* departure_time
	* platform
	* origin
	* destination

relations:
* location - station
	* one to many (each location can have multiple stations)