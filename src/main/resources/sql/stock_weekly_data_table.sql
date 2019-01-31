CREATE TABLE `stock_weekly_data` (
  `time` datetime NOT NULL,
  `open` double DEFAULT NULL,
  `high` double DEFAULT NULL,
  `low` double DEFAULT NULL,
  `close` double DEFAULT NULL,
  `volume` double DEFAULT NULL,
  `STOCK_SYMBOL` varchar(45) NOT NULL,
  PRIMARY KEY (`time`,`STOCK_SYMBOL`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
