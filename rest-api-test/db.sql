drop schema if exists blog;
create schema blog charset utf8mb4 collate utf8mb4_unicode_ci;

use blog;

drop table if exists blog_article_category;
create table blog_article_category
(
    id            varchar(50)  not null
        primary key,
    category_name varchar(120) null,
    create_time   datetime     null
);

drop table if exists blog_article_table;
create table blog_article_table
(
    id           varchar(50)  not null
        primary key,
    title        varchar(120) null comment '标题',
    subtitle     text         null comment '副标题',
    create_time  datetime     null comment '创建时间',
    update_time  datetime     null comment '更新时间',
    read_num     int          null comment '阅读数量',
    archive_date varchar(11)  null comment '归档日期(yyyy-MM)',
    state        varchar(1)   null comment '文章状态: 1发布 0未发布',
    category_id  varchar(50)  null comment '分类标签',
    tags         varchar(100) null comment '标签',
    content      text         null comment '文章内容'
) comment '文章表';

drop table if exists blog_comment_table;
create table blog_comment_table
(
    id              varchar(50)                        not null comment '评论ID'
        primary key,
    article_id      varchar(50)                        not null comment '评论的文章ID',
    refer_id        varchar(50)                        null comment '评论引用的哪个评论',
    content         text                               not null comment '评论内容',
    create_time     datetime default CURRENT_TIMESTAMP null comment '评论时间',
    nickname        varchar(120)                       null comment '用户昵称',
    email           varchar(120)                       not null comment '评论者邮箱',
    net             varchar(120)                       null comment '评论者网站',
    reger_origin_id varchar(50)                        null comment '根评论id'
) comment '评论表';

create index refer_cid
    on blog_comment_table (refer_id);

create index tid
    on blog_comment_table (article_id);


drop table if exists blog_permission_table;
create table blog_permission_table
(
    id                     varchar(50)  not null
        primary key,
    permission_name        varchar(120) not null comment '权限名字',
    permission_description varchar(120) not null comment '权限描述',
    create_time            datetime     null comment '创建时间'
);

INSERT INTO `blog_permission_table` (`id`, `permission_name`, `permission_description`, `create_time`) VALUES ('1', 'admin', 'admin', now());
INSERT INTO `blog_permission_table` (`id`, `permission_name`, `permission_description`, `create_time`) VALUES ('2', 'manage', 'manage', now());
INSERT INTO `blog_permission_table` (`id`, `permission_name`, `permission_description`, `create_time`) VALUES ('3', 'normal', 'normal', now());

drop table if exists blog_role_table;
create table blog_role_table
(
    id              varchar(50)  not null
        primary key,
    role_name       varchar(64)  not null comment '角色名字',
    permissions_ids varchar(240) not null comment '拥有的权限id，逗号分隔',
    description     varchar(120) not null comment '角色描述',
    create_time     datetime     null
) comment '角色表';

INSERT INTO `blog_role_table` (`id`, `role_name`, `permissions_ids`, `description`, `create_time`) VALUES ('1000', 'admin', '1,2,3', '管理员', now());
INSERT INTO `blog_role_table` (`id`, `role_name`, `permissions_ids`, `description`, `create_time`) VALUES ('1001', 'normal', '3', '用户', now());

drop table if exists user_table;
create table user_table
(
    id          varchar(50)  not null
        primary key,
    username    varchar(64)  not null,
    password    varchar(120) not null,
    enable      varchar(10)   not null,
    role_id     varchar(50)  not null,
    create_time datetime     null,
    constraint username
        unique (username)
) comment '用户表';
