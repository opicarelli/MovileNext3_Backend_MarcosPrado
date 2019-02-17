create table regionextension_regions (regionextension_id bigint not null, region_id bigint not null)
create table regionextension_supports (regionextension_id bigint not null, support varchar(255))
create table T_LEG (id bigint generated by default as identity (start with 1), stepOrder integer not null, destination_id bigint not null, origin_id bigint not null, route_id bigint not null, primary key (id))
create table T_LOCALITY (id bigint generated by default as identity (start with 1), coordinateX double not null, coordinateY double not null, primary key (id))
create table T_ORDER (id bigint generated by default as identity (start with 1), destination_id bigint not null, origin_id bigint not null, primary key (id))
create table T_ORDER_ITEM (id bigint generated by default as identity (start with 1), order_id bigint not null, product_id bigint not null, primary key (id))
create table T_PRODUCT (id bigint generated by default as identity (start with 1), categoryTemperature varchar(255) not null, name varchar(255) not null, primary key (id))
create table T_REGION (id bigint generated by default as identity (start with 1), polygon varchar(255) not null, primary key (id))
create table T_REGION_EXTENSION (id bigint generated by default as identity (start with 1), locality_id bigint not null, primary key (id))
create table T_ROUTE (id bigint generated by default as identity (start with 1), status varchar(255) not null, worker_id bigint not null, primary key (id))
create table T_WORKER (id bigint generated by default as identity (start with 1), cpf varchar(255) not null, primary key (id))
alter table regionextension_regions add constraint UK_hxengtly2hj0lcwkbsomy74lf unique (region_id)
alter table T_WORKER add constraint worker_cpf_uk unique (cpf)
alter table regionextension_regions add constraint regionextension_regions_region_fk foreign key (region_id) references T_REGION
alter table regionextension_regions add constraint regionextension_regions_regionextension_fk foreign key (regionextension_id) references T_REGION_EXTENSION
alter table regionextension_supports add constraint regionextension_supports_regionextension_fk foreign key (regionextension_id) references T_REGION_EXTENSION
alter table T_LEG add constraint leg_destination_fk foreign key (destination_id) references T_LOCALITY
alter table T_LEG add constraint leg_origin_fk foreign key (origin_id) references T_LOCALITY
alter table T_LEG add constraint leg_route_fk foreign key (route_id) references T_ROUTE
alter table T_ORDER add constraint order_destination_fk foreign key (destination_id) references T_LOCALITY
alter table T_ORDER add constraint order_origin_fk foreign key (origin_id) references T_LOCALITY
alter table T_ORDER_ITEM add constraint orderitem_order_fk foreign key (order_id) references T_ORDER
alter table T_ORDER_ITEM add constraint orderitem_product_fk foreign key (product_id) references T_PRODUCT
alter table T_REGION_EXTENSION add constraint regionextension_locality_fk foreign key (locality_id) references T_LOCALITY
alter table T_ROUTE add constraint route_worker_fk foreign key (worker_id) references T_WORKER