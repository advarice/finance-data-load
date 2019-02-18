CREATE TABLE `stock` (
  `stock_symbol` varchar(10) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `sector` varchar(100) DEFAULT NULL,
  `industry` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`stock_symbol`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
