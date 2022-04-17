create table notification
(
    id bigint auto_increment,
    notifier bigint not null,
    "outerId" bigint not null,
    gmt_create bigint not null,
    status int default 0 not null,
    constraint NOTIFICATION_PK
        primary key (id)
);

