DROP TABLE IF EXISTS SALES;
DROP TABLE IF EXISTS PRODUCTS_SALE;

CREATE TABLE `SALES` (
  `id_Sale` int NOT NULL,
  `monto_Total` double NOT NULL ,
  `time_Stamp` timestamp NULL DEFAULT NULL,
  `id_Client` int NOT NULL,
  `id_Medio_Pago` int ,
  PRIMARY KEY (`id_Sale`)
);

ALTER TABLE SALES ADD CONSTRAINT CK_monto_Total_Positivos CHECK (monto_Total > 0.0);

CREATE TABLE `PRODUCTS_SALE` (
  `idProductSale` int NOT NULL,
  `id_Sale` int NOT NULL,
  `id_Product` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`idProductSale`)
);