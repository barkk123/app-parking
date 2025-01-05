INSERT INTO parking_location ( street, building_number, city, zip_code)
VALUES
    ( 'Brzozowa', '25', 'Lublin', '20-123'),
    ( '3 Maja', '54', 'Opole Lubelskie', '24-123'),
    ( 'Szkolna', '18F', 'Poniatowa', '22-234');

INSERT INTO parking ( name, type, secured, number_of_parking_spots, location_id)
VALUES
    ( 'Spadochroniarzy', 'PUBLIC', true, 50, 1),
    ( 'Kraczewicka', 'PRIVATE', false, 200, 2),
    ( 'Makowa', 'PUBLIC', false, 30, 3);

INSERT INTO parking_spot ( number, status, parking_id)
VALUES
    ( 1, 'FREE', 1),
    ( 2, 'OCCUPIED', 1),
    ( 3, 'FREE', 1),
    ( 1, 'OCCUPIED', 2),
    ( 2, 'FREE', 2),
    ( 1, 'FREE', 3);

INSERT INTO vehicle (license_number, mark, model)
VALUES
    ('ABC1234', 'Toyota', 'Corolla'),
    ('XYZ5678', 'Honda', 'Civic'),
    ('QWE1345', 'Opel', 'Corsa'),
    ('POI0987', 'Audi', 'A4'),
    ('LMN8901', 'Ford', 'Focus');

INSERT INTO parking_ticket ( parking_spot_id, vehicle_license_number, status, arrival_time, departure_time, fee)
VALUES
    ( 2, 'ABC1234', 'ACTIVE', '2024-12-23 08:30:00', NULL, NULL),
    ( 4, 'XYZ5678', 'PAID', '2024-12-22 14:00:00', '2024-12-22 16:00:00', 15.00),
    ( 1, 'LMN8901', 'ACTIVE', '2024-12-23 09:00:00', NULL, NULL);
