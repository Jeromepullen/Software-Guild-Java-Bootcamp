drop database if exists dealership;
create database dealership;
use dealership;


create table `user`(
`id` int primary key auto_increment,
`username` varchar(30) not null unique,
`email` varchar(30) not null unique,
`password` varchar(100) not null,
`enabled` boolean not null,
`firstName` varchar(30) not null,
`lastName` varchar(40) not null);

create table `role`(
`id` int primary key auto_increment,
`role` varchar(30) not null
);

create table `user_role`(
`user_id` int not null,
`role_id` int not null,
primary key(`user_id`,`role_id`),
foreign key (`user_id`) references `user`(`id`),
foreign key (`role_id`) references `role`(`id`));

create table color(
	colorId int primary key auto_increment,
    name varchar(30) not null
);

create table state(
	stateId int primary key AUTO_INCREMENT,
    abbr varchar(10) not null,
    name varchar(30) not null
);

create table make(
	makeId int primary key AUTO_INCREMENT,
    name varchar(30) not null
);

create table model(
	modelId int primary key AUTO_INCREMENT,
    makeId int not null,
    name varchar(50) not null,
    constraint fk_make_model
		foreign key (makeId) 
        references make(makeId)
);

create table bodyStyle(
	bodyStyleId int primary key AUTO_INCREMENT,
    name varchar(50) not null
);

create table vehicle(
	vehicleId int primary key AUTO_INCREMENT,
    modelId int not null, 
    vin varchar(50) not null unique,
    isNew boolean not null,
    mileage int not null default 0,
	year int not null,
    bodyStyleId int not null,
    isAutomatic boolean not null default true,
    msrp Decimal not null,
    price Decimal not null,
    exteriorColorId int not null,
    interiorColorId int not null,
    description varchar(300) not null,
    url varchar(1000) null default 'https://bridgesyngap.org/wp-content/uploads/2019/09/Photo-Unavailable.jpg',
    isFeatured boolean not null default false,
    isPurchased boolean not null default false,
    constraint fk_vehicle_extcolor
		foreign key (exteriorColorId) 
        references color(colorId),
	constraint fk_vehicle_intcolor
		foreign key (interiorColorId) 
        references color(colorId),
	constraint fk_vehicle_model
		foreign key (modelId) 
        references model(modelId),
	constraint fk_vehicle_body
		foreign key (bodyStyleId) 
        references bodyStyle(bodyStyleId)
);

create table special(
	specialId int primary key AUTO_INCREMENT,
    title varchar(60) not null,
    description varchar(300) not null
);

create table contact(
	contactId int primary key AUTO_INCREMENT,
    name varchar(255) not null,
    email varchar(255) not null,
    phone varchar(50) not null,
    message varchar(300) not null
);

create table purchaseType(
	purchaseTypeId int primary key AUTO_INCREMENT,
    name varchar(60) not null
);

create table sale(
	saleId int primary key AUTO_INCREMENT,
    userId int not null,
    vehicleId int not null,
    purchaseTypeId int not null,
    name varchar(255) not null,
    email varchar(255) not null,
    phone varchar(20) not null,
    addressOne varchar(100) not null,
    addressTwo varchar(100) default 'N/A',
    city varchar(60) not null,
    stateId int not null,
    zipcode varchar(20) not null,
    timeOfSale timestamp not null,
	constraint fk_sale_user
		foreign key (userId) 
        references user(id),
	constraint fk_sale_vehicle
		foreign key (vehicleId) 
        references vehicle(vehicleId),
	constraint fk_sale_purchaseType
		foreign key (purchaseTypeId) 
        references purchaseType(purchaseTypeId),
	constraint fk_sale_state
		foreign key (stateId) 
        references state(stateId)
);

create table SEARCH(
	searchId int primary key AUTO_INCREMENT,
    make varchar(50) not null,
    model varchar(50) not null,
    minPrice int null default 0,
    maxPrice int null default 1000000,
    minYear int null default 2000,
    maxYear int null default 2020,
    year int null
);


