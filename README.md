For proper working, you need to install java 17 on your PC 

You can run project in IDE, running ParkingSystemApplication 

Using terminal in root:
Windows 

gradlew clean build
gradlew bootRun

Linux/Mac
chmod +x gradlew
./gradlew clean build
./gradlew bootRun

Access to Database console 

http://localhost:8080/h2-console

user test
no pass

SQL check

select * from levels 
select * from parking_events
select * from parking_lot
select * from parking_spots
select * from vehicles

Changes pricing strategy
ParkingEventService in constructor


postman requests for import [Parking System.postman_collection.json](https://github.com/user-attachments/files/23591912/Parking.System.postman_collection.json)

{
	"info": {
		"_postman_id": "cea787fd-d8e9-44dd-8620-3d90a408f87a",
		"name": "Parking System",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "27728382",
		"_collection_link": "https://solar-meteor-198286.postman.co/workspace/parking-system~41fc766e-2e0e-457f-a7e5-6679f72d1a2a/collection/27728382-cea787fd-d8e9-44dd-8620-3d90a408f87a?action=share&source=collection_link&creator=27728382"
	},
	"item": [
		{
			"name": "Create parking lot",
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\r\n    \"name\": \"Viktoria Garden\"\r\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8080/api/v1/parking-lots",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"v1",
						"parking-lots"
					]
				}
			},
			"response": []
		},
		{
			"name": "Get parking lot by id",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/api/v1/parking-lots/1",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"v1",
						"parking-lots",
						"1"
					]
				}
			},
			"response": []
		},
		{
			"name": "Create a new level",
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\r\n    \"levelNumber\": 1,\r\n    \"parkingLotId\": 1\r\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8080/api/v1/parking-lots/1/levels",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"v1",
						"parking-lots",
						"1",
						"levels"
					]
				}
			},
			"response": []
		},
		{
			"name": "Delete level",
			"request": {
				"method": "DELETE",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/api/v1/parking-lots/levels/1",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"v1",
						"parking-lots",
						"levels",
						"1"
					]
				}
			},
			"response": []
		},
		{
			"name": "Added 10 COMPACT spots",
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\r\n    \"levelId\": 1,\r\n    \"numberOfSpots\": 10,\r\n    \"spotType\": \"COMPACT\"\r\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8080/api/v1/parking-lots/levels/1/spots",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"v1",
						"parking-lots",
						"levels",
						"1",
						"spots"
					]
				}
			},
			"response": []
		},
		{
			"name": "Delete spot by id",
			"request": {
				"method": "DELETE",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/api/v1/parking-lots/spots/9",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"v1",
						"parking-lots",
						"spots",
						"9"
					]
				}
			},
			"response": []
		},
		{
			"name": "Check in car",
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\r\n    \"licensePlate\": \"AB1234CD\",\r\n    \"vehicleType\": \"CAR\",\r\n    \"parkingLotId\": 1,\r\n    \"isHandicapped\": false\r\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8080/api/v1/vehicles/check-in",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"v1",
						"vehicles",
						"check-in"
					]
				}
			},
			"response": []
		},
		{
			"name": "check in handicapped",
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\r\n    \"licensePlate\": \"AA8221AA\",\r\n    \"vehicleType\": \"CAR\",\r\n    \"parkingLotId\": 1,\r\n    \"isHandicapped\": true\r\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "POST http://localhost:8080/api/v1/vehicles/check-in",
					"protocol": "POST http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"v1",
						"vehicles",
						"check-in"
					]
				}
			},
			"response": []
		},
		{
			"name": "Active session",
			"request": {
				"method": "GET",
				"header": []
			},
			"response": []
		},
		{
			"name": "Check out",
			"request": {
				"method": "GET",
				"header": []
			},
			"response": []
		}
	]
}
