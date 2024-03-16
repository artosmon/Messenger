 create table chatroom (
     id bigserial not null,
     first_user_name varchar(255),
     second_user_name varchar(255),
     primary key (id)
);

 create table message (
     chat_id bigint,
     id bigserial not null,
     time timestamp(6) with time zone,
     content varchar(255),
     recipient_name varchar(255),
     sender_name varchar(255),
     primary key (id)
);

 create table users (
     id bigserial not null,
     name varchar(255) unique,
     password varchar(255),
     primary key (id)
);

alter table if exists chatroom add constraint FKbfjsgc7ucjc3s3go2jxlmtrkj foreign key (first_user_name) references users (name);
alter table if exists chatroom add constraint FK82gj88s9y73gtyjei9et43irf foreign key (second_user_name) references users (name);
alter table if exists message add constraint FK90rn566jbv8saoism1l3o5g2r foreign key (chat_id) references chatroom;
alter table if exists message add constraint FKkw6lep78bmendjku1mqheb5f foreign key (recipient_name) references users (name);
alter table if exists message add constraint FK5q949rg8lhh19qbr58v4jw14c foreign key (sender_name) references users (name);
