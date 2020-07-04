/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/4 10:27:03                            */
/*==============================================================*/


drop table if exists Administrators;

drop table if exists Commodity;

drop table if exists Commodity_evaluation;

drop table if exists Commodity_order;

drop table if exists Commodity_purchasing;

drop table if exists Coupon;

drop table if exists Fresh_category;

drop table if exists Full_discount_commodity_Association;

drop table if exists Full_discount_information;

drop table if exists Limited_time_promotion;

drop table if exists Menu;

drop table if exists Order_details;

drop table if exists Recommended_menu;

drop table if exists Shipping_address;

drop table if exists User_inf;

/*==============================================================*/
/* Table: Administrators                                        */
/*==============================================================*/
create table Administrators
(
   Employee_number      varchar(20) not null,
   Employee_name        varchar(20) not null,
   Pwd                  varchar(20) not null,
   primary key (Employee_number)
);

/*==============================================================*/
/* Table: Commodity                                             */
/*==============================================================*/
create table Commodity
(
   Commodity_number     varchar(20) not null,
   Promotion_number     varchar(20),
   Category_number      varchar(20),
   Trade_name           varchar(20) not null,
   Price                float not null,
   Membership_price     float not null,
   Number               int not null,
   Specifications       varchar(10) not null,
   Detail               varchar(50),
   primary key (Commodity_number)
);

/*==============================================================*/
/* Table: Commodity_evaluation                                  */
/*==============================================================*/
create table Commodity_evaluation
(
   User_number          varchar(20) not null,
   Commodity_number     varchar(20) not null,
   Evaluation_content   varchar(50),
   Evaluation_date      timestamp not null,
   Star                 int not null,
   Photo                longblob,
   primary key (User_number, Commodity_number)
);

/*==============================================================*/
/* Table: Commodity_order                                       */
/*==============================================================*/
create table Commodity_order
(
   Order_number         varchar(20) not null,
   User_number          varchar(20),
   Address_number       varchar(20),
   Original_amount      float not null,
   Settlement_amount    float not null,
   Use_coupon_number    varchar(20) not null,
   Time_of_service_required timestamp not null,
   Order_status         varchar(20) not null,
   primary key (Order_number)
);

/*==============================================================*/
/* Table: Commodity_purchasing                                  */
/*==============================================================*/
create table Commodity_purchasing
(
   Purchase_order_number varchar(20) not null,
   Employee_number      varchar(20),
   Ingredient_number    varchar(20) not null,
   Number               int not null,
   State                varchar(20) not null,
   primary key (Purchase_order_number)
);

/*==============================================================*/
/* Table: Coupon                                                */
/*==============================================================*/
create table Coupon
(
   Coupon_number        varchar(20) not null,
   User_number          varchar(20),
   Content              varchar(30),
   Applicable_amount    float not null,
   Deduction_amount     float not null,
   Start_date           timestamp not null,
   End_date             timestamp not null,
   primary key (Coupon_number)
);

/*==============================================================*/
/* Table: Fresh_category                                        */
/*==============================================================*/
create table Fresh_category
(
   Category_number      varchar(20) not null,
   Category_name        varchar(20) not null,
   Category_description varchar(50),
   primary key (Category_number)
);

/*==============================================================*/
/* Table: Full_discount_commodity_Association                   */
/*==============================================================*/
create table Full_discount_commodity_Association
(
   Commodity_number     varchar(20) not null,
   Full_discount_number varchar(20) not null,
   Start_date           timestamp not null,
   End_date             timestamp not null,
   primary key (Commodity_number, Full_discount_number)
);

/*==============================================================*/
/* Table: Full_discount_information                             */
/*==============================================================*/
create table Full_discount_information
(
   Full_discount_number varchar(20) not null,
   Content              varchar(30),
   Applicable_commodity_quantity int not null,
   Discount             float not null,
   Start_date           timestamp not null,
   End_date             timestamp not null,
   primary key (Full_discount_number)
);

/*==============================================================*/
/* Table: Limited_time_promotion                                */
/*==============================================================*/
create table Limited_time_promotion
(
   Promotion_number     varchar(20) not null,
   Promotion_price      float not null,
   Promotion_quantity   int not null,
   Start_date           timestamp not null,
   End_date             timestamp not null,
   primary key (Promotion_number)
);

/*==============================================================*/
/* Table: Menu                                                  */
/*==============================================================*/
create table Menu
(
   Menu_number          varchar(20) not null,
   Menu_name            varchar(20) not null,
   Recipe_ingredients   varchar(50) not null,
   Step                 varchar(80) not null,
   Picture              longblob,
   primary key (Menu_number)
);

/*==============================================================*/
/* Table: Order_details                                         */
/*==============================================================*/
create table Order_details
(
   Order_number         varchar(20) not null,
   Commodity_number     varchar(20) not null,
   Number               int not null,
   Price                float not null,
   Discount             float not null,
   primary key (Order_number, Commodity_number)
);

/*==============================================================*/
/* Table: Recommended_menu                                      */
/*==============================================================*/
create table Recommended_menu
(
   Commodity_number     varchar(20) not null,
   Menu_number          varchar(20) not null,
   Descri               varchar(50),
   primary key (Commodity_number, Menu_number)
);

/*==============================================================*/
/* Table: Shipping_address                                      */
/*==============================================================*/
create table Shipping_address
(
   Address_number       varchar(20) not null,
   User_number          varchar(20),
   Province             varchar(20) not null,
   City                 varchar(20) not null,
   District             varchar(20) not null,
   Address              varchar(50) not null,
   Contacts             varchar(20) not null,
   Phone                varchar(20) not null,
   primary key (Address_number)
);

/*==============================================================*/
/* Table: User_inf                                              */
/*==============================================================*/
create table User_inf
(
   User_number          varchar(20) not null,
   Employee_number      varchar(20),
   Name                 varchar(20) not null,
   Sex                  varchar(10) not null,
   Pwd                  varchar(20) not null,
   Phone                varchar(20) not null,
   Email                varchar(30) not null,
   City                 varchar(10) not null,
   Registration_time    timestamp not null,
   A_member             bool not null,
   Membership_deadline  timestamp not null,
   primary key (User_number)
);

alter table Commodity add constraint FK_Classification foreign key (Category_number)
      references Fresh_category (Category_number) on delete restrict on update restrict;

alter table Commodity add constraint FK_Promotion foreign key (Promotion_number)
      references Limited_time_promotion (Promotion_number) on delete restrict on update restrict;

alter table Commodity_evaluation add constraint FK_Commodity_evaluation foreign key (Commodity_number)
      references Commodity (Commodity_number) on delete restrict on update restrict;

alter table Commodity_evaluation add constraint FK_Commodity_evaluation1 foreign key (User_number)
      references User_inf (User_number) on delete restrict on update restrict;

alter table Commodity_order add constraint FK_Place_order foreign key (User_number)
      references User_inf (User_number) on delete restrict on update restrict;

alter table Commodity_order add constraint FK_To foreign key (Address_number)
      references Shipping_address (Address_number) on delete restrict on update restrict;

alter table Commodity_purchasing add constraint FK_Purchase foreign key (Employee_number)
      references Administrators (Employee_number) on delete restrict on update restrict;

alter table Coupon add constraint FK_Own foreign key (User_number)
      references User_inf (User_number) on delete restrict on update restrict;

alter table Full_discount_commodity_Association add constraint FK_Full_discount_commodity_Association foreign key (Commodity_number)
      references Commodity (Commodity_number) on delete restrict on update restrict;

alter table Full_discount_commodity_Association add constraint FK_Full_discount_commodity_Association1 foreign key (Full_discount_number)
      references Full_discount_information (Full_discount_number) on delete restrict on update restrict;

alter table Order_details add constraint FK_Order_details foreign key (Commodity_number)
      references Commodity (Commodity_number) on delete restrict on update restrict;

alter table Order_details add constraint FK_Order_details1 foreign key (Order_number)
      references Commodity_order (Order_number) on delete restrict on update restrict;

alter table Recommended_menu add constraint FK_Recommended_menu foreign key (Commodity_number)
      references Commodity (Commodity_number) on delete restrict on update restrict;

alter table Recommended_menu add constraint FK_Recommended_menu1 foreign key (Menu_number)
      references Menu (Menu_number) on delete restrict on update restrict;

alter table Shipping_address add constraint FK_Location foreign key (User_number)
      references User_inf (User_number) on delete restrict on update restrict;

alter table User_inf add constraint FK_Manage foreign key (Employee_number)
      references Administrators (Employee_number) on delete restrict on update restrict;

