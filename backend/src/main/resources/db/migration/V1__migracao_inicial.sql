CREATE TABLE api_publica (
    api_id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    api_nome varchar(255) NOT NULL,
    api_frequencia varchar(255) NOT NULL,
    api_descricao TEXT,
    api_active boolean,
    api_url varchar(2048)
);


CREATE TABLE result_api (
    res_id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    api_id bigint,
    res_data DATE,
    res_payload JSON,
    FOREIGN KEY (api_id) REFERENCES public.api_publica(api_id)
);

CREATE TABLE tag (
    tag_id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    tag_nome varchar(30) NOT NULL,
    tag_descricao varchar(250),
    tag_data DATE,
    tag_active boolean
);

CREATE TABLE tag_api (
    api_id bigint,
    tag_id bigint,
    PRIMARY KEY (api_id, tag_id),
    FOREIGN KEY (api_id) REFERENCES public.api_publica(api_id),
    FOREIGN KEY (tag_id) REFERENCES public.tag(tag_id)
);

CREATE TABLE regionalismo (
    reg_id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    tag_id integer,
    nome varchar(100),
    FOREIGN KEY (tag_id) REFERENCES public.tag(tag_id)
);

CREATE TABLE portal (
    por_id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    por_nome varchar(30),
    por_url varchar(2048),
    por_data DATE,
    por_frequencia varchar(30)
);


CREATE TABLE tag_portal (
    tag_id bigint,
    por_id bigint,
    PRIMARY KEY(tag_id, por_id),
    FOREIGN KEY(tag_id) REFERENCES public.tag(tag_id),
    FOREIGN KEY(por_id) REFERENCES public.portal(por_id)
);

CREATE TABLE reporter (
    rep_id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    rep_nome varchar(255) NOT NULL,
    por_id bigint,
    FOREIGN KEY (por_id) REFERENCES public.portal(por_id)
);

CREATE TABLE noticia (
    noti_id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    noti_data DATE,
    noti_text varchar(255),
    noti_url varchar(2048),
    por_id bigint,
    rep_id bigint,
    FOREIGN KEY(por_id) REFERENCES public.portal(por_id),
    FOREIGN KEY(rep_id) REFERENCES public.reporter(rep_id)
);

CREATE TABLE tag_noticia (
    noti_id bigint,
    tag_id bigint,
    PRIMARY KEY (noti_id, tag_id),
    FOREIGN KEY (noti_id) REFERENCES public.noticia(noti_id),
    FOREIGN KEY (tag_id) REFERENCES public.tag(tag_id)
);

CREATE TABLE result_tag (
    res_id bigint,
    tag_id bigint,
    PRIMARY KEY (res_id, tag_id),
    FOREIGN KEY (res_id) REFERENCES public.result_api(res_id),
    FOREIGN KEY (tag_id) REFERENCES public.tag(tag_id)
);