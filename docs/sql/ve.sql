CREATE DATABASE `ve` CHARACTER SET 'utf8mb4';

use ve;

drop table if exists word_category;

/*==============================================================*/
/* Table: word_category                                         */
/*==============================================================*/
create table word_category
(
   id                   varchar(32) not null  comment '���ʷ���id',
   level                int not null  comment '�ʻ�ȼ�',
   description          varchar(512)  comment '����',
   is_deleted           int not null default 0  comment '�Ƿ���ɾ����0��δɾ����1����ɾ��',
   create_time          datetime not null  comment '����ʱ��',
   update_time          datetime not null  comment '����ʱ��',
   primary key (id)
);

alter table word_category comment '���ʷ�������ʷ���ּ�';

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
   id                   varchar(32) not null  comment '����id',
   word_category_id     varchar(32) not null  comment '���ʷ���id',
   level                int not null  comment '���ʵȼ�',
   word                 varchar(128) not null  comment '����',
   is_deleted           int not null default 0  comment '�Ƿ���ɾ����0��δɾ����1����ɾ��',
   create_time          datetime not null  comment '����ʱ��',
   update_time          datetime not null  comment '����ʱ��',
   primary key (id)
);

alter table word comment '���ʱ�';

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
