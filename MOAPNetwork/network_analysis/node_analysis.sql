-- NODE ANALYSIS

-- Average degree of the neighbors: IN-DEGREE
select n.id,count(*),sum(weight)
from location_4h_150m_node$ n,location_4h_150m_edge$ e
where n.id = e.target
group by n.id

-- Average degree of the neighbors: OUT-DEGREE
select n.id,count(*),sum(weight)
from location_4h_150m_node$ n,location_4h_150m_edge$ e
where n.id = e.source
group by n.id

-- Average degree of the neighbors: IN-DEGREE AND OUT-DEGREE
select n.id,count(*),sum(weight)
from location_4h_150m_node$ n,location_4h_150m_edge$ e
where n.id = e.target or n.id = e.source
group by n.id

------------------------------------------------------------------------------------------------------------------------------------------------------------
-- GRAU DO NODE E DOS VIZINHOS
select n.id node,aux2.degree degree,aux2.weight weight,avg(aux.degree) avg_degree_neigh,avg(aux.weight) avg_weight_neigh
from location_4h_150m_node$ n,location_4h_150m_edge$ e1,
(
	-- Consulta com o grau dos nodes
	select n.id,count(*) degree,avg(e.weight) weight
	from location_4h_150m_node$ n,location_4h_150m_edge$ e
	where n.id = e.target or n.id = e.source
	group by n.id
) aux,
(
	-- Consulta com o grau dos nodes
	select n.id,count(*) degree,avg(e.weight) weight
	from location_4h_150m_node$ n,location_4h_150m_edge$ e
	where n.id = e.target or n.id = e.source
	group by n.id
) aux2
where ((n.id = e1.source and e1.target = aux.id) or (n.id=e1.target and e1.source = aux.id)) and aux2.id=n.id
group by n.id,aux2.degree,aux2.weight
order by n.id

------------------------------------------------------------------------------------------------------------------------------------------------------------
-- GRAU DO NODE E DOS VIZINHOS: IN DEGREE
select n.id node,aux2.degree degree,aux2.weight weight,avg(aux.degree) avg_degree_neigh,avg(aux.weight) avg_weight_neigh
from location_4h_150m_node$ n,location_4h_150m_edge$ e1,
(
	-- IN-DEGREE
	select n.id,count(*) degree,avg(e.weight) weight
	from location_4h_150m_node$ n,location_4h_150m_edge$ e
	where n.id = e.target
	group by n.id
) aux,
(
	-- IN-DEGREE
	select n.id,count(*) degree,avg(e.weight) weight
	from location_4h_150m_node$ n,location_4h_150m_edge$ e
	where n.id = e.target
	group by n.id
) aux2
where aux.id = e1.source and e1.target = n.id and aux2.id=n.id
group by n.id,aux2.degree,aux2.weight
order by n.id

------------------------------------------------------------------------------------------------------------------------------------------------------------
-- GRAU DO NODE E DOS VIZINHOS: OUT DEGREE
select n.id node,aux2.degree degree,aux2.weight weight,avg(aux.degree) avg_degree_neigh,avg(aux.weight) avg_weight_neigh
from location_4h_150m_node$ n,location_4h_150m_edge$ e1,
(
	-- OUT-DEGREE
	select n.id,count(*) degree,avg(e.weight) weight
	from location_4h_150m_node$ n,location_4h_150m_edge$ e
	where n.id = e.source
	group by n.id
) aux,
(
	-- OUT-DEGREE
	select n.id,count(*) degree,avg(e.weight) weight
	from location_4h_150m_node$ n,location_4h_150m_edge$ e
	where n.id = e.source
	group by n.id
) aux2
where aux.id = e1.target and e1.source = n.id and aux2.id=n.id
group by n.id,aux2.degree,aux2.weight
order by n.id

------------------------------------------------------------------------------------------------------------------------------------------------------------
-- GRAU DO NODE E DOS VIZINHOS: OUT-DEGREE DO NODE COM IN-DEGREE DOS VIZINHOS
select n.id node,aux2.degree degree,aux2.weight weight,avg(aux.degree) avg_degree_neigh,avg(aux.weight) avg_weight_neigh
from location_4h_150m_node$ n,location_4h_150m_edge$ e1,
(
	-- IN-DEGREE
	select n.id,count(*) degree,avg(e.weight) weight
	from location_4h_150m_node$ n,location_4h_150m_edge$ e
	where n.id = e.target
	group by n.id
) aux,
(
	-- OUT-DEGREE
	select n.id,count(*) degree,avg(e.weight) weight
	from location_4h_150m_node$ n,location_4h_150m_edge$ e
	where n.id = e.source
	group by n.id
) aux2
where aux.id = e1.target and e1.source = n.id and aux2.id=n.id
group by n.id,aux2.degree,aux2.weight
order by n.id

------------------------------------------------------------------------------------------------------------------------------------------------------------
-- GRAU DO NODE E DOS VIZINHOS: IN-DEGREE DO NODE COM OUT-DEGREE DOS VIZINHOS
select n.id node,aux2.degree degree,aux2.weight weight,avg(aux.degree) avg_degree_neigh,avg(aux.weight) avg_weight_neigh
from location_4h_150m_node$ n,location_4h_150m_edge$ e1,
(
	-- OUT-DEGREE
	select n.id,count(*) degree,avg(e.weight) weight
	from location_4h_150m_node$ n,location_4h_150m_edge$ e
	where n.id = e.source
	group by n.id
) aux,
(
	-- IN-DEGREE
	select n.id,count(*) degree,avg(e.weight) weight
	from location_4h_150m_node$ n,location_4h_150m_edge$ e
	where n.id = e.target
	group by n.id
) aux2
where aux.id = e1.source and e1.target = n.id and aux2.id=n.id
group by n.id,aux2.degree,aux2.weight
order by n.id

------------------------------------------------------------------------------------------------------------------------------------------------------------
-- TABELA COM PROPRIEDADES DOS NODES
select *
from location_4h_150m_node$ n,
(
	-- AVG EDGE WEIGHT
select n.id,sum(e.weight) edgeweight,avg(e.weight) avg_edgeweight 
from location_4h_150m_node$ n,location_4h_150m_edge$ e
where e.source = n.id or e.target = n.id
group by n.id
) w,
(
	-- AVG IN-WEIGHT
select n.id,sum(e.weight) inweight,avg(e.weight) avg_inweight 
from location_4h_150m_node$ n,location_4h_150m_edge$ e
where e.target = n.id
group by n.id
) iw,
(
	-- AVG OUT-WEIGHT
select n.id,sum(e.weight) outweight,avg(e.weight) avg_outweight 
from location_4h_150m_node$ n,location_4h_150m_edge$ e
where e.source = n.id or e.target = n.id
group by n.id
) ow
where  w.id=n.id and iw.id=n.id and ow.id=n.id