CREATE TABLE `financial_data_load` (
  `data_load_id` int(11) NOT NULL AUTO_INCREMENT,
  `load_type` varchar(50) DEFAULT NULL,
  `load_status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`data_load_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
