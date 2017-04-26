CREATE VIEW `ProductCount` AS
    SELECT 
        productSKU, quantityActual
    FROM
        ProductRoster
            NATURAL JOIN
        AisleProductCount
    WHERE
        quantityActual >= 0;