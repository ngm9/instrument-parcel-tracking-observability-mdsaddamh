CREATE TABLE IF NOT EXISTS parcel_tracking (
    id BIGSERIAL PRIMARY KEY,
    parcel_id VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(64) NOT NULL,
    carrier_code VARCHAR(64),
    last_updated TIMESTAMPTZ NOT NULL
);
