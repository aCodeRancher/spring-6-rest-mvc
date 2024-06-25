drop table if exists beer_audit;

CREATE TABLE `beer_audit`
(
    id                  varchar(36) NOT NULL,
    audit_id            varchar(36) NOT NULL,
    beer_name           varchar(50) NOT NULL,
    beer_style          tinyint NOT NULL,
    upc                 varchar(255) NOT NULL,
    quantity_on_hand    integer NOT NULL,
    price               decimal(38,2) not null,
    created_date        datetime(6)  DEFAULT NULL,
    update_date         datetime(6)  DEFAULT NULL,
    created_date_audit   datetime(6)  DEFAULT NULL,
    principal_name      varchar(255)  NOT NULL,
    audit_event_type    varchar(255) NOT NULL,
    version             integer ,
    PRIMARY KEY (audit_id)

) ENGINE = InnoDB;