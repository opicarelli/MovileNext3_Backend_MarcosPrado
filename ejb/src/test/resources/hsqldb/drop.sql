alter table regionextension_actual_products drop constraint regionextension_actual_products_product_fk
alter table regionextension_actual_products drop constraint regionextension_actual_products_regionextension_fk
alter table regionextension_regions drop constraint regionextension_regions_region_fk
alter table regionextension_regions drop constraint regionextension_regions_regionextension_fk
alter table regionextension_supports drop constraint regionextension_supports_regionextension_fk
alter table T_ESTABLISHMENT drop constraint establishment_locality_fk
alter table T_ESTABLISHMENT drop constraint establishment_region_fk
alter table T_LEG drop constraint leg_destination_fk
alter table T_LEG drop constraint leg_origin_fk
alter table T_LEG drop constraint leg_route_fk
alter table T_ORDER drop constraint order_destination_fk
alter table T_ORDER drop constraint order_origin_fk
alter table T_ORDER_ITEM drop constraint orderitem_order_fk
alter table T_ORDER_ITEM drop constraint orderitem_product_fk
alter table T_PRODUCT drop constraint product_establishment_fk
alter table T_REGION_EXTENSION drop constraint regionextension_locality_fk
alter table T_ROUTE drop constraint route_worker_fk
drop table regionextension_actual_products if exists
drop table regionextension_regions if exists
drop table regionextension_supports if exists
drop table T_CLIENTBUYER if exists
drop table T_ESTABLISHMENT if exists
drop table T_LEG if exists
drop table T_LOCALITY if exists
drop table T_ORDER if exists
drop table T_ORDER_ITEM if exists
drop table T_PRODUCT if exists
drop table T_REGION if exists
drop table T_REGION_EXTENSION if exists
drop table T_ROUTE if exists
drop table T_WORKER if exists
