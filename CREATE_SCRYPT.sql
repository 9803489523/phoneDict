create table phone_numbers(
  p_id serial not null primary key,
  p_number varchar(20) unique
);

create table addresses(
  a_id serial not null primary key,
  a_name text unique
);

create table owners(
  o_id serial not null primary key,
  o_name text unique
);

create table dict(
  d_id serial not null primary key,
  o_id int,
  a_id int,
  p_id int,
  foreign key(o_id) references owners(o_id) ON DELETE CASCADE,
  foreign key(a_id) references addresses(a_id) ON DELETE CASCADE,
  foreign key(p_id) references phone_numbers(p_id) ON DELETE CASCADE,
  unique(o_id,a_id,p_id),
  unique(p_id)
);
