CREATE TABLE `financial_data_load_stock` (
  `stock_symbol` varchar(15) NOT NULL,
  `financial_data_load_id` int(11) NOT NULL,
  PRIMARY KEY (`stock_symbol`,`financial_data_load_id`),
  KEY `stock_data_load_id_idx` (`financial_data_load_id`),
  CONSTRAINT `stock_data_load_id` FOREIGN KEY (`financial_data_load_id`) REFERENCES `financial_data_load` (`data_load_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
