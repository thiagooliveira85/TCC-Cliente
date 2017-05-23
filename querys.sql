-- TRAZ OS ESTACIONAMENTOS ATIVOS --
SELECT 
    e.id,
    s.nome as status, 
    e.nome_fantasia,
    tl.nome AS tipo_logr,
    end.nome_logradouro,
    end.numero,
    b.nome AS bairro,
    c.nome AS cidade,
    end.latitude,
    end.longitude
FROM
    estacionamento e,
    endereco end,
    tipo_logradouro tl,
    bairro b,
    cidade c,
    status s
WHERE
    e.id_endereco = end.id
        AND tl.id = end.id_tipo_logradouro
        AND b.id = end.id_bairro
        AND c.id = end.id_cidade
        AND s.id = e.id_status
        AND s.nome = 'ATIVO'
        AND e.nome_fantasia = 'Blue Park';
        
        
-- TRAZ AS VAGAS DE UM ESTACIONAMENTO COM O SEU TIPO --
select v.id, tv.nome as tipo, v.codigo, v.largura, v.altura, v.status, tv.preco from vaga v, tipo_vaga tv 
where v.id_tipo_vaga = tv.id 
and v.id_estacionamento = 1;

-- QUANTIDADE TOTAIS DE VAGAS
select count(*) as qtd_vagas from vaga where id_estacionamento = 1;

-- QUANTIDADE TOTAIS DE VAGAS POR TIPO
select tv.nome, count(tv.nome) as vagas from vaga v, tipo_vaga tv 
where v.id_tipo_vaga = tv.id 
and v.id_estacionamento = 1
group by tv.nome
order by count(tv.nome);


-- CRIACAO TB TIPO_PAGAMENTO
create table tipo_pagamento (
	id int auto_increment primary key,
	nome varchar(50) not null unique	
); 

-- CRIAÇÃO DA ASSICIAÇÃO ESTACIONAMENTO COM TIPO_PAGAMENTO
create table estacionamento_tp_pagamento (
	id_estacionamento int not null,
	id_tipo_pagamento int not null    
); 

ALTER TABLE estacionamento_tp_pagamento ADD CONSTRAINT pk_tab PRIMARY KEY (id_estacionamento, id_tipo_pagamento);

ALTER TABLE estacionamento_tp_pagamento ADD CONSTRAINT fk_estacionamento
FOREIGN KEY(id_estacionamento) REFERENCES estacionamento (id);

ALTER TABLE estacionamento_tp_pagamento ADD CONSTRAINT fk_tp_pagamento
FOREIGN KEY(id_tipo_pagamento) REFERENCES tipo_pagamento (id);

alter table vaga add comprimento int;