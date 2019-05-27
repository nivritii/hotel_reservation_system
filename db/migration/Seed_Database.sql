INSERT INTO `hrs`.`room_type` (`type`, `description`, `quantity`, `price`, `created_at`, `updated_at`) VALUES ('suite', 'suite for couple', '100', '1000', 'NOW()', 'NOW()');
INSERT INTO `hrs`.`room_type` (`type`, `description`, `quantity`, `price`, `created_at`, `updated_at`) VALUES ('executive', 'executive room for one person', '200', '800', 'NOW()', 'NOW()');

INSERT INTO `hrs`.`customer` (`username`, `password`, `name`, `created_at`, `updated_at`) VALUES 
('customer', 'password', 'customer', NOW(), NOW());