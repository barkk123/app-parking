INSERT INTO parking_location (id, street, building_number, city, zip_code)
VALUES
    (1, 'Main Street', '10A', 'New York', '10001'),
    (2, 'Highway Avenue', '200B', 'Los Angeles', '90001'),
    (3, 'Ocean Drive', '5C', 'Miami', '33101');

INSERT INTO parking (id, name, type, secured, number_of_parking_spots, location_id)
VALUES
    (1, 'Lubartowska', 'PUBLIC', true, 50, 1),
    (2, 'Lwowska', 'PRIVATE', false, 200, 2),
    (3, 'Cynamonowa', 'PUBLIC', false, 30, 3);

INSERT INTO parking_spot (id, number, status, parking_id)
VALUES
    (1, 1, 'FREE', 1),
    (2, 2, 'OCCUPIED', 1),
    (3, 3, 'FREE', 1),
    (4, 1, 'OCCUPIED', 2),
    (5, 2, 'FREE', 2),
    (6, 1, 'FREE', 3);

INSERT INTO vehicle (license_number, mark, model)
VALUES
    ('ABC1234', 'Toyota', 'Corolla'),
    ('XYZ5678', 'Honda', 'Civic'),
    ('LMN8901', 'Ford', 'Focus');

INSERT INTO parking_ticket (id, parking_spot_id, vehicle_license_number, status, arrival_time, departure_time, fee)
VALUES
    (1, 2, 'ABC1234', 'ACTIVE', '2024-12-23 08:30:00', NULL, NULL),
    (2, 4, 'XYZ5678', 'PAID', '2024-12-22 14:00:00', '2024-12-22 16:00:00', 15.00),
    (3, 1, 'LMN8901', 'ACTIVE', '2024-12-23 09:00:00', NULL, NULL);
