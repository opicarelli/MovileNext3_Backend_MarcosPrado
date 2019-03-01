-- Region 1
insert into T_REGION (id, polygon) values (1, 'POLYGON((-47.1010145892716 -22.81855527197949,-47.092474435768175 -22.828800068388006,-47.08955619235998 -22.83235985574656,-47.09509227176672 -22.838055321834787,-47.10474822422032 -22.83623596274866,-47.10607859989171 -22.83493075537399,-47.10586402317052 -22.822075771583116,-47.1010145892716 -22.81855527197949))');
-- Region 2
insert into T_REGION (id, polygon) values (2, 'POLYGON((-47.098568702521604 -22.816751687398167,-47.09650876599817 -22.813903538458675,-47.09264638501673 -22.813587073788966,-47.09110143262414 -22.81461558127721,-47.089470649543095 -22.81469469691615,-47.0868957288888 -22.81857130692694,-47.08440663892297 -22.819204212368263,-47.083205009284306 -22.81698903045462,-47.08157422620325 -22.81690991614842,-47.081531310859 -22.82019312123134,-47.07955720502404 -22.82695706527599,-47.079213594398546 -22.831054611192926,-47.079986070594835 -22.83469344359709,-47.080200647316026 -22.83623596274866,-47.081659769020135 -22.83623596274866,-47.08685252567296 -22.832913592078143,-47.08921286960607 -22.830975505060067,-47.094706033668565 -22.824251315719238,-47.09719512363439 -22.821284656005588,-47.09951255222325 -22.818436601867788,-47.098826194587026 -22.81718681601903,-47.098568702521604 -22.816751687398167))');
-- Establishment 1: Region 2
insert into T_LOCALITY (id, coordinateX, coordinateY) values (1, -47.08733362292022, -22.82426679667877);
insert into T_ESTABLISHMENT (id, documentNumber, locality_id, region_id) values (1, '84.348.151/0001-25', 1, 2);
-- Product 1: Establishment 1
insert into T_PRODUCT (id, categoryTemperature, establishment_id, name) values (1, 'FROZEN', 1, 'açaí');
insert into T_PRODUCT (id, categoryTemperature, establishment_id, name) values (2, 'COOLED', 1, 'água');
insert into T_PRODUCT (id, categoryTemperature, establishment_id, name) values (3, 'CLIMATE', 1, 'Torta de Palmito');
insert into T_PRODUCT (id, categoryTemperature, establishment_id, name) values (4, 'WARM', 1, 'Sopa de Feijão');
-- RegionExtension 1: Region 1 + Region 2; FROZEN
insert into T_LOCALITY (id, coordinateX, coordinateY) values (2, -47.093354200325074, -22.826387270684734);
insert into T_REGION_EXTENSION (id, locality_id) values (1, 2);
insert into T_REGION_EXTENSION_REGIONS (regionextension_id, region_id) values (1, 1);
insert into T_REGION_EXTENSION_REGIONS (regionextension_id, region_id) values (1, 2);
insert into T_REGION_EXTENSION_SUPPORTS (regionextension_id, support) values (1, 'FROZEN');
insert into T_REGION_EXTENSION_SUPPORTS (regionextension_id, support) values (1, 'COOLED');
-- Establishment 2: Region 2
insert into T_LOCALITY (id, coordinateX, coordinateY) values (3, -47.09021866321566, -22.8199674481165);
insert into T_ESTABLISHMENT (id, documentNumber, locality_id, region_id) values (2, '66.965.616/0001-05', 3, 2);
-- Establishment 3: Region 2
insert into T_LOCALITY (id, coordinateX, coordinateY) values (4, -47.08208620548259, -22.825297552037668);
insert into T_ESTABLISHMENT (id, documentNumber, locality_id, region_id) values (3, '69.261.831/0001-13', 4, 2);
-- Establishment 4: Region 1
insert into T_LOCALITY (id, coordinateX, coordinateY) values (5, -47.099488377571184, -22.8296583909365);
insert into T_ESTABLISHMENT (id, documentNumber, locality_id, region_id) values (4, '62.628.627/0001-49', 5, 1);
