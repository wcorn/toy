create table user_info
(
  user_id    bigint primary key auto_increment not null,
  nickname   varchar(12)                       not null,
  email      text,
  role       varchar(20)                       not null,
  state      varchar(20)                       not null,
  created_at TIMESTAMP                         not null,
  updated_at TIMESTAMP                         not null
);

create table admin_login
(
  admin_id bigint primary key not null,
  id       text               not null,
  password text               not null,
  salt     varchar(10)        not null
);

create table social_login
(
  user_id   bigint primary key not null,
  social_id text               not null,
  provider  varchar(20)        not null
);

create table social_provider
(
  provider varchar(20) primary key not null,
  state    varchar(20)             not null
);

alter table admin_login
  add foreign key (admin_id) references user_info (user_id);
alter table social_login
  add foreign key (user_id) references user_info (user_id);
alter table social_login
  add foreign key (provider) references social_provider (provider);