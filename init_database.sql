CREATE TABLE IF NOT EXISTS parcel_tracking (
    id BIGSERIAL PRIMARY KEY,
    parcel_id VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(64) NOT NULL,
    carrier_code VARCHAR(64),
    last_updated TIMESTAMPTZ NOT NULL
);

INSERT INTO parcel_tracking (parcel_id, status, carrier_code, last_updated) VALUES
('PKG-10001', 'CREATED', 'FEDEX', NOW() - INTERVAL '5 days'),
('PKG-10002', 'IN_TRANSIT', 'UPS', NOW() - INTERVAL '3 days'),
('PKG-10003', 'OUT_FOR_DELIVERY', 'DHL', NOW() - INTERVAL '1 day'),
('PKG-10004', 'DELIVERED', 'FEDEX', NOW() - INTERVAL '6 hours'),
('PKG-10005', 'IN_TRANSIT', 'USPS', NOW() - INTERVAL '2 days'),
('PKG-10006', 'CREATED', 'DHL', NOW() - INTERVAL '4 days'),
('PKG-10007', 'RETURNED', 'UPS', NOW() - INTERVAL '12 hours'),
('PKG-10008', 'IN_TRANSIT', 'FEDEX', NOW() - INTERVAL '1 day'),
('PKG-10009', 'DELIVERED', 'USPS', NOW() - INTERVAL '3 hours'),
('PKG-10010', 'OUT_FOR_DELIVERY', 'DHL', NOW() - INTERVAL '8 hours')
ON CONFLICT (parcel_id) DO NOTHING;
