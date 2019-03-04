create table CAP_CUSTOMER_PRODUCT (CAP_ID bigint not null auto_increment, CAP_QUANTITY integer, CAP_PRO_ID bigint, CAP_USR_ID bigint, primary key (CAP_ID));
create table PRO_PRODUCT (PRO_ID bigint not null auto_increment, PRO_DATE datetime, PRO_NAME varchar(255) not null, PRO_TYPE integer not null, PRO_VALUE decimal(10,2) not null, primary key (PRO_ID));
create table USL_USER_LEVEL (USL_ID bigint not null auto_increment, USL_LEVEL varchar(20) not null, USR_ID bigint not null, primary key (USL_ID));
create table USR_USER (USR_ID bigint not null auto_increment, USR_ENABLE bit not null, USR_LOGIN varchar(20) not null, USR_PASSWORD varchar(60) not null, primary key (USR_ID));
alter table USR_USER add constraint UK_pau1p4kqc031twukvdtajnjux unique (USR_LOGIN);
alter table CAP_CUSTOMER_PRODUCT add constraint FK_g2mwmcu1xv881sqali8paedc2 foreign key (CAP_PRO_ID) references PRO_PRODUCT (PRO_ID);
alter table CAP_CUSTOMER_PRODUCT add constraint FK_3q36iuxpvj0jo9yk24aqam9xv foreign key (CAP_USR_ID) references USR_USER (USR_ID);
alter table USL_USER_LEVEL add constraint FK_s2j2itt9iod5d9tpgdt8joanh foreign key (USR_ID) references USR_USER (USR_ID);


INSERT INTO USR_USER (USR_ENABLE, USR_LOGIN, USR_PASSWORD) VALUES (1, 'adm', '$2a$10$/rMGxGoKcgLMN4cgJf/iQO2zpnxbNAkol0n33z7f0vMJ.8teoYSKS');
INSERT INTO USL_USER_LEVEL (USL_LEVEL,USR_ID) VALUES ('ROLE_ADMIN', 1);
INSERT INTO USL_USER_LEVEL (USL_LEVEL,USR_ID) VALUES ('ROLE_VISITOR', 1);
INSERT INTO USL_USER_LEVEL (USL_LEVEL,USR_ID) VALUES ('ROLE_CUSTOMER', 1);

INSERT INTO USR_USER (USR_ENABLE, USR_LOGIN, USR_PASSWORD) VALUES (1, 'visitor', '$2a$10$/rMGxGoKcgLMN4cgJf/iQO2zpnxbNAkol0n33z7f0vMJ.8teoYSKS');
INSERT INTO USL_USER_LEVEL (USL_LEVEL,USR_ID) VALUES ('ROLE_VISITOR', 2);

INSERT INTO USR_USER (USR_ENABLE, USR_LOGIN, USR_PASSWORD) VALUES (1, 'customer', '$2a$10$/rMGxGoKcgLMN4cgJf/iQO2zpnxbNAkol0n33z7f0vMJ.8teoYSKS');
INSERT INTO USL_USER_LEVEL (USL_LEVEL,USR_ID) VALUES ('ROLE_CUSTOMER', 3);