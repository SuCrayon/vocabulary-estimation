CREATE DATABASE `ve` CHARACTER SET 'utf8mb4';

use ve;

drop table if exists word_category;

/*==============================================================*/
/* Table: word_category                                         */
/*==============================================================*/
create table word_category
(
   id                   varchar(32) not null  comment '单词分类id',
   level                int not null  comment '词汇等级',
   description          varchar(512)  comment '描述',
   is_deleted           int not null default 0  comment '是否已删除，0：未删除，1：已删除',
   create_time          datetime not null  comment '创建时间',
   update_time          datetime not null  comment '更新时间',
   primary key (id)
);

alter table word_category comment '单词分类表，单词分类分级';

/*==============================================================*/
/* Index: idx_word_category_level                               */
/*==============================================================*/
create index idx_word_category_level on word_category
(
   level
);

drop table if exists word;

/*==============================================================*/
/* Table: word                                                  */
/*==============================================================*/
create table word
(
   id                   varchar(32) not null  comment '单词id',
   word_category_id     varchar(32) not null  comment '单词分类id',
   level                int not null  comment '单词等级',
   word                 varchar(128) not null  comment '单词',
   is_deleted           int not null default 0  comment '是否已删除，0：未删除，1：已删除',
   create_time          datetime not null  comment '创建时间',
   update_time          datetime not null  comment '更新时间',
   primary key (id)
);

alter table word comment '单词表';

/*==============================================================*/
/* Index: idx_word_word_category_id                             */
/*==============================================================*/
create index idx_word_word_category_id on word
(
   word_category_id
);

/*==============================================================*/
/* Index: idx_word_level                                        */
/*==============================================================*/
create index idx_word_level on word
(
   level
);
