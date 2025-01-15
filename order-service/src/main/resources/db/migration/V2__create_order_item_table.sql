create sequence order_item_id_seq start with 1 increment by 50;

create table order_items
(
    id       bigint default nextval('order_item_id_seq') not null,
    code     text not null,
    name     text not null,
    price    numeric not null,
    quantity integer not null,
    primary key (id),
    order_id bigint  not null references orders (id)
);