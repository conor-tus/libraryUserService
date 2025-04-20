CREATE TABLE IF NOT EXISTS library_user (
    library_user_id int AUTO_INCREMENT PRIMARY KEY,
    username varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    mobile_number varchar(100) NOT NULL,
    created_at date NOT NULL,
    created_by varchar(20) NOT NULL,
    updated_at date DEFAULT NULL,
    updated_by varchar(20) DEFAULT NULL
);