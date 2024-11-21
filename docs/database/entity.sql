create table user_info
(
  user_id       bigint primary key auto_increment not null,
  nickname      varchar(12)                       not null,
  email         text                              not null,
  profile_image text,
  role          varchar(20)                       not null,
  state         varchar(20)                       not null,
  created_at    TIMESTAMP                         not null,
  updated_at    TIMESTAMP                         not null
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

create table product
(
  product_id     bigint primary key not null,
  category_id    bigint             not null,
  user_id        bigint             not null,
  title          text               not null,
  content        text               not null,
  price          bigint             not null,
  view           bigint             not null,
  selling_status varchar(20)        not null,
  state          varchar(20)        not null,
  created_at     TIMESTAMP          not null,
  updated_at     TIMESTAMP          not null
);

create table interest_product
(
  interest_product_id bigint primary key not null,
  product_id          bigint             not null,
  user_id             bigint             not null
);

create table product_image
(
  product_image_id bigint primary key not null,
  product_id       bigint             not null,
  image_url        text               not null
);

create table category
(
  category_id bigint primary key not null,
  category    varchar(20)        not null,
  state       varchar(20)        not null
);

alter table product
  add foreign key (category_id) references category (category_id);
alter table product
  add foreign key (user_id) references user_info (user_id);
alter table interest_product
  add foreign key (product_id) references product (product_id);
alter table interest_product
  add foreign key (user_id) references user_info (user_id);
alter table product_image
  add foreign key (product_id) references product (product_id);