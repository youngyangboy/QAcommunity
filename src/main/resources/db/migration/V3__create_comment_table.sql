create table comment(
    id bigint auto_increment primary key ,
    parent_id bigint not null ,
    content varchar(1024),
    type int not null ,
    commentator int not null ,
    comment_count int default 0 ,
    gmt_create bigint not null ,
    gmt_modified bigint not null ,
    like_count bigint default 0
)